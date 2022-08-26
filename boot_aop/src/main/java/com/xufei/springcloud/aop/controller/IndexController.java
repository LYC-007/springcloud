package com.xufei.springcloud.aop.controller;

import com.alibaba.fastjson.JSONObject;
import com.xufei.springcloud.aop.aspectj.Log;
import com.xufei.springcloud.aop.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class IndexController {
    // 测试的方法
    @PostMapping("/save")
    @Log // 自定义的注解
    public String saveUser(@RequestBody User user) {
        log.info("Controller-收到的请求参数：{}",user.toString());
        return JSONObject.toJSONString(user);
    }
}
