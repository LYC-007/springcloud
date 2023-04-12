package com.lkadoc.controller;


import com.lk.api.annotation.LKAMethod;
import com.lk.api.annotation.LKARespose;
import com.lk.api.annotation.LKAResposes;
import com.lk.api.annotation.LKAType;
import com.lk.api.constant.ContentType;
import com.lkadoc.model.User;
import com.lkadoc.result.ApiResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 简单的响应参数示例
 */
@LKAType(value="用户业务模块")
@RestController
@RequestMapping("business")
public class LKADemoController2 {

    @LKAMethod("简单的响应出参")
    @LKARespose(names= {"code","msg","data"},values= {"状态码","消息","数据"})
    @GetMapping("getInfo")
    public Map<String,Object> getInfo() {
        Map<String,Object> map = new HashMap<>();
        map.put("code",200);
        map.put("msg","获取信息成功");
        map.put("data","响应数据");
        return map;
    }


    //不管是入参还是出参如果是一个对象，且对象有加@LKAModel及属性有加@LKAProperty注解，那么Lkadoc会自动扫描该出参对象
    @LKAMethod(value="对象出参",contentType= ContentType.JSON)
    @PostMapping("getUser")
    public User getUser(@RequestBody User user) {
        return user;
    }
    @LKAMethod(value="复杂的Map结构响应参数")
    @LKAResposes({
            @LKARespose(names= {"code","msg"},values= {"状态码","消息"}),
            @LKARespose(name="total",value="总记录数",parentName="result",parentValue="响应数据"),
            @LKARespose(type=User.class,parentName="users",parentIsArray=true,parentValue="用户对象列表",grandpaName="result")
    })
    @GetMapping("getMap")
    public Map<String,Object> getMap() {
        Map<String,Object> map = new HashMap<>();
        map.put("code",200);
        map.put("msg","操作成功！");
        Map<String,Object> data = new HashMap<>();
        data.put("total",10);
        List<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setName("张三");
        User user2 = new User();
        user2.setName("李四");
        users.add(user1);
        users.add(user2);
        data.put("users",users);
        map.put("result",data);
        return map;
    }
    @LKAMethod(value="超过3层嵌套结构用法技巧")
    @LKAResposes({
            @LKARespose(name="a",value="一级"),
            @LKARespose(name="b",value="二级",parentName="a"),
            @LKARespose(name="c",value="三级",parentName="b"),
            @LKARespose(name="d",value="四级",parentName="c")
    })
    @GetMapping("getMoreMap")
    public Map<String,Object> getMoreMap(){
        Map<String,Object> mapa= new HashMap<>();
        Map<String,Object> mapb= new HashMap<>();
        Map<String,Object> mapc= new HashMap<>();
        Map<String,Object> mapd= new HashMap<>();
        mapa.put("a",mapb);
        mapb.put("b",mapc);
        mapc.put("c",mapd);
        mapd.put("d",1);
        return mapa;
    }
    /**
     这个方法其实和上面复杂的Map结构响应参数示例是一样的，不一样的地方是一个是Map,一个是ApiResut对象。但是我们发现这个方法在响应参数描述是少用一个注解：
     @LKARespose(names= {"code","msg"},values= {"状态码","消息"})
     这是因为ApiResult对象已经通过@LKAProperty注解描述过"code","msg"属性了，Lkadoc会去自动扫描带有@LKAModel注解的响应对象。还有如果@LKARespose注解描述的参数和对象里面的属性一致的话，@LKARespose注解描述的参数会覆盖掉对象里面的属性
     */
    @LKAMethod(value="复杂的对象结构响应参数")
    @LKAResposes({
            @LKARespose(name="total",value="总记录数",parentName="result",parentValue="响应数据"),
            @LKARespose(type=User.class,parentName="users",parentIsArray=true,parentValue="用户对象列表",grandpaName="result")
    })
    @PostMapping("getObj")
    public ApiResult getObj() {
        List<User> users = new ArrayList<User>();
        User user1 = new User();
        user1.setName("张三");
        User user2 = new User();
        user2.setName("李四");
        users.add(user1);
        users.add(user2);
        return ApiResult.success().put("total",10).put("users",users);
    }
    //这个方法上面复杂的对象结构响应参数测试方法是一样的，唯一不同的是在type=User.class增加了group = "getUser"属性配置，代表只展示getUser组的数据
    @LKAMethod(value="响应参数对象参数分组")
    @LKAResposes({
            @LKARespose(name="total",value="总记录数",parentName="result",parentValue="响应数据"),
            @LKARespose(type=User.class,group = "getUser",parentName="users",parentIsArray=true,
                    parentValue="用户对象列表",grandpaName="result")
    })
    @PostMapping("getObj2")
    public ApiResult getObj2() {
        List<User> users = new ArrayList<User>();
        User user1 = new User();
        user1.setName("张三");
        User user2 = new User();
        user2.setName("李四");
        users.add(user1);
        users.add(user2);
        return ApiResult.success().put("total",10).put("users",users);
    }
}