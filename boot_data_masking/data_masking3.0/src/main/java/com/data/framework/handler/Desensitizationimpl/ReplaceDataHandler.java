package com.data.framework.handler.Desensitizationimpl;

import com.data.framework.annotation.HandleType;
import com.data.framework.handler.DataHandlerAbstract;
import com.data.framework.util.StringBuilderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component("replaceImpl")
public class ReplaceDataHandler extends DataHandlerAbstract {

    //敏感词
    private static final Map<String,String> desensitizationMap=new HashMap<>();
    private final static HandleType TYPE=HandleType.DESENSITIZE;

    @Override
    public HandleType getHandleType() {
        return TYPE;
    }
    //可替换Redis方式等
    static {
        desensitizationMap.put("敏感0","#XXX0");
    }

    /**
     * 脱敏替换
     * @param text 原文
     * @return 原文脱敏值
     */
    @Override
    public String preProcess(String text) {
        if (Objects.nonNull(text)&&!desensitizationMap.isEmpty()) {
            log.info("replaceImpl替换脱敏目标text=="+text);
            StringBuilder desensitizationResult = StringBuilderUtil.replaceMap(new StringBuilder(text), desensitizationMap);
            log.info("replaceImpl替换脱敏text结果=="+desensitizationResult.toString());
            return desensitizationResult.toString();
        }
        return text;
    }




    @Override
    public String posProcess(String Value) {
        if (Objects.nonNull(Value)&&!desensitizationMap.isEmpty()) {
            StringBuilder desensitizationResult = StringBuilderUtil.replaceFlipMap(new StringBuilder(Value), desensitizationMap);
            log.info("replaceImpl替换脱敏text结果=="+desensitizationResult.toString());
            return desensitizationResult.toString();
        }

        return Value;
    }
}
