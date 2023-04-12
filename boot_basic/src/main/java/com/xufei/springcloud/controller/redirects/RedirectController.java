package com.xufei.springcloud.controller.redirects;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class RedirectController {
    @GetMapping("/hello5")
    public String hello5() {
        return "hello";
    }
    //方式一：使用 "redirect" 关键字（不是指java关键字），注意：类的注解不能使用@RestController，要用@Controller
    @GetMapping("/r1" )
    public String test1() {
        return "redirect:hello5";
    }

    //方式二：使用servlet 提供的API，注意：类的注解可以使用@RestController，也可以使用@Controller
    @GetMapping("/r2" )
    public void test2(HttpServletResponse response) throws IOException {
        //方式一:
        response.sendRedirect("http://localhost:8001/hello5");
        //方式二:
        //response.sendRedirect("/hello5");
    }

    @GetMapping("/r3")
    public ModelAndView test3() {
        // ModelAndView modelAndView = new ModelAndView("redirect:/hello5");
        ModelAndView modelAndView = new ModelAndView("redirect:http://localhost:8001/hello5");
        modelAndView.addObject("age","2343");//它会把参数拼接到后面:http://localhost:8001/hello5?age=2343
        return modelAndView;
    }
    @GetMapping(value = "/r4")
    public String test4(RedirectAttributes attributes) {//我们想要重定向一个视图，并且还要携带数据可以使用：RedirectAttributes
        /**
         * addAttribute(“param”, value);这种方式就相当于重定向之后，在url后面拼接参数，这样在重定向之后的页面或者控制器再去获取url后面的参数就可以了，但这个方式因为是在url后面添加参数的方式，所以暴露了参数，有风险
         * addFlashAttribute(“param”, value);这种方式也能达到重新向带参，而且能隐藏参数，其原理就是放到session中，session在跳到页面后马上移除对象。所以你刷新一下后这个值就会丢掉
         */
        attributes.addFlashAttribute("age", "66");
        attributes.addFlashAttribute("name","xufei");
        attributes.addFlashAttribute("sex","男");
        return "redirect:http://localhost:8001/hello5";
    }
}
