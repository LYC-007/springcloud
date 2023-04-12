package com.xufei.framework.strategy;


import com.xufei.framework.enums.SensitiveDefaultLengthEnum;
import com.xufei.framework.util.SensitiveInfoUtils;

/**
 * 身份证号脱敏
 **/
public class SensitiveIdCard implements IStrategy {

    @Override
    public String desensitization(String idCardNum,int begin ,int end) {
        if(begin != SensitiveDefaultLengthEnum.ID_CARD_NUM.getBegin() && begin !=0 &&
                end != SensitiveDefaultLengthEnum.ID_CARD_NUM.getEnd() && end !=0){
            return SensitiveInfoUtils.idCardNum(idCardNum,begin,end);
        }
        return SensitiveInfoUtils.idCardNum(idCardNum, SensitiveDefaultLengthEnum.ID_CARD_NUM.getBegin(), SensitiveDefaultLengthEnum.ID_CARD_NUM.getEnd());
    }

}
