package com.xufei.controller;

import com.xufei.common.BaseController;
import com.xufei.service.MailServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * slf4j+logback是springboot的默认日志框架
 * <p>
 * <p>
 * 日志的使用方式:
 * 1.protected final Logger logger = LoggerFactory.getLogger(BaseController.class);
 * 2.@Slf4j+Lombok插件
 */
@Slf4j
@RestController
@RequestMapping("/index")
public class IndexController extends BaseController {

    @Value("${server.port}")
    private String port;

    @GetMapping("/1")
    public String helloDocker() {
        String str = "hello docker" + "\t" + port + "\t" + UUID.randomUUID().toString();
        logger.info("logback print info msg:{}", str);
        int i = 10 / 0;
        return str;
    }

    @GetMapping("/2")
    public String index() {
        String str = "服务端口号: " + "\t" + port + "\t" + UUID.randomUUID().toString();
        log.info("logback print info msg:{}", str);
        return str;
    }

    @Autowired
    MailServiceImpl mailService;
    // 发件人要跟yml配置文件里填写的邮箱一致'
    @Value("${spring.mail.username}")
    String mailFrom;
    // 收件人
    @Value("${spring.mail.error.to}")
    String mailTo;
    // 抄送,也相当于收件人(不是主要的收件人)
    @Value("${spring.mail.error.to}")
    String cc;


    @GetMapping("/3")
    public void testSendSimpleMail() {
        mailService.sendSimpleMail(mailFrom, "一个大帅哥", mailTo, cc, "TestMail", "Hello World !");
    }
}
