package com.xufei.framework.annotation;


import com.xufei.framework.handler.impl.DesensitizationDataHandler;
import com.xufei.framework.handler.DataHandlerAbstract;
import com.xufei.framework.handler.impl.encryption.AES;

import java.lang.annotation.*;


/**
* 实现数据加密，解密，脱敏
 *
 * 识别要加密或脱敏的类，指定加密方法，脱敏方法（无参数执行默认方式）
*/
@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MybatisDataProcess {
    /**
     *加密模式
     */
    Class<? extends DataHandlerAbstract> encryptionMode() default AES.class;

    /**
     *脱敏模式
     */
    Class<? extends DataHandlerAbstract> desensitizationMode() default DesensitizationDataHandler.class;



}

