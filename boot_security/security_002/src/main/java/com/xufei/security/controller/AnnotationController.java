package com.xufei.security.controller;

import com.xufei.security.annotation.Anonymous;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnnotationController {

    @Anonymous//被该注解标注的地址可以允许访问
    @PostMapping({"/112","/102"})
    public String hello3() {
        return "hello112";
    }
}
