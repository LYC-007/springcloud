package com.xufei.springcloud.controller.forwards;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ForwardController {
    @GetMapping("/hello4")
    public String hello4() {
        return "hello";
    }
    //方式一：使用 "forward" 关键字（不是指java关键字），注意：类的注解不能使用@RestController 要用@Controller
    @RequestMapping(value="f1" , method = RequestMethod.GET)
    public String test() {
        return "forward:/hello4";//hello4不是一个页面而是一个请求;
    }
    //方式二：使用servlet 提供的API，注意：类的注解可以使用@RestController,也可以使用@Controller
    @RequestMapping(value="f2" , method = RequestMethod.GET)
    public void test(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //转发
        request.setAttribute("name","Jack");
        request.setAttribute("sex","女");
        request.setAttribute("age","65");
        request.getRequestDispatcher("/hello4").forward(request,response);
    }

    @GetMapping("/f3")
    public ModelAndView saveUser() {
        ModelAndView modelAndView = new ModelAndView("hello");   //默认为forward模式
        modelAndView.addObject("name", "派123大星");
        modelAndView.addObject("sex", "男");
        modelAndView.addObject("age", 53);
        return modelAndView;
    }
}
