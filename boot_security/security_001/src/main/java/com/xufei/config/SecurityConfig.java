package com.xufei.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter  {

    @Autowired
    private AuthenticationSuccessHandler authencationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authencationFailureHandler;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().
                //配置认证成功处理器
                        successHandler(authencationSuccessHandler)
                //配置认证失败处理器
                .failureHandler(authencationFailureHandler);

        //配置注销成功处理器
        http.logout().logoutSuccessHandler(logoutSuccessHandler);


        //因为重写了 所以需要手动添加认证规则
        http.authorizeRequests().anyRequest().authenticated();
    }
}
