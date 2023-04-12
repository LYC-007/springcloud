package com.data.framework.annotation;

import com.data.framework.handler.DataHandlerAbstract;
import com.data.framework.handler.Desensitizationimpl.DesensitizationDataHandler;
import com.data.framework.handler.encryptionimpl.AES;

import java.lang.annotation.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @DataHandle(HandleType.ENCRYPA) 要加密，解密字段
 * @DataHandle(HandleType.DESENSITIZE) 脱敏字段
 */
@Documented
@Inherited
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataHandle {
    HandleType value();
}