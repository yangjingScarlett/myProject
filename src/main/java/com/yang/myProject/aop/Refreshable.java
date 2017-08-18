package com.yang.myProject.aop;

import java.lang.annotation.*;

/**
 * @author Yangjing
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface Refreshable {
}
