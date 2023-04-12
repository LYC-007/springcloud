package com.javaedit.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.javaedit.entity.Order;
import com.javaedit.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class OrderService extends ServiceImpl<OrderMapper, Order> {

    public void insertList() {
        for (int i = 1; i < 100; i++) {
            Order order = new Order();
            order.setGoodsId("iphone13_"+i);
            order.setNumber(3+i);
            order.setCreateTime(new Date());
            this.save(order);
        }
    }

}
