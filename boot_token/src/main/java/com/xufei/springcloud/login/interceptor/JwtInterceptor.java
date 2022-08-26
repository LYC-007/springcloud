package com.xufei.springcloud.login.interceptor;


import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.xufei.springcloud.login.utils.Auth0JwtUtils01;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class JwtInterceptor implements HandlerInterceptor {
    //JWT拦截器,所有请求都被这个拦截器拦截,校验header中的token,token校验通过再放行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String,Object> map = new HashMap<>();
        try {
            Auth0JwtUtils01.verifyToken(request);
            return true;
        } catch (AlgorithmMismatchException e) {
            map.put("msg","JWT算法不匹配!");
        } catch (SignatureVerificationException e) {
            map.put("msg","JWT签名不匹配!");
        } catch (TokenExpiredException e) {
            map.put("msg","用户信息已经过期,请重新登录!");
        } catch (RuntimeException e) {
            map.put("msg","无效登录信息!");
        }
        map.put("state",false);
        response.setContentType("application/json;charset=utf-8");
        String msg = new ObjectMapper().writeValueAsString(map);
        response.getWriter().println(msg);
        return false;
    }
}

