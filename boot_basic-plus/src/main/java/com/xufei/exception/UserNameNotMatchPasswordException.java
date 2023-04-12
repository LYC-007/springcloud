package com.xufei.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN,reason = "用户名和密码不匹配！")
public class UserNameNotMatchPasswordException extends RuntimeException{
    private static final long serialVersionUID = 12L;
    private String msg;
    private int code = 500;

    public UserNameNotMatchPasswordException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public UserNameNotMatchPasswordException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public UserNameNotMatchPasswordException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public UserNameNotMatchPasswordException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
