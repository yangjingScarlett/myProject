package com.yang.myProject.service.local.student.impl;

import com.yang.myProject.aop.Refreshable;
import com.yang.myProject.dao.local.student.LocalStudentDao;
import com.yang.myProject.entity.local.Student;
import com.yang.myProject.service.local.student.LocalStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;

/**
 * @author Yangjing
 */
@Service
@Transactional(transactionManager = "h2TransactionManager", propagation = Propagation.REQUIRED)
public class LocalStudentServiceImpl implements LocalStudentService {
    @Autowired
    private LocalStudentDao localStudentDao;

    @Override
    public String getMinEnterDate() {
        return localStudentDao.getMinEnterDate();
    }

    @Override
    public String getMaxEnterDate() {
        return localStudentDao.getMaxEnterDate();
    }

    @Override
    public void saveStudent(Collection<Student> students) {
        localStudentDao.saveStudent(students);
    }


    @Override
    public void saveStudent(Student student){
        localStudentDao.saveStudent(student);
    }

    @Override
    public Student getLatestStudent(){
        String maxEnterDate = getMaxEnterDate();
        return localStudentDao.getStudent(maxEnterDate);
    }

    @Refreshable
    @Override
    public Collection<Student> queryStatsData(Date startDate, Date endDate){
        return localStudentDao.queryStatsData(startDate,endDate);
    }
}
