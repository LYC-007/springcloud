package com.xufei.springcloud.openfeign.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
/**
 * @Author: XuFei
 * @Date: 2022/8/23 14:33
 */
@Component
@FeignClient(value = "cloud-provide")//cloud_provide
public interface OrderFeignService {
    @GetMapping("/order/find/{id}")
    String find(@PathVariable("id") Long id);
    /**
     * 超时测试
     * @param id
     * @return
     */
    @GetMapping("/order/time/{id}")
    String timeOut(@PathVariable("id")Long id);
}
