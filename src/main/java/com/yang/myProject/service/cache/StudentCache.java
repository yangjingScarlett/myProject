package com.yang.myProject.service.cache;

import com.yang.myProject.entity.local.Student;

import java.util.Date;

/**
 * @author Yangjing
 */
public class StudentCache {
    /**
     * 最早入学日期
     */
    private Date earliestEnterDate;

    /**
     * 最近最新保存的Student对象
     */
    private volatile Student latestStudent;


    public Date getEarliestEnterDate() {
        return earliestEnterDate == null ? new Date() : earliestEnterDate;
    }

    public void setEarliestEnterDate(Date earliestEnterDate) {
        this.earliestEnterDate = earliestEnterDate;
    }

    public Student getLatestStudent() {
        return latestStudent;
    }

    public void setLatestStudent(Student latestStudent) {
        this.latestStudent = latestStudent;
    }
}
