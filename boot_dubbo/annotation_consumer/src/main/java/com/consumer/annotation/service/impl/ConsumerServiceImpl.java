package com.consumer.annotation.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.xufei.common.domain.UserAddress;
import com.xufei.common.service.ConsumerService;
import com.xufei.common.service.ProviderService;

import java.util.List;


@Service
public class ConsumerServiceImpl implements ConsumerService {
    @Reference //dubbo提供了@Reference注解，可替换@Autowired注解，用于引入远程服务
    ProviderService providerService;

    @Override
    public List<UserAddress> initOrder(String userId) {

        System.out.println("用户id：" + userId);
        //1、查询用户的收货地址
        return providerService.getUserAddressList(userId);
    }
}
