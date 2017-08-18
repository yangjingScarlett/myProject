package com.yang.myProject.dao.remote.student;

import com.yang.myProject.entity.local.Student;

/**
 * @author Yangjing
 */
public interface StuRealTimeDao {

    /**
     * 加载指定入学日期当天的学生数量
     *
     * @param enterDate 指定入学日期
     * @param student   记录值
     */
    void loadStuCount(String enterDate, Student student);

    /**
     * 加载指定日期之前累计的入学学生数量
     *
     * @param enterDate 指定入学日期
     * @param student   记录值
     */
    void loadStuCountDueDay(String enterDate, Student student);

    /**
     * 根据T-1日数据，更新T日数据相关信息：
     * <p>
     *
     * @param student     T日数据
     * @param prevStudent T-1日数据
     */
    void calculateRelativeStats(Student student, Student prevStudent);

}
