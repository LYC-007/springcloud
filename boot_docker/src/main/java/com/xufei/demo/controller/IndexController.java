package com.xufei.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequestMapping("/index")
@RestController
public class IndexController {

    @Value("${server.port}")
    private String port;

    @GetMapping("/1")
    public String helloDocker() {
        return "hello docker"+"\t"+port+"\t"+ UUID.randomUUID().toString();
    }

    @GetMapping("/2")
    public String index() {
        return "服务端口号: "+"\t"+port+"\t"+UUID.randomUUID().toString();
    }
}
