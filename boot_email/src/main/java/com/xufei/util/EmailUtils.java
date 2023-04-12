package com.xufei.util;

import com.xufei.provicer.domain.EmailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Component
public class EmailUtils {

    @Autowired
    private JavaMailSenderImpl mailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;
    @Value("${spring.mail.username}")
    private String username;

    /**
     * 发送简单邮件
     */
    public void sendTextMessage(String to, String subject, String content) {

        // 实例化消息对象
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(username);
        msg.setTo(to);
        msg.setSubject(subject);
        msg.setText(content);
        try {
            // 发送消息
            this.mailSender.send(msg);
            System.out.println("邮件发送成功");
        } catch (MailException e) {
            System.out.println("邮件发送失败:" + e.getMessage());
        }
    }

    /**
     * 发送带附件的邮件
     */
    public void sendEmailWithAttachments(String to, String subject,
                                         String content, Map<String, File> files) {
        /**
            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName, file);
         */
        try {
            // 实例化消息对象
            MimeMessage message = mailSender.createMimeMessage();
            // 需要指定第二个参数为true 代表创建支持可选文本，内联元素和附件的多部分消息
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(username);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content);
            // 传入附件
            for (Map.Entry<String, File> entry : files.entrySet()) {
                helper.addAttachment(entry.getKey(), entry.getValue());
            }
            // 发送消息
            this.mailSender.send(message);
            System.out.println("邮件发送成功");
        } catch (MessagingException e) {
            System.out.println("邮件发送失败:" + e.getMessage());
        }
    }

    /**
     *  发送带内嵌资源的邮件
     */
    public void sendEmailWithInline(String to, String subject, String content, File file) {
        try {
            // 实例化消息对象
            MimeMessage message = mailSender.createMimeMessage();
            // 需要指定第二个参数为true 代表创建支持可选文本，内联元素和附件的多部分消息
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(username);
            helper.setTo(to);
            helper.setSubject(subject);
            // 使用true标志来指示包含的文本是HTML 固定格式资源前缀 cid:
            helper.setText("<html><body><img src='cid:image'></body></html>", true);
            // 需要先指定文本 再指定资源文件
            FileSystemResource res = new FileSystemResource(file);
            helper.addInline("image", res);
            // 发送消息
            this.mailSender.send(message);
            System.out.println("邮件发送成功");
        } catch (MessagingException e) {
            System.out.println("邮件发送失败:" + e.getMessage());
        }
    }

    /**
     *  使用模板邮件
     */
    public void sendEmailByTemplate(String to, String subject, String content) {
        try {
            Context ctx = new Context();
            ctx.setVariable("subject", subject);
            ctx.setVariable("content", content);
            String emailStr = templateEngine.process("email_template", ctx);
            // 实例化消息对象
            MimeMessage message = mailSender.createMimeMessage();
            // 指定 utf-8 防止乱码
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
            helper.setFrom(username);
            helper.setTo(to);
            helper.setSubject(subject);
            // 为true 时候 表示文本内容以 html 渲染
            helper.setText(emailStr, true);
            this.mailSender.send(message);
            System.out.println("邮件发送成功");
        } catch (MessagingException e) {
            // 消息发送失败可以做对应的处理
            System.out.println("邮件发送失败:" + e.getMessage());
        }
    }

    //构建复杂邮件信息类
    private void sendMimeMail(EmailVo emailVo) {
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mailSender.createMimeMessage(), true);//true表示支持复杂类型
            //emailVo.setFrom(getMailSendFrom());//邮件发信人从配置项读取
            messageHelper.setFrom(emailVo.getFrom());//邮件发信人
            messageHelper.setTo(emailVo.getTo().split(","));//邮件收信人
            messageHelper.setSubject(emailVo.getSubject());//邮件主题
            messageHelper.setText(emailVo.getText());//邮件内容
            if (!StringUtils.isEmpty(emailVo.getCc())) {//抄送
                messageHelper.setCc(emailVo.getCc().split(","));
            }
            if (!StringUtils.isEmpty(emailVo.getBcc())) {//密送
                messageHelper.setCc(emailVo.getBcc().split(","));
            }
            if (emailVo.getMultipartFiles() != null) {//添加邮件附件
                for (MultipartFile multipartFile : emailVo.getMultipartFiles()) {
                    messageHelper.addAttachment(Objects.requireNonNull(multipartFile.getOriginalFilename()), multipartFile);
                }
            }
            if (StringUtils.isEmpty(emailVo.getSentDate())) {//发送时间
                emailVo.setSentDate(new Date());
                messageHelper.setSentDate(emailVo.getSentDate());
            }
            mailSender.send(messageHelper.getMimeMessage());//正式发送邮件
            emailVo.setStatus("ok");
        } catch (Exception e) {
            throw new RuntimeException(e);//发送失败
        }
    }

}