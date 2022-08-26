package com.xufei.springcloud.login.controller;

import com.xufei.springcloud.login.domain.UserVo;
import com.xufei.springcloud.login.utils.Auth0JwtUtils01;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class IndexController {//前端登录后从后端token字符串，每次访问后端时前端都的带上这个字符串
    @PostMapping("/login")
    public String login() {
        UserVo userVo = new UserVo();
        userVo.setId(22L);
        userVo.setUsername("Jack");
        return Auth0JwtUtils01.Token(userVo);
    }
    @PostMapping("/check")
    public String check(HttpServletRequest request) {
        return Auth0JwtUtils01.getMemberIdByJwtToken(request);
    }
}
