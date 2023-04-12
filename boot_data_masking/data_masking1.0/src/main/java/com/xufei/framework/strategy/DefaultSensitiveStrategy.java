package com.xufei.framework.strategy;


import com.xufei.framework.enums.SensitiveDefaultLengthEnum;
import com.xufei.framework.util.SensitiveInfoUtils;

/**
 * 默认脱敏策略--脱敏
 */
public class DefaultSensitiveStrategy implements IStrategy{

    @Override
    public String desensitization(String source, int begin, int end) {
        return SensitiveInfoUtils.password(source, SensitiveDefaultLengthEnum.DEFAULT_STRATEGY.getBegin());
    }
}
