package com.xufei.springcloud.interfaces01.domain;


import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Response {
    private Integer status;
    private String msg;
    private Object data;

    public Response(Integer valueOf, String msg, Object o) {
        this.status=valueOf;
        this.msg=msg;
        this.data=o;
    }
    //省略get、set、toString、无参有参构造方法
}
