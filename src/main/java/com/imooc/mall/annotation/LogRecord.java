package com.imooc.mall.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-08-14
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogRecord {
    String operator() default "";

    String pwd() default "";

    long operateTime() default 0;

    int opType() default 0;
}