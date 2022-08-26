package com.xufei.springcloud.openfeign.controller;

import com.xufei.springcloud.openfeign.feign.OrderFeignService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: XuFei
 * @Date: 2022/8/23 14:37
 */
@RestController
@RequestMapping("/index")
public class IndexController {
    @Resource
    private OrderFeignService orderFeignService;
    @GetMapping("/find/{id}")
    public String find(@PathVariable("id")Long id){
        return orderFeignService.find(id);
    }

    @GetMapping("/time/{id}")
    public String timeOut(@PathVariable("id")Long id){
        return orderFeignService.timeOut(id);
    }
}
