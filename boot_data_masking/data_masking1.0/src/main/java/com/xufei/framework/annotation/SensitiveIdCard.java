package com.xufei.framework.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;

import java.lang.annotation.*;

/**
 * 身份证号脱敏
 **/
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@SensitiveInfo(strategy =com.xufei.framework.strategy.SensitiveIdCard.class,pattern = "(?<=\\w{0})\\w(?=\\w{4})",replaceChar = "*")
@JacksonAnnotationsInside
public @interface SensitiveIdCard {

}
