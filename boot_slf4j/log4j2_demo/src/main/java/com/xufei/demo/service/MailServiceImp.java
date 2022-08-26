package com.xufei.demo.service;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


@Service
public class MailServiceImp {

    @Autowired
    private Configuration configuration;
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;


    /**
     * 简单版
     */
    private void basicSend() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        //邮件发送人
        simpleMailMessage.setFrom(from);
        //邮件接收人，可以是多个，参数为可变参数
        simpleMailMessage.setTo("2458918040@qq.com");
        //邮件主题，也就是标题
        simpleMailMessage.setSubject("SpringBoot测试邮件发送");
        //邮件内容
        simpleMailMessage.setText("简单的邮件正文");

        javaMailSender.send(simpleMailMessage);
    }
    public void sendHtml() throws MessagingException {
        MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setTo("2458918040@qq.com");
        mimeMessageHelper.setSubject("SpringBoot测试邮件发送");

        //邮件内容
        mimeMessageHelper.setText("<h1>Hello World</h1> <br/> " +
                "<div> 欢迎点击 <a href=\"https://blog.hhui.top\">一灰灰博文地址</a><br/>" +
                " <img width=\"200px\" height=\"200px\" src=\"https://blog.hhui.top/hexblog/imgs/info/wx.jpg\"/>" +
                "</div>", true);//setText方法的第二个参数，必须有，且为 true，否则会当成文本内容发送

        javaMailSender.send(mimeMailMessage);
    }
    /**
     * 发送附件(图片)
     */
    public void sendWithFile() throws MessagingException, IOException {
        MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setTo("bangzewu@126.com");
        mimeMessageHelper.setSubject("SpringBoot测试邮件发送");

        mimeMessageHelper.setText("<h1>Hello World</h1> <br/> " +
                "<div> 欢迎点击 <a href=\"https://blog.hhui.top\">一灰灰博文地址</a><br/>" +
                " <img width=\"200px\" height=\"200px\" src=\"https://blog.hhui.top/hexblog/imgs/info/wx.jpg\"/>" +
                "</div>");

        String url = "https://blog.hhui.top/hexblog/imgs/info/wx.jpg";
        URL imgUrl = new URL(url);
        mimeMessageHelper.addAttachment("img.jpg", imgUrl::openStream);
        javaMailSender.send(mimeMailMessage);
    }



    /**
     * freemarker 模板
     */
    public void freeMakerTemplate() throws MessagingException, IOException, TemplateException {
        MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
        mimeMessageHelper.setFrom(from);
        mimeMessageHelper.setTo("bangzewu@126.com");
        mimeMessageHelper.setSubject("SpringBoot测试邮件发送");

        Map<String, Object> map = new HashMap<>();
        map.put("title", "邮件标题");
        map.put("content", "邮件正文");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(configuration.getTemplate("mail.ftl"), map);
        mimeMessageHelper.setText(text, true);

        String url = "https://blog.hhui.top/hexblog/imgs/info/wx.jpg";
        URL imgUrl = new URL(url);
        mimeMessageHelper.addAttachment("img.jpg", imgUrl::openStream);

        javaMailSender.send(mimeMailMessage);
    }
}
