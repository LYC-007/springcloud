package com.xufei.consumer.controller;

import com.xufei.provicer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {
    @Autowired
    private UserService userService;

    @GetMapping("/1")
    public String test(){
        userService.save();
        return "OK";
    }
}
