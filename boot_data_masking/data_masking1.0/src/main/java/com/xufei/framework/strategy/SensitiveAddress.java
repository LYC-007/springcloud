package com.xufei.framework.strategy;


import com.xufei.framework.enums.SensitiveDefaultLengthEnum;
import com.xufei.framework.util.SensitiveInfoUtils;

/**
 * 地址脱敏
 **/
public class SensitiveAddress implements IStrategy {
    @Override
    public String desensitization(String address,int begin, int end) {
        if(begin != SensitiveDefaultLengthEnum.ADDRESS.getBegin() && begin !=0 ){
            return SensitiveInfoUtils.address(address,begin);
        }
        return SensitiveInfoUtils.address(address, SensitiveDefaultLengthEnum.ADDRESS.getBegin());
    }

}
