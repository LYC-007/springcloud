package com.global.format.controller;

import com.alibaba.fastjson.JSON;
import com.global.format.entity.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping
public class GlobalFormattingTimeController {

    @RequestMapping("/test")
    public OrderDTO timeTest() {
        OrderDTO dto = new OrderDTO();
        dto.setCreateTime(LocalDateTime.now());
        dto.setUpdateTime(new Date());

        log.info(JSON.toJSONString(dto));
        return dto;
    }
}
