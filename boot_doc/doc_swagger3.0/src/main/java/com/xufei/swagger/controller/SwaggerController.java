package com.xufei.swagger.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "SwaggerController")
@RequestMapping("api")
public class SwaggerController {


    @GetMapping("hello")
    @ApiOperation(value="hello 方法", notes="hello Swagger测试方法--hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("test1")
    @ApiOperation(value="test1 方法", notes="hello Swagger测试方法--test1")
    public String test1(){
        return "test1";
    }

    @GetMapping("test2")
    @ApiOperation(value="test2 方法", notes="hello Swagger测试方法-test2")
    public String test2(){
        return "test2";
    }
}