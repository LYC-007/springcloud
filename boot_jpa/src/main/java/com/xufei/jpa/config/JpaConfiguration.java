package com.xufei.jpa.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Order(Ordered.HIGHEST_PRECEDENCE)//定义组件的加载顺序，这里为最高级
@Configuration//表明这是一个配置类
@EnableTransactionManagement(proxyTargetClass = true)//启用JPA的事物管理
@EnableJpaRepositories(basePackages = "com.xufei.jpa.repository")//启动JPA资源库并设置接口资源库的位置
@EntityScan(basePackages = "com.xufei.jpa.model")//实体类位置
public class JpaConfiguration {

    /**
     * 为什么要声明一个PersistenceExceptionTranslationPostProcessor 的Bean对象,有两作用：
     *      (1):用于被容器扫描：
     *      (2):捕获平台特定的异常并将它们重新抛出，作为Spring的一个未检查的异常。(用于事务的管理，例如捕获异常回滚)
     */
    @Bean
    PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}