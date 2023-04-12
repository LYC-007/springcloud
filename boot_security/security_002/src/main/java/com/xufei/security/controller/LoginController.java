package com.xufei.security.controller;

import com.xufei.security.entity.User;
import com.xufei.resultresp.R;
import com.xufei.security.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/user/login")
    public R login(@RequestBody User user){
        String jwt = loginService.login(user);
        return R.ok("登录成功！").put("token",jwt);
    }

    @PostMapping("/user/logout")
    public R logout(){
        loginService.logout();
        return R.ok("注销成功！");
    }
}

