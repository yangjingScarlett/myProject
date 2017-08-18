package com.yang.myProject.service.local.student;

import com.yang.myProject.entity.local.Student;

import java.util.Collection;
import java.util.Date;

/**
 * @author Yangjing
 */
public interface LocalStudentService {
    String getMinEnterDate();

    String getMaxEnterDate();

    void saveStudent(Collection<Student> students);

    void saveStudent(Student student);

    Student getLatestStudent();

    /**
     * 查询指定日期区间内的Student历史数据。
     *
     * @param startDate     开始日期
     * @param endDate       结束日期
     * @return Student数据列表
     */
    Collection<Student> queryStatsData(Date startDate, Date endDate);

}
