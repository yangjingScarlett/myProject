package com.yang.myProject.dao.remote.student;

import com.yang.myProject.entity.local.Student;

import java.util.Date;
import java.util.TreeMap;

/**
 * @author Yangjing
 */
public interface StuHisDao {
    Date getEarliestEnterDate();

     /**
     * 加载最大入学日期之前的历史入学学生人数。
     *
     * @param maxEnterDate            最大入学日期
     * @param stuTreeMap    记录集合
     */
    void loadHistStuCnt(String maxEnterDate, TreeMap<String, Student> stuTreeMap);

    /**
     * 加载历史截至每日的累计入学学生人数
     *
     * @param stuTreeMap 记录集合
     */
    void loadHistStuNumDueDay(TreeMap<String, Student> stuTreeMap);

}
