package com.xufei.sprincloud.sentinel.two.blockhandler;

import com.alibaba.csp.sentinel.slots.block.BlockException;

/**
 * @Author: XuFei
 * @Date: 2022/8/23 20:16
 */
public class CustomerBlockHandler {
    public static String handleException(BlockException exception){
        return "自定义的限流处理信息......CustomerBlockHandler";
    }
}
