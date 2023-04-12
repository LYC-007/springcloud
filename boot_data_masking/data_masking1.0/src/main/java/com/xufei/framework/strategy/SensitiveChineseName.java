package com.xufei.framework.strategy;

import com.xufei.framework.enums.SensitiveDefaultLengthEnum;
import com.xufei.framework.util.SensitiveInfoUtils;

/**
 * 中文名称脱敏
 **/
public class SensitiveChineseName implements IStrategy {

    @Override
    public String desensitization(String source,int begin,int end) {
        if(begin != SensitiveDefaultLengthEnum.CHINESE_NAME.getBegin() && begin !=0){
            return SensitiveInfoUtils.chineseName(source,begin);
        }
        return SensitiveInfoUtils.chineseName(source, SensitiveDefaultLengthEnum.CHINESE_NAME.getBegin());
    }

}
