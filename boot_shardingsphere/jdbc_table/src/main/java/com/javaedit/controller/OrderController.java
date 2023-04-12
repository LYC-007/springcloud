package com.javaedit.controller;

import com.javaedit.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/order")
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;


    @GetMapping("/list")
    public Object list() {
        return orderService.list();
    }

    /**
     * 试试不同id看数据落在哪个tb_order表吧
     */
    @GetMapping("/save")
    public Object insertList() {
        orderService.insertList();
        return "success";
    }
}
