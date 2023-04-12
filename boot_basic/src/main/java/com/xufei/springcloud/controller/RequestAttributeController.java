package com.xufei.springcloud.controller;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class RequestAttributeController {

    @GetMapping("/test1")
    public String test1(HttpServletRequest request) {
        request.setAttribute("site", "<a href='http://www.baidu.com'>百度官网！！！！！！！！</a>");
        return "forward:/test2";
    }

    @GetMapping(value = "/test2", produces = "text/html;charset=UTF-8")
    public String test2(@RequestAttribute(value = "site", required = false) String site) {
        //@RequestAttribute 注解标注的参数的值来源于org.springframework.web.servlet.mvc.method.annotation.RequestAttributeMethodArgumentResolver解析器
        //@RequestAttribute("site")注解，site 参数的值等于request.getAttribute("site")
        return site;
    }
}

