package com.xufei.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.xufei.security.mapper")
public class MybatisPlusConfig {


}
