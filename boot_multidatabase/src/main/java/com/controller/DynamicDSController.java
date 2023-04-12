package com.controller;

import com.datasource.service.TransactionMangerService;
import com.datasource.service.service1.UserServiceV1;
import com.datasource.service.service2.UserServiceV2;
import com.datasource.service.service3.UserServiceV3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DynamicDSController {
    @Autowired
    private UserServiceV2 userServiceV2;
    @Autowired
    private UserServiceV1 userServiceV1;
    @Autowired
    private UserServiceV3 userServiceV3;

    @Autowired
    private TransactionMangerService transactionMangerService;
    /**
     * 测试在方法上指定数据源SECOND
     */
    @GetMapping("/1")
    public String test01(){
        userServiceV1.save_1();
        return "OK";
    }
    /**
     * 方法上未指定，类上未指定，包未指定
     */
    @GetMapping("/2")
    public String test02(){
        userServiceV1.save_2();
        return "OK";
    }
    /**
     * 测试 方法上未指定，类上指定SECOND，则类中的都是操作相同的数据源
     */
    @GetMapping("/3")
    public String test03() {
        userServiceV2.save_1();
        return "OK";
    }

    /**
     * 测试 方法上未指定，类上未指定
     */
    @GetMapping("/4")
    public String test04() {
        userServiceV3.save();
        return "OK";
    }

    /**
     * 测试 方法上未指定，类上未指定
     */
    @GetMapping("/5")
    public String test05() {
        transactionMangerService.SlaveAndMasterInsert();
        return "OK";
    }
}
