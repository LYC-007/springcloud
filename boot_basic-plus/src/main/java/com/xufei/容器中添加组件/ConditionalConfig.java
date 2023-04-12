package com.xufei.容器中添加组件;

import com.xufei.domain.Computer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ConditionalOnBean // 如果有容器中有Computer类,就注入备用电脑Computer类,如果没有Computer类就不注入备用电脑Computer
 * @ConditionalOnMissingBean // 当给定的在bean不存在时,则实例化当前Bean，感觉这个是在多态环境下使用，当一个接口有多个实现类时，如果只希望它有一个实现类，那就在各个实现类上加上这个注解
 * @ConditionalOnClass // 当给定的类名在类路径上存在，则实例化当前Bean
 * @ConditionalOnMissingClass // 当给定的类名在类路径上不存在，则实例化当前Bean
 */
/**
 * 1、配置类里面使用@Bean标注在方法上给容器注册组件，默认也是单实例的
 * 2、配置类本身也是组件 ;
 *         如果@Configuration(proxyBeanMethods = true)通过代理对象调用方法获取组件实例。SpringBoot总会检查这个组件的实例是否在容器中有。因为要保持组件单实例
 * 3、proxyBeanMethods：代理bean的方法
 *      Full(proxyBeanMethods = true)、【保证每个@Bean方法被调用多少次返回的组件都是单实例的，就是保证用户的“pet” 和容器中的 “pet” 是一样的】
 *      Lite(proxyBeanMethods = false)【每个@Bean方法被调用多少次返回的组件都是新创建的，如果容器中的组件没有依赖关系，建议使用这个，启动时会快点】
 *      组件依赖必须使用Full模式默认。其他默认是Lite模式
 */
@Configuration(proxyBeanMethods = true) //告诉SpringBoot这是一个配置类 == 配置文件
public class ConditionalConfig {
    @Bean(name = "notebookPC")
    public Computer computer1(){
        return new Computer("笔记本电脑");
    }
    /**
     * 配置类中有两个Computer类的bean，一个是笔记本电脑，一个是备用电脑。
     * 如果当前容器中已经有电脑bean了，就不注入备用电脑，如果没有，则注入备用电脑，这里需要使用到
     */
    @ConditionalOnMissingBean(Computer.class)
    @Bean("reservePC")
    public Computer computer2(){
        return new Computer("备用电脑");
    }
}
