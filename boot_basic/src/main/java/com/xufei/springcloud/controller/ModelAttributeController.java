package com.xufei.springcloud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ModelAttributeController {

    /**
     * 被@ModelAttribute注释的方法会在此controller的每个方法执行前被执行 ，如果有返回值，则自动将该返回值加入到ModelMap中。
     * 因此对于一个controller映射多个URL的用法来说，要谨慎使用。我们编写控制器代码时，会将保存方法独立成一个控制器也是如此。
     * 都可以通过入参接收前台提交的数据，而且对入参绑定的设置都是一样的。
     * 入参绑定的数据如果没有设置可为空，不能接收空数据,否则会报错。
     * 都可以将数据放入model中，而且对于一次请求,model是共享的,所以在处理方法中的model中存放了@ModelAttribute注解的方法中存放的数据。
     */
    @ModelAttribute
    public void populateModel(@RequestParam String abc, Model model) {
        model.addAttribute("attributeName", abc);
    }

    @GetMapping(value = "/helloWorld")
    public String helloWorld() {
        return "helloWorld.jsp";
    }



}
