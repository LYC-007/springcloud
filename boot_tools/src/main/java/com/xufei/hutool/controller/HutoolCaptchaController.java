package com.xufei.hutool.controller;

import cn.hutool.captcha.*;
import cn.hutool.captcha.generator.MathGenerator;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.lang.Console;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * 验证码示例
 */
@Controller
public class HutoolCaptchaController {
    private LineCaptcha lineCaptcha;
    private final Logger logger = LoggerFactory.getLogger(HutoolCaptchaController.class);
    @GetMapping(value = "/toLogin")
    public String login() {
        return "redirect:http://localhost:8001/index";  //转发
    }
    @GetMapping("/hello")
    public void hello(HttpServletResponse response) throws IOException {
        response.sendRedirect("http://localhost:8001/index");
    }
    @GetMapping(value = "/test")
    public void test11(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.getRequestDispatcher("hello").forward(request, response);//转发
    }
    //方式一：使用 "forward" 关键字（不是指java关键字），注意：类的注解不能使用@RestController 要用@Controller
    @GetMapping(value = "/index")
    public String test() {
        return "login";//转发
    }
    /**
     * 登录逻辑实现
     */
    private final HashMap<String,String> hashMap=new HashMap<>();
    @PostMapping(value = "/login")
    public String index(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String imgText = request.getParameter("imgText");
        logger.info("username:{}", username);
        logger.info("password:{}", password);
        logger.info("登录验证码:{}", lineCaptcha.getCode());
        request.getParameter("imgText");
        //if (("user").equals(username) && ("123").equals(password) && imgText.equals(lineCaptcha.getCode())) {
        if (("user").equals(username) && ("123").equals(password) && new MathGenerator().verify(hashMap.get("code"), imgText)) {
            // 可以使用MathGenerator对象中的verify方法校验 :boolean verify = new MathGenerator().verify(code, userInputCode);
            return "hello";
        } else {
            return "redirect:http://localhost:8001/index";
        }
    }

    /**
     * 生成验证码
     */
    @GetMapping(value = "/getCode")
    public void getCode(HttpServletResponse response) {
        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "No-cache");
        //四则运算验证码
        lineCaptcha = CaptchaUtil.createLineCaptcha(100/*宽*/, 30/*高*/, 6/*验证码显示几个*/, 150/*有几个线段*/);
        try {
            // 调用父类的 setGenerator() 方法，设置验证码的类型
            lineCaptcha.setGenerator(new MathGenerator());// 自定义验证码内容为四则运算方式
            // 输出到页面
            lineCaptcha.createCode(); // 重新生成code
            hashMap.put("code",lineCaptcha.getCode().toString());
            lineCaptcha.write(response.getOutputStream());
            // 打印日志
            logger.info("生成的验证码:{}", lineCaptcha.getCode());
            // 关闭流
            response.getOutputStream().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/hutool")
    public void getCaptcha(HttpServletResponse response) {
        // 1.创建线条干扰验证码    定义图形验证码的长、宽、验证码字符数、干扰元素个数
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(100/*宽*/, 30/*高*/);
        // 2.扭曲干扰验证码
        ShearCaptcha shearCaptcha = CaptchaUtil.createShearCaptcha(200, 100, 4, 4);
        // 3.定义GIF验证码的长、宽
        GifCaptcha gifCaptcha = CaptchaUtil.createGifCaptcha(200, 100);
        System.out.println(gifCaptcha.getCode());
        // 4.随机4位数字验证码
        LineCaptcha numberCode = CaptchaUtil.createLineCaptcha(200/*宽*/, 100/*高*/, 4/*验证码显示几个*/, 50/*有几个线段*/);
        numberCode.setGenerator(new RandomGenerator("0123456789", 4));
        numberCode.createCode();// 重新生成code
        System.out.println(numberCode.getCode());
        // 5.圆形干扰验证码
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(200, 100, 4, 30);
        System.out.println(circleCaptcha.getCode());
        //6.四则运算验证码
        LineCaptcha mathLineCaptcha = CaptchaUtil.createLineCaptcha(200/*宽*/, 100/*高*/, 4/*验证码显示几个*/, 50/*有几个线段*/);
        mathLineCaptcha.setGenerator(new MathGenerator());// 自定义验证码内容为四则运算方式
        mathLineCaptcha.createCode(); // 重新生成code
        System.out.println(mathLineCaptcha.getCode());
        // 可以使用MathGenerator对象中的verify方法校验 :boolean verify = new MathGenerator().verify(code, userInputCode);


        //告诉浏览器输出内容为jpeg类型的图片
        response.setContentType("image/jpeg");
        //禁止浏览器缓存
        response.setHeader("Pragma", "No-cache");
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            lineCaptcha.write(outputStream);
            String code = lineCaptcha.getCode();//存入redis中，然后来校验
            Console.log(code);
            //关闭流
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
