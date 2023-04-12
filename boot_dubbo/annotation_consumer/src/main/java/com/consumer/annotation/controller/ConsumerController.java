package com.consumer.annotation.controller;

import com.xufei.common.domain.UserAddress;
import com.xufei.common.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ConsumerController {

    @Autowired
    private ConsumerService consumerService;

    @GetMapping("/initOrder")
    public List<UserAddress> initOrder() {
        String userId="250";
        return consumerService.initOrder(userId);
    }
}
