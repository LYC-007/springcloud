package com.xufei.springcloud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;


@Controller
public class ResponseController {
    @GetMapping("/hello0")
    public ModelAndView hello(ModelAndView modelAndView) {
    /**
    *
    * 构造方法:
    *      ModelAndView()  //默认构造函数豆式的用法：填充bean的属性，而不是将在构造函数中的参数。
    *      ModelAndView(String viewName)  //方便的构造时，有没有模型数据暴露。
    *      ModelAndView(String viewName, Map model)  //给出创建一个视图名称和模型新的ModelAndView。
    *      ModelAndView(String viewName, String modelName, Object modelObject)  //方便的构造采取单一的模式对象。
    *      ModelAndView(View view)   //构造方便在没有模型数据暴露。
    *      ModelAndView(View view, Map model)  //创建给定一个视图对象和模型，新的ModelAndView。
    *      ModelAndView(View view, String modelName, Object modelObject)   //方便的构造采取单一的模式对象。
    *类方法:
    *      ModelAndView  addAllObjects(Map modelMap)  //添加包含在所提供的地图模型中的所有条目。
    *      ModelAndView  addObject(Object modelObject)  //添加对象使用的参数名称生成模型。
    *      ModelAndView  addObject(String modelName,ObjectmodelObject)  //对象添加到模型中。
    *      void  clear()  //清除此ModelAndView对象的状态。
    *      Map  getModel()  //返回的模型图。
    *      protectedMap  getModelInternal()  //返回的模型图。
    *      ModelMap  getModelMap()  //返回底层ModelMap实例（从不为null）。
    *      View  getView()  //返回View对象，或者为null，如果我们使用的视图名称由通过一个ViewResolverDispatcherServlet会得到解决。
    *      String  getViewName()  //返回视图名称由DispatcherServlet的解决，通过一个ViewResolver，或空，如果我们使用的视图对象。
    *      boolean  hasView()  //指示此与否的ModelAndView有一个观点，无论是作为一个视图名称或作为直接查看实例。
    *      boolean  isEmpty()  //返回此ModelAndView对象是否为空，即是否不持有任何意见，不包含模型。
    *      boolean  isReference()  //返回，我们是否使用视图的参考，i.e.
    *      void  setView(Viewview)  //设置此ModelAndView的视图对象。
    *      void  setViewName(StringviewName)  //此ModelAndView的设置视图名称，由通过一个ViewResolverDispatcherServlet会得到解决。
    *      String  toString()  //返回这个模型和视图的诊断信息。
    *      boolean  wasCleared()??  //返回此ModelAndView对象是否为空的调用的结果，以清除（），即是否不持有任何意见，不包含模型。
    *
    */
        //ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("hello");  //返回到那个文件
        modelAndView.addObject("name", "派大星");
        modelAndView.addObject("sex", "男");
        modelAndView.addObject("age", 53);
        System.out.println(modelAndView);
        return modelAndView;
    }

    @RequestMapping("/hello1")   //意为请求 localhost:8080/hello
    public String hello1(Model model) {
        //封装数据（向模型中添加数据，可以jsp页面直接取出并渲染）
        model.addAttribute("name", "张三");
        model.addAttribute("sex", "男");
        model.addAttribute("age", 23);
        System.out.println(model);
        //会被视图解析器处理
        return "hello";   //返回到哪个页面
    }
    @RequestMapping("/hello2")
    public String hello2(Map<String, String> map) {
        map.put("name", "李四");
        map.put("sex", "男");
        map.put("age","23");
        //会被视图解析器处理
        return "hello";   //返回到哪个页面
    }
}




