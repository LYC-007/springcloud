package com.xufei.demo.controller;

import com.xufei.demo.service.MailServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.util.UUID;

/**
 * @Author: XuFei
 * @Date: 2022/8/17 3:27
 */
@Slf4j
@RestController
@RequestMapping("/index")
public class IndexController {

    @Autowired
    MailServiceImp mailServiceImp;
    @Value("${server.port}")
    private String port;
    @GetMapping("/1")
    public String helloDocker() throws MessagingException {
        String str = "hello docker" + "\t" + port + "\t" + UUID.randomUUID().toString();
        log.info("logback print info msg:{}", str);
        mailServiceImp.sendHtml();
        return str;
    }
    @GetMapping("/2")
    public String hello(){
        String str = "hello docker" + "\t" + port + "\t" + UUID.randomUUID().toString();
        int i=10/0;
        return str;
    }

}
