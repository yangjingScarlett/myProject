package com.yang.myProject.entity.local;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Yangjing
 */
@Entity(name = "L_STUDENT")
public class Student {
    @Id
    @Column(name = "ENTER_DATE")
    private String enterDate;

    /**
     * enterDate当天累计的入学人数
     */
    @Column(name = "STU_CNT_PER_DAY")
    private Long stuCountPerDay;

    /**
     * enterDate及之前累计的入学人数(包括enterDate)
     */
    @Column(name = "STU_CNT_DUE_DAY")
    private Long stuCountDueDay;

    public Student() {
    }

    public Student(String enterDate) {
        this.enterDate = enterDate;
    }

    public String getEnterDate() {
        return enterDate;
    }

    public void setEnterDate(String enterDate) {
        this.enterDate = enterDate;
    }

    public Long getStuCountPerDay() {
        return stuCountPerDay;
    }

    public void setStuCountPerDay(Long stuCountPerDay) {
        this.stuCountPerDay = stuCountPerDay;
    }

    public Long getStuCountDueDay() {
        return stuCountDueDay;
    }

    public void setStuCountDueDay(Long stuCountDueDay) {
        this.stuCountDueDay = stuCountDueDay;
    }
}
