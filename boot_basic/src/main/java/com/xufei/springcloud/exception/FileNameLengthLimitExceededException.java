package com.xufei.springcloud.exception;

/**
 * 文件名字长度超过限制异常，用于文件校验
 */
public class FileNameLengthLimitExceededException extends RuntimeException{

    private static final long serialVersionUID = 12L;

    public FileNameLengthLimitExceededException(String message){
        super(message);
    }

}
