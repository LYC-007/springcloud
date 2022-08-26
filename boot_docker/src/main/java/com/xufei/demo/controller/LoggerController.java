package com.xufei.demo.controller;

/**
 * 日志测试Controller
 */
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller

@Slf4j
public class LoggerController {
    /**
     * 配合日志文件 logback.xml一起使用
     */

    private final static Logger logger = LoggerFactory.getLogger(LoggerController.class);

    public static void main(String[] args) {
        logger.info("进来了啊");
        logger.info("进来了啊");
        logger.info("进来了啊");
    }
}
