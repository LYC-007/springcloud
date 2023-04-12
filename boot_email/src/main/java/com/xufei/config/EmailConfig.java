package com.xufei.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
@Configuration
public class EmailConfig {

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String authWord;

    /**
     * 在这里可以声明不同的邮件服务器主机，
     * 通常是SMTP主机,而具体的用户名和时授权码则建议在业务中从数据库查询
     */
    @Bean(name = "mainSender")
    JavaMailSenderImpl javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(host);
        // 设置发送人邮箱和授权码
        javaMailSender.setUsername(username);
        javaMailSender.setPassword(authWord);
        return javaMailSender;
    }

}