package com.xufei.sprincloud.sentinel.feign;

import com.xufei.sprincloud.sentinel.feign.fallback.OrderFallbackService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * fallback = OrderFallbackService.class,当远程调用的服务器宕机时，如果还有大量的访问来到当前服务器，那么当前这个服务器将会被卡死
 * 在OrderFallbackService类里面设置兜底方案
 */
@Component
@FeignClient(value = "cloud-provide",fallback = OrderFallbackService.class)//cloud_provide
public interface OrderFeignService {
    @GetMapping("/order/find/{id}")
    String find(@PathVariable("id") Long id);

    @GetMapping("/order/time/{id}")
    String timeOut(@PathVariable("id")Long id);
}
