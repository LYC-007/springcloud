package demo.controller;


import com.xufei.demo.domain.MyStudent;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BodyController {

    @RequestMapping("/body/text")
    public String doText(Model model){
        model.addAttribute("msg","学习的开发语言<b>java</b>");
        model.addAttribute("content","全栈开发<b>vue</b>,<b>java</b>,<b>mysql</b>");
        return "body";

    }

    @RequestMapping("/body/each")
    public String doEach(Model model){
        List<MyStudent> list = new ArrayList<>();
        list.add( new MyStudent(1001,"刘备",30));
        list.add(new MyStudent(1002,"关羽",26));
        list.add(new MyStudent(1003,"张飞",22));
        list.add(new MyStudent(1004,"黄忠",29));
        list.add(new MyStudent(1005,"马超",24));
        model.addAttribute("students",list);

        //创建Map
        Map<String,String> stumap = new HashMap<>();
        stumap.put("id","学生编号1001");
        stumap.put("name","学生名称张飒");
        stumap.put("age","20");
        stumap.put("classname","java高级一班");
        model.addAttribute("stumap",stumap);

        // list -map结构
        List<Map<String, MyStudent>> listmap = new ArrayList<>();
        MyStudent s1 = new MyStudent(1001,"李四",20);
        MyStudent s2 = new MyStudent(1002,"周芳",21);

        Map<String,MyStudent> map = new HashMap<>();
        map.put("stu-1-1",s1);
        map.put("stu-1-2",s2);
        listmap.add(map);

        //创建map 和MyStudent
        map = new HashMap<>();
        MyStudent s3 = new MyStudent(1003,"周宇",28);
        MyStudent s4= new MyStudent(1004,"张力",23);
        MyStudent s5 = new MyStudent(1005,"李思维",19);
        map.put("stu-2-1",s3);
        map.put("stu-2-2",s4);
        map.put("stu-2-3",s5);
        listmap.add(map);

        model.addAttribute("listmap",listmap);

        //数组
        String names[]= new String[]{"刘备","曹操","孙权"};
        model.addAttribute("names",names);

        //Map<String,String> 给select准备的数据
        Map<String,String> citys  = new HashMap<>();
        citys.put("010","北京");
        citys.put("021","上海");
        citys.put("022","天津");
        citys.put("023","重庆");
        model.addAttribute("citys",citys);
        //指定select中选中的值
        model.addAttribute("choice","北京");

        return "each";

    }

    //判断if
    @RequestMapping("/body/if")
    public String doIf(Model model){
        //true
        model.addAttribute("old",true);
        model.addAttribute("login","login");
        model.addAttribute("num1",10);
        model.addAttribute("num2",-2);
        model.addAttribute("obj1", new MyStudent());
        model.addAttribute("str0","");

        //false
        model.addAttribute("yong",false);
        model.addAttribute("str1","off");
        model.addAttribute("str2","no");
        model.addAttribute("str3","false");
        model.addAttribute("num3",0);
        model.addAttribute("obj2",null);


        return "if";
    }

    @GetMapping("/body/tpl")
    public String doTemplate(Model model){
        model.addAttribute("tplname","menu");

        return "frag/main";
        //return "frag/tpl";
        //return "frag/rem";
    }

    //inline
    @GetMapping("/body/inline")
    public String  doInline(Model model){
        List<MyStudent> students = new ArrayList<>();
        MyStudent s1 = new MyStudent(1001,"刘备",28);
        MyStudent s2 = new MyStudent(1002,"关羽",26);
        MyStudent s3 = new MyStudent(1003,"张飞",24);
        students.add(s1);
        students.add(s2);
        students.add(s3);

        model.addAttribute("stulist",students);
        model.addAttribute("name","三国演义");
        model.addAttribute("info","我爱<b>中国</b>");

        return "inline";


    }
}
