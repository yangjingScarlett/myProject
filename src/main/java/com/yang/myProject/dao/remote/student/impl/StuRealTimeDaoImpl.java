package com.yang.myProject.dao.remote.student.impl;

import com.yang.myProject.aop.Monitorable;
import com.yang.myProject.dao.remote.common.impl.RemoteGenericDaoImpl;
import com.yang.myProject.dao.remote.student.StuRealTimeDao;
import com.yang.myProject.entity.local.Student;
import com.yang.myProject.enums.SQLQueryEnum;
import com.yang.myProject.server.SQLQueryProcessor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Yangjing
 */
@Repository
@Transactional(transactionManager = "transactionManager", propagation = Propagation.SUPPORTS, readOnly = true)
public class StuRealTimeDaoImpl extends RemoteGenericDaoImpl implements StuRealTimeDao {

    @Monitorable
    @Override
    public void loadStuCount(String enterDate, Student student) {
        String sql = String.format(SQLQueryProcessor.getSql(SQLQueryEnum.QUERY_STUDENT_CNT), enterDate);

        List<Object[]> results = getResultList(sql);
        if (!results.isEmpty()) {
            Object[] res = results.get(0);
            student.setStuCountPerDay(Long.valueOf(String.valueOf(res[0])));
        }
    }

    //该方法总是在loadStuCount方法之后执行的
    @Monitorable
    @Override
    public void loadStuCountDueDay(String enterDate, Student student) {
        String sql = String.format(SQLQueryProcessor.getSql(SQLQueryEnum.QUERY_STUDENT_CNT_DUE_DAY), enterDate);

        List results = getResultList(sql);
        if (!results.isEmpty()) {
            BigDecimal cnt = (BigDecimal) results.get(0);
            student.setStuCountDueDay(Long.valueOf(String.valueOf(cnt.toString())));
        }
    }

    @Monitorable
    @Override
    public void calculateRelativeStats(Student student, Student prevStudent) {
        Long totalStuCount = prevStudent.getStuCountDueDay() + student.getStuCountPerDay();
        student.setStuCountDueDay(totalStuCount);
    }
}

