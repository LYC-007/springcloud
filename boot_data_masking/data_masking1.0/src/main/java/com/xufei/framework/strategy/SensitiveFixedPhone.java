package com.xufei.framework.strategy;


import com.xufei.framework.enums.SensitiveDefaultLengthEnum;
import com.xufei.framework.util.SensitiveInfoUtils;

/**
 * 固话脱敏
 **/
public class SensitiveFixedPhone implements IStrategy {

    @Override
    public String desensitization(String fixedPhone,int begin ,int end) {
        if(begin != SensitiveDefaultLengthEnum.FIXED_PHONE.getBegin() && begin !=0 &&
                end != SensitiveDefaultLengthEnum.FIXED_PHONE.getEnd() && end !=0){
            return SensitiveInfoUtils.fixedPhone(fixedPhone,end);
        }
        return SensitiveInfoUtils.fixedPhone(fixedPhone, SensitiveDefaultLengthEnum.FIXED_PHONE.getEnd());
    }

}
