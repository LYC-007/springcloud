package com.xufei.limit.annotation;

import java.lang.annotation.*;

/**
 *  使用Redisson进行分布式限流
 **/
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedissionRateLimit {
    String limitKey() default ""; //限流的方法名
    int time() default  1; //默认设置为1秒
    int value()  default 3;  //发放的许可证数量
}
