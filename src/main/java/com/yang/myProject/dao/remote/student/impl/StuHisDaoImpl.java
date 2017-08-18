package com.yang.myProject.dao.remote.student.impl;

import com.yang.myProject.dao.remote.common.impl.RemoteGenericDaoImpl;
import com.yang.myProject.dao.remote.student.StuHisDao;
import com.yang.myProject.dao.remote.student.StuRealTimeDao;
import com.yang.myProject.entity.local.Student;
import com.yang.myProject.enums.SQLQueryEnum;
import com.yang.myProject.server.SQLQueryProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Yangjing
 */
@Repository
@Transactional(transactionManager = "transactionManager", propagation = Propagation.SUPPORTS, readOnly = true)
public class StuHisDaoImpl extends RemoteGenericDaoImpl implements StuHisDao {
    @Autowired
    private StuRealTimeDao stuRealTimeDao;

    @Override
    public Date getEarliestEnterDate() {
        String sql = String.format(SQLQueryProcessor.getSql(SQLQueryEnum.QUERY_EARLIEST_ENTER_DATE));
        Object obj = executeSingleStatement(sql);
        java.sql.Date date = (java.sql.Date) obj;
        Date utilDate=new Date(date.getTime());
        return obj != null ? utilDate : null;
    }

    //stuTreeMap是空的，通过getOrPutStudent方法里面加入元素
    @Override
    public void loadHistStuCnt(String maxEnterDate, TreeMap<String, Student> stuTreeMap) {
        String sql = String.format(SQLQueryProcessor.getSql(SQLQueryEnum.QUERY_STUDENT_CNT_PER_DAY_HIST), maxEnterDate);
        List<Object[]> results = getResultList(sql);
        results.forEach(r -> {
            int seq = 0;
            String enterDate = String.valueOf(r[seq++]);
            Student student = getOrPutStudent(enterDate, stuTreeMap);
            student.setStuCountPerDay(Long.valueOf(String.valueOf(r[seq])));
        });
    }

    //本方法在使用的时候是跟在loadHistStuCnt方法之后的
    //在经过loadHistStuCnt方法之后，stuTreeMap里面存放了maxEnterDate之前的所有student信息：enterDate和stuCountPerDay
    //本方法是为了添加stuCountDueDay
    @Override
    public void loadHistStuNumDueDay(TreeMap<String, Student> stuTreeMap) {
        for (Map.Entry<String, Student> entry : stuTreeMap.entrySet()) {
            String dateStr = entry.getKey();
            Student student = entry.getValue();
            stuRealTimeDao.loadStuCountDueDay(dateStr, student);
        }
    }

    private static Student getOrPutStudent(String enterDate, TreeMap<String, Student> stuTreeMap) {
        Student student = stuTreeMap.get(enterDate);
        if (student == null) {
            student = new Student(enterDate);
            stuTreeMap.put(enterDate, student);
        }
        return student;
    }
}
