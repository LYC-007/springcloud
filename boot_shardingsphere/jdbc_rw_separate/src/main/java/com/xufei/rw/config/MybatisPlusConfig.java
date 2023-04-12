package com.xufei.rw.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;


@MapperScan("com.xufei.rw.mapper")
@Configuration
public class MybatisPlusConfig {
}
