package com.yang.myProject.enums;

/**
 * @author Yangjing
 */
public enum SQLQueryEnum {
    /**
     * student表的所有信息
     */
    QUERY_STUDENT_DETAIL,

    /**
     * course表的所有信息
     */
    QUERY_COURSE_DETAIL,

    /**
     * 指定日期当天的入学人数
     */
    QUERY_STUDENT_CNT,

    /**
     * 指定日期当天之前的入学人数
     */
    QUERY_STUDENT_CNT_DUE_DAY,

    /**
     * 最早入学日期
     */
    QUERY_EARLIEST_ENTER_DATE,

    /**
     * 某天之前的累计入学人数
     */
    QUERY_STUDENT_CNT_PER_DAY_HIST,

    /**
     * 获取业务日期
     */
    QUERY_BUSINESS_DATE,

}
