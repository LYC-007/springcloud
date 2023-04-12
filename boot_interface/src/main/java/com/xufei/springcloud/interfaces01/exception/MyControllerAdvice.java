package com.xufei.springcloud.interfaces01.exception;

import com.xufei.springcloud.interfaces01.domain.Response;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class MyControllerAdvice {
    @ResponseBody
    @ExceptionHandler(ServiceException.class)
    public Response serviceExceptionHandler(ServiceException exception){
        return new Response(Integer.valueOf(exception.getCode()),exception.getMsg(),null);
    }
}
