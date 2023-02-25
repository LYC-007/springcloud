package com.xufei.controller;

import com.xufei.constant.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: XuFei
 * @Date: 2022/8/12 16:47
 */

@RequestMapping("/index")
@RestController
public class IndexController {
    @GetMapping("/user")
    public User getUser(){
        User user = new User();
        user.setId(1);
        user.setName("Jack");
        user.setSex("男");
        return user;
    }
}
