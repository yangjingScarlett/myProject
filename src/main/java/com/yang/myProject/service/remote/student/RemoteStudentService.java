package com.yang.myProject.service.remote.student;

import com.yang.myProject.entity.local.Student;

import java.util.Date;
import java.util.TreeMap;

/**
 * @author Yangjing
 */
public interface RemoteStudentService {
    Date getEarliestEnterDate();

    /**
     * 加载指定入学日期之前的学生数据
     *
     * @param dateStr 指定入学日期
     * @return 历史学生数据集合
     */
    TreeMap<String, Student> loadHistStatsData(String dateStr);

    Student loadStatsData(Date specificDate, Student prevStudent);


    /**
     * 获取指定入学日期区间内的入学学生数据明细。
     *
     * @param enterDate 指定入学日期
     * @return 入学学生列表
     *//*
    Collection<Student> getStudentDetails( Date enterDate);*/

}
