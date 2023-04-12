package com.xufei.springcloud.exception;

/**
 * 文件后缀无效异常，用于文件校验
 */
public class InvalidExtensionException extends RuntimeException{

    private static final long serialVersionUID = 10L;
    public InvalidExtensionException(String message){
        super(message);
    }

}
