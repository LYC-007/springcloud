package com.xufei.monitor.controller;

import com.xufei.monitor.model.Orders;
import com.xufei.monitor.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class OrdersController {

    @Autowired
    OrdersRepository ordersRepository;

    @RequestMapping("/saveAll")
    public String saveAll(){
        Orders orders=null;
        List<Orders> ordersList=new ArrayList<>(200);
        for(int i=0;i<200;i++){
            orders=new Orders();
            //订单表的主键应该使用雪花算法之类生成,雪花算法生成的id是有序的，这样索引查询起来会快很多,uuid是无序的,查询效率极低。。。。
            orders.setId(i);
            orders.setOrderName("订单_"+i);
            ordersList.add(orders);
        }
        ordersRepository.saveAll(ordersList);
        return "success";
    }

}
