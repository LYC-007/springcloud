package com.xufei.springcloud.exception;

/**
 * @Author: XuFei
 * @Date: 2022/8/27 15:47
 */
public class FileTypeException extends RuntimeException {

    public FileTypeException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
