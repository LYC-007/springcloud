package com.xufei.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@MapperScan("com.xufei.mapper")
@Configuration
public class MybatisPlusConfig {
    /**
     * 仅支持 updateById(id) 与 update(entity, wrapper) 方法
     * 乐观锁字段支持的数据类型只有:int,Integer,long,Long,Date,Timestamp,LocalDateTime
     * 在实体类的字段上加上@Version注解
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {//乐观锁插件
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }
}
