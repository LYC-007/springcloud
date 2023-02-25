package com.xufei.controller;

import com.xufei.utils.R;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Max;

/**
 * 验证请求参数(Path Variables 和 Request Parameters)
 *
 * 一定一定不要忘记在类上加上 Validated 注解了，这个参数可以告诉 Spring 去校验方法参数。如果不加下面的@Valid注解不起作用
 */
@RestController
@RequestMapping("/api")
@Validated//一定一定不要忘记在类上加上 Validated 注解了，这个参数可以告诉 Spring 去校验方法参数。如果不加下面的@Valid注解不起作用
public class PersonControllers {
    @GetMapping("/person/{id}")
    public R getPersonByID(@Valid @PathVariable("id") @Max(value = 5, message = "超过 id 的范围了") Integer id) {
        return R.ok();
    }
}