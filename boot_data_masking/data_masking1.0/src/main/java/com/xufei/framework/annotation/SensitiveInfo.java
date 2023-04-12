package com.xufei.framework.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xufei.framework.core.SensitiveInfoSerialize;
import com.xufei.framework.strategy.DefaultSensitiveStrategy;
import com.xufei.framework.strategy.IStrategy;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 脱敏信息
 */
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = SensitiveInfoSerialize.class)
@Inherited
public @interface SensitiveInfo {

    /**
     * 脱敏策略
     * @return
     */
    Class<? extends IStrategy> strategy() default DefaultSensitiveStrategy.class;

    /**
     * 输入格式，使用正则表达式, 优先级大于value
     *
     * @return 格式
     */
    String pattern() default "";

    /**
     * 替换目标字符, 优先级大于value
     *
     * @return 替换目标字符串
     */
    String replaceChar() default "";

    /**
     * 开始显示几位
     * @return
     */
    int begin() default 0;

    /**
     * 结束显示几位
     * @return
     */
    int end() default 0;
}
