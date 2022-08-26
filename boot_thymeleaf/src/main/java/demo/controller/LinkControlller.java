package demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LinkControlller {


    @RequestMapping("/link/test")
    public String doLink(Model model){
        model.addAttribute("myname","zhangsan");
        return "link";
    }
}
