package com.xufei.framework.strategy;

import com.xufei.framework.enums.SensitiveDefaultLengthEnum;
import com.xufei.framework.util.SensitiveInfoUtils;

/**
 * 电子邮箱脱敏
 **/
public class SensitiveEmail implements IStrategy {

    @Override
    public String desensitization(String email,int begin,int end) {
        if(begin != SensitiveDefaultLengthEnum.EMAIL.getBegin() && begin !=0 ){
            return SensitiveInfoUtils.email(email,begin);
        }
        return SensitiveInfoUtils.email(email, SensitiveDefaultLengthEnum.EMAIL.getBegin());
    }

}
