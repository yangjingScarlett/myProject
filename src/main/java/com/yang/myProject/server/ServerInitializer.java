package com.yang.myProject.server;

import com.yang.myProject.entity.local.Student;
import com.yang.myProject.service.cache.GlobalCache;
import com.yang.myProject.service.local.student.LocalStudentService;
import com.yang.myProject.service.remote.common.BusinessDateService;
import com.yang.myProject.service.remote.student.RemoteStudentService;
import com.yang.myProject.util.DateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @author Yangjing
 */
@Component
public class ServerInitializer {
    private static final Logger logger = LoggerFactory.getLogger(ServerInitializer.class);

    @Autowired
    private RemoteStudentService remoteStudentService;

    @Autowired
    private BusinessDateService businessDateService;

    @Autowired
    private LocalStudentService localStudentService;

    @PostConstruct
    public void init() {
        logger.info("Server is initializing...");
        long startInitTime = System.nanoTime();
        GlobalCache.getInstance().loadServerVersion();

        String sqlFile = this.getClass().getClassLoader().getResource("query.xsql").getPath();
        SQLQueryProcessor.loadXSqlscript(sqlFile);

        //校验本地Student缓存数据：最小EnterDate=Student的最早入学日期
        String minLocalEnterDateStr = localStudentService.getMinEnterDate();
        logger.info("Minimal EnterDate retrieved locally:{}", minLocalEnterDateStr);
        Date earliestEnterDate = remoteStudentService.getEarliestEnterDate();
        GlobalCache.getInstance().getStudentCache().setEarliestEnterDate(earliestEnterDate);
        String earliestEnterDateStr = DateTimeUtil.toDateString(earliestEnterDate);
        if (minLocalEnterDateStr != null && !minLocalEnterDateStr.equals(earliestEnterDateStr)) {
            logger.error("The locally minimal EnterDate [" + minLocalEnterDateStr + "] inconsistent with database value ["
                    + earliestEnterDateStr + "]");
        }

        //获取本地Student缓存数据：最大EnterDate值
        String maxLocalEnterDateStr = localStudentService.getMaxEnterDate();
        logger.info("Maximal EnterDate retrieved locally:{}", maxLocalEnterDateStr);
        Date maxEnterDate = maxLocalEnterDateStr == null ? null : DateTimeUtil.valueOfDate(maxLocalEnterDateStr);

        //校验本地Student缓存数据：最大EnterDate<系统业务日期
        String lowerEnterDateStr = maxLocalEnterDateStr == null ? earliestEnterDateStr : maxLocalEnterDateStr;
        Date bizDate = businessDateService.getBusinessDate();
        String upperEnterDateStr = DateTimeUtil.toDateString(bizDate);
        logger.info("System business date: {}", upperEnterDateStr);
        if (maxEnterDate != null && !maxEnterDate.before(bizDate)) {
            logger.error("The last persistent EnterDate [" + maxLocalEnterDateStr + "] is not before the system business date ["
                    + upperEnterDateStr + "]");
        }

        if (maxEnterDate == null) {
            //初始化所有数据
            logger.info("Loading all student historical statistics data from the very beginning. ({}, {})", lowerEnterDateStr, upperEnterDateStr);
            long start = System.nanoTime();
            TreeMap<String, Student> studentTreeMap = remoteStudentService.loadHistStatsData(upperEnterDateStr);
            logger.info("Student historical statistics data loaded in {} ms", (System.nanoTime() - start) / 1000 / 1000);

            logger.info("Saving student statistics data locally...");
            start = System.nanoTime();
            Collection<Student> students = studentTreeMap.values();
            localStudentService.saveStudent(students);
            logger.info("Student Historical statistics data [size={}] saved in {} ms", studentTreeMap.size(),
                    (System.nanoTime() - start) / 1000 / 1000);
            GlobalCache.getInstance().getStudentCache().setLatestStudent(studentTreeMap.lastEntry().getValue());
        } else {
            Student latestStudent = localStudentService.getLatestStudent();
            //List<Student> studentList = new LinkedList<>();
            Date nextDay = maxEnterDate;
            while (true) {
                nextDay = DateTimeUtil.nextOneDay(nextDay);
                if (nextDay.equals(bizDate)) {
                    logger.info("Student historical data before [{}] has already been loaded.", upperEnterDateStr);
                    break;
                } else {
                    logger.info("Loading student historical statistics data on {}", DateTimeUtil.toDateString(nextDay));
                    latestStudent = remoteStudentService.loadStatsData(nextDay, latestStudent);
                    //studentList.add(latestStudent);
                }
                localStudentService.saveStudent(latestStudent);
                GlobalCache.getInstance().getStudentCache().setLatestStudent(latestStudent);
            }
            logger.info("Server initialization completed in {} ms.", (System.nanoTime() - startInitTime) / 1000 / 1000);
        }
    }


}
