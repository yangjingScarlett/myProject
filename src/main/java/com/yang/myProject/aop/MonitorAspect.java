package com.yang.myProject.aop;

import com.yang.myProject.api.CimsResult;
import com.yang.myProject.util.PerfMonitor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author Yangjing
 * <p>
 * 这是编写的切面
 * 切面类
 */
@Component
@Aspect
public class MonitorAspect {
    private static final Logger logger = LoggerFactory.getLogger(MonitorAspect.class);

    //这里的意思是匹配注解@Monitorable
    @Around("@annotation(com.yang.myProject.aop.Monitorable)")
    public CimsResult collectPerfAspect(ProceedingJoinPoint pjp) throws Throwable {
        boolean isMonitor = logger.isInfoEnabled();
        PerfMonitor monitor = null;

        if (isMonitor) {
            monitor = PerfMonitor.startInstance();
        }

        //环绕通知=前置+目标方法执行+后置通知，proceed方法就是用于启动目标方法执行的，返回一个Object
        CimsResult result = (CimsResult) pjp.proceed();

        if (isMonitor) {
            //asssert后边跟一个布尔表达式,如果表达式的值为true，那么就认为当前条件符合要求，继续执行业务代码。
            // 如果表达式的值为false，那么认为当前条件不符合要求，立即抛出AssertionError的错误。
            assert monitor != null;
            monitor.stop();

            //获得当前正在执行的方法
            MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
            Method targetMethod = methodSignature.getMethod();

            //得到 方法的Annotation 信息，然后就可以调用 Annotation 的方法得到响应属性值
            Monitorable monitorable = targetMethod.getAnnotation(Monitorable.class);
            String comment = monitorable != null ? monitorable.method() : targetMethod.getName();
            logger.info("Monitorable [{}] completed in {} ms", comment, monitor.getElapsedTime());
        }
        return result;

    }
}
