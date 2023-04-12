package com.xufei.framework.strategy;


import com.xufei.framework.enums.SensitiveDefaultLengthEnum;
import com.xufei.framework.util.SensitiveInfoUtils;

/**
 * 手机号码脱敏
 **/
public class SensitiveMobile implements IStrategy {

    @Override
    public String desensitization(String mobile,int begin ,int end) {
        if(begin != SensitiveDefaultLengthEnum.MOBILE.getBegin() && begin !=0 &&
                end != SensitiveDefaultLengthEnum.MOBILE.getEnd() && end !=0){
            return SensitiveInfoUtils.mobilePhone(mobile,begin,end);
        }
        return SensitiveInfoUtils.mobilePhone(mobile, SensitiveDefaultLengthEnum.MOBILE.getBegin(), SensitiveDefaultLengthEnum.MOBILE.getEnd());
    }

}
