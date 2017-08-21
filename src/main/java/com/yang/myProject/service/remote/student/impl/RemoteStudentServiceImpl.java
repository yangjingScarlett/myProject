package com.yang.myProject.service.remote.student.impl;

import com.yang.myProject.dao.remote.student.StuHisDao;
import com.yang.myProject.dao.remote.student.StuRealTimeDao;
import com.yang.myProject.entity.local.Student;
import com.yang.myProject.service.remote.student.RemoteStudentService;
import com.yang.myProject.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.TreeMap;

/**
 * @author Yangjing
 */
@Service
@Transactional(transactionManager = "transactionManager", propagation = Propagation.SUPPORTS, readOnly = true)
public class RemoteStudentServiceImpl implements RemoteStudentService {
    @Autowired
    private StuRealTimeDao stuRealTimeDao;

    @Autowired
    private StuHisDao stuHisDao;

    @Override
    public Date getEarliestEnterDate() {
        return stuHisDao.getEarliestEnterDate();
    }

    @Override
    public TreeMap<String, Student> loadHistStatsData(String dateStr) {
        TreeMap<String, Student> stuTreeMap = new TreeMap<>();
        stuHisDao.loadHistStuCnt(dateStr, stuTreeMap);
        stuHisDao.loadHistStuNumDueDay(stuTreeMap);
        return stuTreeMap;
    }

    @Override
    public Student loadStatsData(Date specificDate, Student prevStudent) {
        String specificDateStr = DateTimeUtil.toDateString(specificDate);
        Student student = new Student(specificDateStr);
        stuRealTimeDao.loadStuCount(specificDateStr, student);
        stuRealTimeDao.loadStuCountDueDay(specificDateStr, student);
        return student;
    }
}
