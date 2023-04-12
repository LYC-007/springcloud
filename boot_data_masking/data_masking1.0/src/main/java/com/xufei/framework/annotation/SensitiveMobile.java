package com.xufei.framework.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;

import java.lang.annotation.*;

/**
 * 手机号脱敏
 **/
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@SensitiveInfo(strategy = com.xufei.framework.strategy.SensitiveMobile.class,pattern = "(?<=\\w{3})\\w(?=\\w{4})",replaceChar = "*")
@JacksonAnnotationsInside
public @interface SensitiveMobile {

}
