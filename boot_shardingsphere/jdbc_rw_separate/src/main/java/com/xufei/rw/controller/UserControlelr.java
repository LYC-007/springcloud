package com.xufei.rw.controller;

import com.xufei.rw.domain.User;
import com.xufei.rw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserControlelr {

    @Autowired
    private UserService userService;


    @GetMapping("/1")
    public void test() {
       userService.test();
    }
    @GetMapping("/2")
    public void test1() {
        userService.selectCount();
    }



}
