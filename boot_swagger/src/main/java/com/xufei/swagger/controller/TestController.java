package com.xufei.swagger.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @Api：修饰整个类，描述Controller的作用 （就是描述这个接类的作用）
 * @ApiOperation：描述一个类的一个方法，或者说一个接口 （就是描述接口类中这个方法的作用）
 * @ApiParam：单个参数描述
 * @ApiModel：用对象来接收参数
 * @ApiModelProperty：用对象接收参数时，描述对象的一个字段
 * @ApiImplicitParam：一个请求参数
 * @ApiImplicitParams：多个请求参数
 */
//访问地址：http://localhost:8080/swagger-ui.html


@Api("数据字典的接口")//修饰整个类，描述Controller的作用  （就是描述这个接类的作用）
@RestController
@RequestMapping("/admin")
public class TestController {
    @GetMapping("/login")
    @ApiOperation("根据ID查询子数据列表") //描述一个类的一个方法，或者说一个接口  （就是描述接口类中这个方法的作用）
    public String findChildData() {
        return "你好,Swagger！！！";
    }


    @ApiOperation(value = "登录", notes = "根据用户账号密码进行登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String", paramType = "query")})
    @GetMapping("/logins")
    public void login(String username, String password) {

        System.out.println(username+password);
        //......
    }
    @ApiOperation(value = "登录", notes = "根据用户账号密码进行登录")
    @GetMapping(value = "user")
    public void getUserByRoleName(
            @ApiParam(name="用户名",value="传入String格式",required=true) String username,
            @ApiParam(name="密码",value="传入String格式",required=true) String password) {

    }

    @ApiOperation(value="修改当前用户密码", notes="修改当前用户密码")
    @ApiImplicitParam(name = "user", value = "用户信息实体类", required = true, dataType = "User")
    @PutMapping(value = "user/password")
    public void updatePassword(@RequestBody User user) {
        System.out.println(user.toString());
    }

}

@ToString
@Data
class User{
    private String username;
    private String password;
}

@Data
@ApiModel(description = "数据字典")
class Dict {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")//用对象接收参数时，描述对象的一个字段
    private Long id;


    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}