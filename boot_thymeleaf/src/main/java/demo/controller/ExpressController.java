package demo.controller;


import demo.domain.School;
import demo.domain.Student;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExpressController {

    @RequestMapping("/express-var")
    public String express_var(Model model){
        //添加简单类型的数据
        model.addAttribute("myname","张三");
        model.addAttribute("myage",20);

        //添加对象类型
        Student student = new Student();
        student.setId(1001);
        student.setName("李向");
        student.setEmail("lixiang@qq.com");
        student.setAge(26);
        student.sex="男";

        //创建shool对象
        School school = new School();
        school.setSchname("蛙课网");
        school.setSchaddress("北京");
        student.setMySchool(school);



        model.addAttribute("student",student);

        return "var1";

    }


    @RequestMapping("/i18N")
    public String  i18n(Model model){
        return "login";
    }
}
