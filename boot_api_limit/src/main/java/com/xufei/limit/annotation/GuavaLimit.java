package com.xufei.limit.annotation;

import java.lang.annotation.*;

/**
 * Google的guava单机限流
 **/
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GuavaLimit {

    String limitKey() default ""; //限流的方法名

    double value()  default 0d;  //发放的许可证数量
}
