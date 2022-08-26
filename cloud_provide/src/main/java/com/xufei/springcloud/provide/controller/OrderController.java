package com.xufei.springcloud.provide.controller;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @Author: XuFei
 * @Date: 2022/8/23 14:56
 */
@RefreshScope
@RestController
@RequestMapping("/order")
public class OrderController {
    @GetMapping("/find/{id}")
    public String find(@PathVariable("id") Long id) {
        return id + ":cloud_provide";
    }

    @GetMapping("/time/{id}")
    public String timeOut(@PathVariable("id") Long id) {
        try {
            TimeUnit.SECONDS.sleep(6);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return id+":超时测试！！";
    }
}
