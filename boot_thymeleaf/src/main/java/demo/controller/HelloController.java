package demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HelloController {

    /*
       参数：Model 可以存放数据， 放入到request作用域
       返回值：String:表示视图
     */
    @RequestMapping("/hello")
    public String hello(Model model, HttpServletRequest request){
        //添加数据
        //model.addAttribute("name","李四");
        request.setAttribute("name","张三");
        //指定模板视图
        return "hello";

    }
}
