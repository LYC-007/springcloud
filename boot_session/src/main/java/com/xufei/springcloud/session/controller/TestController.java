package com.xufei.springcloud.session.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;
@RestController
public class TestController {
    @RequestMapping("/set")
    public Object set(HttpSession session){
        session.setAttribute("myKey","我的Session数据！");
        return "Session设置成功";
    }

    @RequestMapping("/get")
    public String get(HttpSession session){
        return (String) session.getAttribute("myKey");
    }
}
