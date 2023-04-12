package com.server.config;

import com.server.service.TorlesseUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * spring security配置
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private TorlesseUserDetailsService torlesseUserDetailsService;

    /**
     * 密码的加密方式
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 身份验证管理器
     * 通过自定义实现userDetailsService来实现
     * 配置了使用BCryptPasswordEncoder哈希来保存用户的密码（生产环境的用户密码肯定不能是明文保存）
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 注入userDetailsService的实现类并通过passwordEncoder进行加密
        auth.userDetailsService(torlesseUserDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * 设置认证管理器 便于我们使用 ，使用默认的认证管理器即可
     */
    @Override
    @Bean(value = "authenticationManager")
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 设置拦截器
     * 除了"/login","/oauth/authorize"请求外,设置为任意的请求都需要登录认证
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/login", "/oauth/authorize")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login");
    }

    /**
     * 静态资源放行【如果存在静态资源的话】
     */
    @Override
    public void configure(WebSecurity webSecurity) {
        // 静态资源放行
        webSecurity.ignoring().antMatchers("/dist/**", "/moudle/**", "/plugins/**");
    }
}
