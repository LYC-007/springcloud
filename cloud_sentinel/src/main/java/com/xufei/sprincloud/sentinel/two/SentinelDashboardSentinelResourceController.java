package com.xufei.sprincloud.sentinel.two;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.xufei.sprincloud.sentinel.two.blockhandler.CustomerBlockHandler;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 方案二:Sentinel控制台+@SentinelResource，直接在Controller层就进行限流
 */
@RestController
public class SentinelDashboardSentinelResourceController {
    /**
     * 方式一:兜底方法在Controller层造成代码臃肿
     */
    @GetMapping("/testHotKey")
    @SentinelResource(value = "testHotKey", blockHandler = "dealHandler_testHotKey")
    public String testHotKey(@RequestParam(value = "p1", required = false) String p1,
                             @RequestParam(value = "p2", required = false) String p2) {
        return "------testHotKey";
    }

    public String dealHandler_testHotKey(String p1, String p2, BlockException exception) {
        return "-----dealHandler_testHotKey";
    }

    /**
     * 方法二:兜底方法和业务方法分开
     *
     * 自定义通用的限流处理逻辑
     * value = "customerBlockHandler"要和Sentinel控制台的资源名对应
     * blockHandlerClass = CustomerBlockHandler.class
     * blockHandler = handleException
     * 上述配置：找CustomerBlockHandler类里的handleException方法进行兜底处理
     * 自定义通用的限流处理逻辑
     */
    @GetMapping("/rateLimit/1")
    @SentinelResource(value = "customerBlockHandler",blockHandlerClass = CustomerBlockHandler.class, blockHandler = "handleException")
    public String customerBlockHandler() {
        return  "按客户自定义限流处理逻辑";
    }

    /**
     * 方法二:兜底方法和业务方法分开
     *
     * 自定义通用的限流处理逻辑
     * value = "customerBlockHandler"要和Sentinel控制台的资源名对应
     * blockHandlerClass = CustomerBlockHandler.class
     * blockHandler = handleException
     * 上述配置：找CustomerBlockHandler类里的handleException方法进行兜底处理
     * 自定义通用的限流处理逻辑
     */
    @GetMapping("/rateLimit/byUrl")
    @SentinelResource(value = "customer",blockHandlerClass = CustomerBlockHandler.class,
            blockHandler = "handleException")
    public String customerBlockHandler2() {
        return  "按客户自定义限流处理逻辑";
    }
}
