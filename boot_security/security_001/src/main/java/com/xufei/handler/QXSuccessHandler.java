package com.xufei.handler;


import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义认证成功处理器:1.AuthenticationSuccessHandler接口;2.在SpringSecurity中进行配置;
 * 实际上在UsernamePasswordAuthenticationFilter进行登录认证的时候，
 * 如果登录成功了UsernamePasswordAuthenticationFilter的父类AbstractAuthenticationProcessingFilter的doFilter方法是会调用AuthenticationSuccessHandler的方法进行认证成功后的处理的。
 *
 * AuthenticationSuccessHandler就是登录成功处理器。
 *
 *
 *
 * 但是我们如果不经过UsernamePasswordAuthenticationFilter那么我们自定义的认证成功处理器就没有作用，在项目security_002中就没有用到
 * UsernamePasswordAuthenticationFilter，所以在security_002中配置自定义认证成功处理器就没有作用;
 * 在security_002中得到用户名和密码后通过自定义的LoginServiceImpl直接调用AuthenticationManager中的方法
 * 没有通过UsernamePasswordAuthenticationFilter中的方法调用AuthenticationManager中的方法;
 *
 *
 * 我们自定义的注销成功处理器和认证失败处理器也是同样的道理，在security_002中不起作用
 *
 *
 *
 */
@Component
public class QXSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("认证成功");
    }
}
