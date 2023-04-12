package com.xufei.exception;


import com.xufei.result.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @ControllerAdvice+@ExceptionHandler 能自定义处理大多异常（包括特殊的异常）
 * 剩余处理不到的异常交给自定义的异常处理控制器(MyBasicErrorController)处理。
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.xufei.controller")
public class GlobalExceptionControllerAdvice {
    /**
     * 返回视图
     */
    @ExceptionHandler({RRException.class})
    public ModelAndView handleArithmeticException(RRException e) {
        ModelAndView modelAndView = new ModelAndView();
        log.error("异常是：{}", e.getMsg());
        modelAndView.setViewName("error/RRException"); //视图地址
        return modelAndView;
    }

    /**
     * 放回 json 字符串
     */
    @ExceptionHandler(value = UserNameNotMatchPasswordException.class)
    public AjaxResult userNameNotMatchPasswordExceptionException(UserNameNotMatchPasswordException e) {
        log.error("数据效验出现问题{},异常类型{}", e.getMessage(), e.getClass());
        return AjaxResult.error(e.getCode(), e.getMessage());
        //前端返回的json字符串为:{"msg":"UserNameNotMatchPasswordException 异常！！！！！","code":500}
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleException(MethodArgumentNotValidException e, HttpServletRequest req) {
        //检查请求是否为ajax, 如果是 ajax 请求则返回 Result json串, 如果不是 ajax 请求则返回 error 视图
        String contentTypeHeader = req.getHeader("Content-Type");
        String acceptHeader = req.getHeader("Accept");
        String xRequestedWith = req.getHeader("X-Requested-With");
        if ((contentTypeHeader != null && contentTypeHeader.contains("application/json"))
                || (acceptHeader != null && acceptHeader.contains("application/json"))
                || "XMLHttpRequest".equalsIgnoreCase(xRequestedWith)) {
            return AjaxResult.error(500,e.getMessage());
        } else {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("message", e.getMessage());
            modelAndView.addObject("url", req.getRequestURL());
            modelAndView.addObject("stackTrace", e.getStackTrace());
            modelAndView.addObject("author", "Jack");
            modelAndView.addObject("ltd", "商城");
            modelAndView.setViewName("error/error");
            return modelAndView;
        }
    }
}