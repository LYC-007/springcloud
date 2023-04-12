package com.xufei.springcloud.interfaces01.controller;

import com.xufei.springcloud.interfaces01.domain.Response;
import com.xufei.springcloud.interfaces01.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TokenController {
    @Autowired
    private TokenService tokenService;

    @GetMapping("/token")//该接口用于生成一个token字符串并把它存在redis中
    public Response token(){
        return tokenService.createToken();
    }

    @PostMapping("/check")
    public Response checkToken(HttpServletRequest request){
        return tokenService.checkToken(request);
    }
}
