package com.xufei.framework.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 密码脱敏
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@SensitiveInfo(strategy =com.xufei.framework.strategy.SensitivePassword.class,pattern = "(?<=).",replaceChar = "*")
@JacksonAnnotationsInside
public @interface SensitivePassword {

}
