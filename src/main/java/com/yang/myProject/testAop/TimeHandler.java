package com.yang.myProject.testAop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author Yangjing
 */
@Component
@Aspect
public class TimeHandler {

    @Around("execution(* com.yang.myProject.testAop.DaoImpl.insert()) || execution(* com.yang.myProject.testAop.DaoImpl.delete())")
    public void printTime(ProceedingJoinPoint pjp) {
        /*Signature signature = pjp.getSignature();
        if (signature instanceof MethodSignature) {
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();
            System.out.println(method.getName() + "()方法开始时间：" + System.currentTimeMillis());

            try {
                pjp.proceed();
                System.out.println(method.getName() + "()方法结束时间：" + System.currentTimeMillis());
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }*/
        System.out.println("方法开始时间：" + System.currentTimeMillis());
        try {
            pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println("方法结束时间：" + System.currentTimeMillis());


    }

}
