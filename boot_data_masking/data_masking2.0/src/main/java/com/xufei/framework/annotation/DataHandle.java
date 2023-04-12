package com.xufei.framework.annotation;


import com.xufei.framework.enums.HandleType;

import java.lang.annotation.*;

/**
 * @DataHandle(HandleType.ENCRYPA) 要加密，解密字段
 * @DataHandle(HandleType.DESENSITIZE) 脱敏字段
 *
 */
@Documented
@Inherited
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataHandle {
    HandleType value();
}
