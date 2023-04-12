package com.xufei.framework.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;

import java.lang.annotation.*;

/**
 * 中文姓名脱敏
 **/
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@SensitiveInfo(strategy = com.xufei.framework.strategy.SensitiveChineseName.class, pattern = "(?<=.{1}).",replaceChar = "*")
@JacksonAnnotationsInside
public @interface SensitiveChineseName {


}
