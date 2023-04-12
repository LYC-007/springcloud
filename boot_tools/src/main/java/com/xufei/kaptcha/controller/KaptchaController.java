package com.xufei.kaptcha.controller;

import com.google.code.kaptcha.Producer;
import com.xufei.kaptcha.util.Base64Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
@Slf4j
@RestController("/kaptcha")
public class KaptchaController {
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    /**
     * 这个直接返回验证码图片给前端
     */
    @GetMapping(value = "/code")
    public ModelAndView getKaptchaImage(HttpServletRequest request, HttpServletResponse response) {
        ServletOutputStream out = null;
        try {
            response.setDateHeader("Expires", 0);
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            response.addHeader("Cache-Control", "post-check=0, pre-check=0");
            response.setHeader("Pragma", "no-cache");
            response.setContentType("image/jpeg");
            String type = request.getParameter("type");
            String capStr = null;
            String code = null;
            BufferedImage bi = null;
            if ("math".equals(type)) {
                String capText = captchaProducerMath.createText();
                code  = capText.substring(0, capText.lastIndexOf("@"));
                capStr = capText.substring(capText.lastIndexOf("@") + 1);//这个值需要存入Redis中和前端传过来的验证码进行比较
                bi = captchaProducerMath.createImage(code);
            } else if ("char".equals(type)) {
                capStr =code = captchaProducer.createText();
                bi = captchaProducer.createImage(capStr);
            }
            out = response.getOutputStream();
            if (bi != null) {
                ImageIO.write(bi, "jpg", out);
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 这个返回的验证码图片经过 Base64 编码后的字符串
     * <img width="40" height="30" src="data:image/jpg;base64,/9j/4QMZRXhpZgAASUkqAAgAAAAL...." />
     */
    @GetMapping("/code01")
    public Map<String, Object> generateVerificationCode(HttpServletRequest request) throws Exception {
        Map<String, Object> map = new HashMap<>();
        // 生成文字验证码
        String type = request.getParameter("type");
        String code = null;
        BufferedImage image = null;

        String capStr = null;//这个值保存到Redis中
        if ("math".equals(type)) {
            String capText = captchaProducerMath.createText();//capText:3+2=?@5
            code = capText.substring(0, capText.lastIndexOf("@"));//code:3+2=?  这个发给前端
            image = captchaProducerMath.createImage(code);
            capStr = capText.substring(capText.lastIndexOf("@") + 1);//capStr:5  这个值需要存入Redis中和前端传过来的验证码进行比较
        } else if ("char".equals(type)) {
            code = captchaProducer.createText();
            image = captchaProducer.createImage(code);
        }
        // 生成图片验证码
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        if (image != null) ImageIO.write(image, "jpg", outputStream);
        map.put("img", Base64Utils.encode(outputStream.toByteArray()));
        //生成验证码对应的token
        String codeToken = UUID.randomUUID().toString();
        //以token为key 验证码为value存在redis中
        //redisTemplate.opsForValue().set(UUID.randomUUID().toString(), capStr,5, TimeUnit.MINUTES);
        map.put("cToken", codeToken);
        return map;
    }
}
