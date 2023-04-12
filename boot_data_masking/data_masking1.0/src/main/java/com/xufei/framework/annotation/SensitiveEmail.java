package com.xufei.framework.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;

import java.lang.annotation.*;
import com.xufei.framework.annotation.SensitiveEmail;

/**
 * 邮箱脱敏
 **/
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@SensitiveInfo(strategy = com.xufei.framework.strategy.SensitiveEmail.class,pattern = "(\\w+)\\w{3}@(\\w+)",replaceChar = "$1***@$2")
@JacksonAnnotationsInside
public @interface SensitiveEmail {

}
