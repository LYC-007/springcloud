package com.xufei.controller;

import com.xufei.exception.RRException;
import com.xufei.exception.UserNameNotMatchPasswordException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello/{id}")
    public void hello(@PathVariable("id") Integer integer) {
        if (integer == 1) {
            throw new RRException("RRException 异常！！！！！");
        } else if (integer == 2) {
            throw new UserNameNotMatchPasswordException("UserNameNotMatchPasswordException 异常！！！！！");
        } else if (integer == 3) {
            throw new NullPointerException();
        }
        System.out.println("HelloController,没有抛出异常！！！！！");
    }

    @GetMapping("/error")
    public Object testError() {
        return 1 / 0;
    }

    @PostMapping("/post")
    public void test1() {
        System.out.println("HelloController,没有抛出异常！！！！！");
    }
}
