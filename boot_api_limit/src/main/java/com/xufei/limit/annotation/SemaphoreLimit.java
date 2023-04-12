package com.xufei.limit.annotation;

import java.lang.annotation.*;


@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SemaphoreLimit {

    String limitKey() default "";

    int value()  default 3;

}
