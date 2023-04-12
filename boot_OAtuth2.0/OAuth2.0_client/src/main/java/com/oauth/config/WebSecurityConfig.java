package com.oauth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * spring security配置
 */
@Configuration
@Order(200)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * /路径和/login路径允许访问，其它路径需要身份认证后才能访问
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers( "/demo**","/login**").permitAll()
                .antMatchers("/test/hello").anonymous()
                .anyRequest().authenticated();
    }
}
