package com.xufei.enums;

public enum BizCodeEnum {
    UNKNOWN_EXCEPTION(10000,"系统未知异常！"),
    VAL_EXCEPTION(10001,"参数格式校验失败！");
    private final Integer code;
    private final String message;
    BizCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public Integer getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
}
