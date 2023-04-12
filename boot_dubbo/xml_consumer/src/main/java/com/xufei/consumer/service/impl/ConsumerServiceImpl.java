package com.xufei.consumer.service.impl;

import com.xufei.common.domain.UserAddress;
import com.xufei.common.service.ConsumerService;
import com.xufei.common.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsumerServiceImpl implements ConsumerService {
    @Autowired
    ProviderService providerService;

    @Override
    public List<UserAddress> initOrder(String userId) {
        System.out.println("用户id：" + userId);
        //1、查询用户的收货地址
        return providerService.getUserAddressList(userId);
    }
}
