package com.xufei.framework.handler.impl;

import com.xufei.framework.enums.HandleType;
import com.xufei.framework.handler.DataHandlerAbstract;
import com.xufei.framework.util.StringBuilderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 脱敏
 */
@Slf4j
@Component("desensitizationImpl")
public class DesensitizationDataHandler extends DataHandlerAbstract {

    private final static HandleType TYPE=HandleType.DESENSITIZE;

    @Override
    public HandleType getHandleType() {
        return TYPE;
    }

    //敏感词 可替换Redis方式等
    private static final Map<String,String> desensitizationMap=new HashMap<>();
    static {
        desensitizationMap.put("敏感0","#0");
        desensitizationMap.put("敏感1","#1");
        desensitizationMap.put("敏感2","#2");
        desensitizationMap.put("敏感3","#3");
        desensitizationMap.put("敏感4","#4");
        desensitizationMap.put("敏感5","#5");
        desensitizationMap.put("敏感6","#6");
        desensitizationMap.put("敏感7","#7");
        desensitizationMap.put("敏感8","#8");
        desensitizationMap.put("敏感9","#9");
    }

    /**
     * 脱敏替换
     * @param text 原文
     * @return 原文脱敏值
     */
    @Override
    public String preProcess(String text) {
        if (Objects.nonNull(text)&&!desensitizationMap.isEmpty()) {
            log.info("desensitizationImpl替换脱敏目标text=="+text);
            StringBuilder desensitizationResult = StringBuilderUtil.replaceMap(new StringBuilder(text), desensitizationMap);
            log.info("desensitizationImpl替换脱敏text结果=="+desensitizationResult.toString());
            return desensitizationResult.toString();
        }
        return text;
    }




    @Override
    public String posProcess(String Value) {
        log.info("desensitizationImpl脱敏目标text=="+Value);
        return Value;
    }
}
