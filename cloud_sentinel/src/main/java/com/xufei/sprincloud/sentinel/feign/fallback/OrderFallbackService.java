package com.xufei.sprincloud.sentinel.feign.fallback;

import com.xufei.sprincloud.sentinel.feign.OrderFeignService;
import org.springframework.stereotype.Component;

/**
 * @Author: XuFei
 * @Date: 2022/8/23 20:07
 */

@Component
public class OrderFallbackService implements OrderFeignService {
    @Override
    public String find(Long id) {
        return "find:fallback";
    }

    @Override
    public String timeOut(Long id) {
        return "timeOut:fallback";
    }
}
