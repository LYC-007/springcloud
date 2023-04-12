package com.lkadoc.result;

import com.lk.api.annotation.LKAModel;
import com.lk.api.annotation.LKAProperty;

import java.util.HashMap;
import java.util.Map;

@LKAModel
public class ApiResult {
    @LKAProperty(value="状态码")
    private String code;
    @LKAProperty(value="消息")
    private String msg;
    @LKAProperty(value="响应数据")
    private Map<String,Object> result = new HashMap<>();

    private ApiResult() {}

    public static ApiResult success() {
        ApiResult res = new ApiResult();
        return res;
    }

    public ApiResult put(String key,Object value) {
        this.result.put(key, value);
        return this;
    }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getMsg() { return msg; }
    public void setMsg(String msg) { this.msg = msg; }
    public Map<String, Object> getResult() { return result; }
    public void setResult(Map<String, Object> result) { this.result = result; }
}
