package com.yang.myProject.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Yangjing
 */
public class DateTimeUtil {
    private static final Logger logger = LoggerFactory.getLogger(DateTimeUtil.class);

    private static final String NORMAL_TIME_FORMAT = "HH:mm:ss";
    private static final String NORMAL_DATE_FORMAT = "yyyy/MM/dd";
    private static final String NORMAL_DATE_FORMAT1 = "yyyy-MM-dd";
    private static final String NORMAL_DATETIME_FORMAT = "yyyy/MM/dd HH:mm:ss";
    private static final String NORMAL_FULLDATETIME_FORMAT = "yyyy/MM/dd HH:mm:ss.SSS";
    private static final String NORMAL_FULLTIME_FORMAT = "HH:mm:ss.SSS";

    public static Date valueOfDate(String dateStr) {
        try {
            if (dateStr.contains("/")) {
                SimpleDateFormat sdf = new SimpleDateFormat(NORMAL_DATE_FORMAT);
                return sdf.parse(dateStr);
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat(NORMAL_DATE_FORMAT1);
                return sdf.parse(dateStr);
            }
        } catch (ParseException e) {
            logger.error("Invalid date format " + dateStr);
            return null;
        }
    }

    public static Date valueOfDateTime(String dateTimeStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(NORMAL_DATETIME_FORMAT);
        try {
            return sdf.parse(dateTimeStr);
        } catch (ParseException e) {
            logger.error("Invalid datetime format " + dateTimeStr);
            return null;
        }
    }

    public static String toDateString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(NORMAL_DATE_FORMAT);
        return sdf.format(date);
    }

    public static String toDateTimeString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(NORMAL_DATETIME_FORMAT);
        return sdf.format(date);
    }

    public static String nextOneDay(String dateStr) {
        return prevOrNextNthDate(dateStr, 1);
    }

    public static Date nextOneDay(Date date) {
        return prevOrNextNthDate(date, 1);
    }

    public static String prevOneDay(String dateStr) {
        return prevOrNextNthDate(dateStr, -1);
    }

    public static Date prevOneDay(Date date) {
        return prevOrNextNthDate(date, -1);
    }

    public static String prevOrNextNthDate(String dateStr, int prevOrNextCnt) {
        Date date = valueOfDate(dateStr);
        Date prevDate = prevOrNextNthDate(date, prevOrNextCnt);
        return toDateString(prevDate);
    }

    public static Date prevOrNextNthDate(Date date, int prevOrNextCnt) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, prevOrNextCnt);
        return c.getTime();
    }

    public static Date getBeginningDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
