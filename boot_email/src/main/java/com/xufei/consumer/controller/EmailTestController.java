package com.xufei.consumer.controller;


import com.xufei.util.EmailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@RestController
public class EmailTestController {
    @Autowired
    private EmailUtils emailUtil;
    // 接收方邮箱地址
    private static final String to = "2458918040@qq.com";

    /**
     * 发送简单邮件
     */

    @GetMapping("/sendMsg")
    public String sendMessage() {
        emailUtil.sendTextMessage(to, "发送简单邮件", "Hello Spring Email");
        return "邮件发送成功！！！！！！！！！！！";
    }

    /**
     * 发送带附件的邮件
     */
    public void sendEmailWithAttachments() {
        Map<String, File> fileMap = new HashMap<>();
        fileMap.put("image1.jpg", new File("E:\\Aaron\\picture\\附件1.png"));
        fileMap.put("image2.jpg", new File("E:\\Aaron\\picture\\附件2.png"));
        emailUtil.sendEmailWithAttachments(to, "发送带附件的邮件", "Hello Spring Email", fileMap);
    }

    /**
     * 发送带内嵌资源的邮件
     */
    public void sendEmailWithInline() {
        emailUtil.sendEmailWithInline(to, "发送带内嵌资源的邮件"
                , "Hello Spring Email", new File("E:\\Aaron\\picture\\附件3.png"));
    }

    /**
     * 使用模板邮件
     */
    @GetMapping("/1")
    public void sendEmailByTemplate() {
        emailUtil.sendEmailByTemplate(to, "使用模板邮件", "Hello Spring Email");
    }

}
