package com.data.framework.annotation;

import com.data.framework.handler.DataHandlerAbstract;

import java.util.Map;
public enum HandleType {
    /**
     * 加密类型
     */
    ENCRYPA("该字段需要加密存入数据库！"),
    /**
     * 脱敏类型
     */
    DESENSITIZE("该字段返回给前端需要进行脱敏处理！");
    private final String desc;
    HandleType(String desc){
        this.desc=desc;
    }
    public String getDesc() {
        return desc;
    }
}
