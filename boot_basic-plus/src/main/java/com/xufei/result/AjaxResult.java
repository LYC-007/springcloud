package com.xufei.result;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import java.util.HashMap;
import java.util.Objects;

public class AjaxResult extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;
    /**
     * 状态码
     */
    public static final String CODE_TAG = "code";
    /**
     * 返回内容
     */
    public static final String MSG_TAG = "msg";
    /**
     * 数据对象
     */
    public static final String DATA_TAG = "data";
    /**
     * 初始化一个新创建的 AjaxResult 对象，使其表示一个空消息。
     */
    public AjaxResult() {
    }
    public AjaxResult(String msg) {
        super.put(MSG_TAG, msg);
    }
    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param code 状态码
     * @param msg  返回内容
     */
    public AjaxResult(int code, String msg) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
    }
    /**
     * 初始化一个新创建的 AjaxResult 对象
     *
     * @param code 状态码
     * @param msg  返回内容
     * @param data 数据对象
     */
    public AjaxResult(int code, String msg, Object data) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        if (Objects.nonNull(data)) {
            super.put(DATA_TAG, data);
        }
    }
    //利用fastjson进行反序列化
    public <T> T getData(TypeReference<T> typeReference) {
        Object data = get("data");	//默认是map
        String jsonString = JSON.toJSONString(data);
        return JSON.parseObject(jsonString, typeReference);
    }
    //利用fastjson进行反序列化
    public <T> T getData(String key,TypeReference<T> typeReference) {
        Object data = super.get(key);	//默认是map
        String jsonString = JSON.toJSONString(data);
        return JSON.parseObject(jsonString, typeReference);
    }
    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static AjaxResult success() {
        return new AjaxResult("操作成功！");
    }
    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static AjaxResult success(int code, String msg) {
        return new AjaxResult(code,msg);
    }
    /**
     * 返回成功消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static AjaxResult success(int code,String msg, Object data) {

        return new AjaxResult(code, msg, data);
    }

    /**
     * 返回错误消息
     */
    public static AjaxResult error() {
        return new AjaxResult("未知异常，请联系管理员！");
    }
    /**
     * 返回错误消息
     * @param msg 返回内容
     * @return 警告消息
     */
    public static AjaxResult error(String msg) {
        return new AjaxResult(msg);
    }
    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg  返回内容
     * @return 警告消息
     */
    public static AjaxResult error(int code, String msg) {
        return new AjaxResult(code, msg);
    }
    /**
     * 返回错误消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static AjaxResult error(int code,String msg, Object data) {
        return new AjaxResult(code, msg, data);
    }
    public AjaxResult setData(Object object){
        super.put(DATA_TAG, object);
        return this;
    }
}
