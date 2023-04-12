package com.xufei.framework.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;

import java.lang.annotation.*;


/**
 * 银行卡号脱敏
 **/
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@SensitiveInfo(strategy = com.xufei.framework.strategy.SensitiveChineseName.class, pattern = "(?<=\\w{4})\\w(?=\\w{4})",replaceChar = "*")
@JacksonAnnotationsInside
public @interface SensitiveBankCard {

}
