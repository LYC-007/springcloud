package com.xufei.framework.strategy;


import com.xufei.framework.enums.SensitiveDefaultLengthEnum;
import com.xufei.framework.util.SensitiveInfoUtils;

/**
 * 密码脱敏
 **/
public class SensitivePassword implements IStrategy {

    @Override
    public String desensitization(String password,int begin,int end) {
        if(begin != SensitiveDefaultLengthEnum.PASSWORD.getBegin() && begin !=0){
            return SensitiveInfoUtils.password(password,begin);
        }
        return SensitiveInfoUtils.password(password, SensitiveDefaultLengthEnum.PASSWORD.getBegin());
    }

}
