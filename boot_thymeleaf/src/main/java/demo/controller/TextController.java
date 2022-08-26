package demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TextController {

    @RequestMapping("/text")
    public String doText(Model model){
        model.addAttribute("info","语言java 和 go");
        model.addAttribute("n1","zhangsan");
        model.addAttribute("n2","lisi");
        return "text";

    }

    @GetMapping("/num")
    public String doNum(Model model){
        model.addAttribute("n1",100);
        model.addAttribute("n2",20);

        return "num";
    }

    @GetMapping("/bool")
    public String doBool(Model model){
        model.addAttribute("age",16);
        model.addAttribute("married",true);
        return "bool";

    }

    @GetMapping("/null")
    public String doNull(Model model){
        model.addAttribute("name",null);
        model.addAttribute("myname","");
        return "null";
    }

    @GetMapping("/comp")
    public String doComp(Model model){
        model.addAttribute("n1",100);
        model.addAttribute("n2",20);
        model.addAttribute("islogin",false);
        return "comp";
    }

    @GetMapping("/ternary")
    public String ternary(Model model){
        model.addAttribute("age",20);
        model.addAttribute("islogin",true);
        return "ternary";
    }
}
