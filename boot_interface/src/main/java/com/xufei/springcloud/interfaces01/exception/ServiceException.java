package com.xufei.springcloud.interfaces01.exception;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
public class ServiceException extends RuntimeException{


    private static final long serialVersionUID = 11L;

    @Setter
    @Getter
    private String code;

    @Setter
    @Getter
    private String msg;

    public ServiceException(String toString, String msg) {

        this.code=toString;
        this.msg=msg;
    }
    //省略get、set、toString以及构造方法
}
