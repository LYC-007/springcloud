package com.xufei.limit.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisLimit {
    /**
     * 限流唯一标示
     */
    String key() default "";
    /**
     * 限流时间
     */
    int time();
    /**
     * 限流次数
     */
    int count();
}
