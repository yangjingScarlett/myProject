package com.yang.myProject.aop;

import com.yang.myProject.entity.local.Student;
import com.yang.myProject.service.cache.GlobalCache;
import com.yang.myProject.service.local.student.LocalStudentService;
import com.yang.myProject.service.remote.common.BusinessDateService;
import com.yang.myProject.service.remote.student.RemoteStudentService;
import com.yang.myProject.util.DateTimeUtil;
import com.yang.myProject.util.PerfMonitor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Yangjing
 */
@Component
@Aspect
public class RefreshAspect {
    private static final Logger logger= LoggerFactory.getLogger(RefreshAspect.class);

    @Autowired
    private RemoteStudentService remoteStudentService;

    @Autowired
    private BusinessDateService businessDateService;

    @Autowired
    private LocalStudentService localStudentService;

    private static Lock lock = new ReentrantLock();

    @Before("@annotation(com.yang.myProject.aop.Refreshable)")
    public void refreshAspect() {
        Date bizDate = businessDateService.getBusinessDate();
        Student latestStudent = GlobalCache.getInstance().getStudentCache().getLatestStudent();
        Date latestDate = DateTimeUtil.valueOfDate(latestStudent.getEnterDate());
        Date nextDay = latestDate;
        if (latestDate.before(DateTimeUtil.prevOneDay(bizDate))) {
            try {
                lock.lock();
                Date newLatestDate = DateTimeUtil.valueOfDate(
                        GlobalCache.getInstance().getStudentCache().getLatestStudent().getEnterDate());
                if (!newLatestDate.equals(latestDate)) {
                    return;
                }
                logger.info("System business date has been changed to {}", DateTimeUtil.toDateString(bizDate));
                PerfMonitor monitor = PerfMonitor.startInstance();
                List<Student> studentList = new LinkedList<>();
                while (true) {
                    nextDay = DateTimeUtil.nextOneDay(nextDay);
                    if (nextDay.equals(bizDate)) {
                        break;
                    } else {
                        logger.info("Fetching cash historical statistics data on {}", DateTimeUtil.toDateString(nextDay));
                        latestStudent = remoteStudentService.loadStatsData(nextDay, latestStudent);
                        studentList.add(latestStudent);
                    }
                }
                monitor.stop();
                logger.info("Fetching historical statistics data completed in {} ms", monitor.getElapsedTime());
                localStudentService.saveStudent(studentList);
                GlobalCache.getInstance().getStudentCache().setLatestStudent(latestStudent);
            } finally {
                lock.unlock();
            }
        }
    }

}
