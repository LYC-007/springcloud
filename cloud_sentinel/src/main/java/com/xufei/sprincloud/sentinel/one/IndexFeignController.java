package com.xufei.sprincloud.sentinel.one;

import com.xufei.sprincloud.sentinel.feign.OrderFeignService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 方案一:整合Openfeign，在Sentinel控制台和Controller层不设置任何流控规则，当远程服务器出现异常时在远程调用接口设置处理逻辑
 * application.yml
 * # 激活Sentinel对Feign的支持
 * feign:
 *   sentinel:
 *     enabled: true
 */
@RestController
public class IndexFeignController {
    @Resource
    private OrderFeignService orderFeignService;
    @GetMapping("/find/{id}")
    public String find(@PathVariable("id") Long id) {
        return orderFeignService.find(id);
    }

    @GetMapping("/time/{id}")
    public String timeOut(@PathVariable("id") Long id) {
        return orderFeignService.timeOut(id);
    }
}
