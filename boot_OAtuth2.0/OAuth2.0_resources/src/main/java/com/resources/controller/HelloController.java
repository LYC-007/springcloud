package com.resources.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HelloController
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "HelloController--> hello() 可直接访问";
    }

}
