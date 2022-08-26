package demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AttrController {

    @RequestMapping("/attr")
    public String doAttr(Model model){
        model.addAttribute("myaction","/user/login");
        model.addAttribute("mytext","请登录");
        model.addAttribute("selected",true);
        model.addAttribute("unselect",false);
        return "attr";
    }
}
