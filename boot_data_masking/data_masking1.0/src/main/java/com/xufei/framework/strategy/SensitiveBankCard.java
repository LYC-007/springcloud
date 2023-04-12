package com.xufei.framework.strategy;


import com.xufei.framework.enums.SensitiveDefaultLengthEnum;
import com.xufei.framework.util.SensitiveInfoUtils;

/**
 * 银行卡号脱敏
 **/
public class SensitiveBankCard implements IStrategy {

    @Override
    public String desensitization(String bankCard,int begin, int end) {
        if(begin != SensitiveDefaultLengthEnum.BANKCARD.getBegin() && begin !=0 &&
                end != SensitiveDefaultLengthEnum.BANKCARD.getEnd() && end !=0){
            return SensitiveInfoUtils.bankCard(bankCard,begin,end);
        }
        return SensitiveInfoUtils.bankCard(bankCard, SensitiveDefaultLengthEnum.BANKCARD.getBegin(), SensitiveDefaultLengthEnum.BANKCARD.getEnd());
    }

}
