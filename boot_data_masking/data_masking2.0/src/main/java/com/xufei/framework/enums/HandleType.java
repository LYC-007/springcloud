package com.xufei.framework.enums;


public enum HandleType {
    /**
     * 加密类型
     */
    ENCRYPA("加密类型"),
    /**
     * 脱敏类型
     */
    DESENSITIZE("脱敏类型");
    private final String desc;

    HandleType(String desc){
        this.desc=desc;
    }

    public String getDesc() {
        return desc;
    }
}
