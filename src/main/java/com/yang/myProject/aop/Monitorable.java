package com.yang.myProject.aop;

import java.lang.annotation.*;

/**
 * @author Yangjing
 *
 * 这是一个自定义的注解
 * PointCut连接点注解类
 */
//运行时 Annotation 指 @Retention 为 RUNTIME 的 Annotation
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface Monitorable {
    //注解类的方法名为注解配置参数名，且：没有方法体、参数和修饰符（只允许public&abstract）；
    //返回值为基本类型；default为默认值
    String method() default "HELLO WORLD";
}
