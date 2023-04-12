package com.xufei.consumer.controller;

import com.xufei.common.domain.UserAddress;
import com.xufei.common.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ConsumerController {

    @Autowired
    private ConsumerService consumerService;

    @GetMapping("/initOrder")
    public List<UserAddress> initOrder(@RequestParam("uid")String userId) {
        return consumerService.initOrder(userId);
    }
}
