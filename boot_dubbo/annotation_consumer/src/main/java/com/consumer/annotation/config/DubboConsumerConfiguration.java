package com.consumer.annotation.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.xufei.common.service.ProviderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 注解配置类
 *
 * 详细配置请查看官网:https://cn.dubbo.apache.org/zh/docs3-v2/java-sdk/reference-manual/config/properties/#registry
 */
@Configuration
public class DubboConsumerConfiguration {
    @Bean // 应用配置  每个应用必须要有且只有一个 application 配置，对应的配置类：org.apache.dubbo.config.ApplicationConfig
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
//        applicationConfig.setOwner("1113193990@qq.com");//可选  应用负责人，用于服务治理，请填写负责人公司邮箱前缀
//        applicationConfig.setOrganization("china");//组织名称(BU或部门)，用于注册中心区分服务来源，此配置项建议不要使用autoconfig，直接写死在配置中，比如china,intl,itu,crm,asc,dw,aliexpress等
        applicationConfig.setName("annotation-consumer");//必填 当前应用名称，用于注册中心计算应用间依赖关系
        return applicationConfig;
    }

    @Bean // 服务消费者配置   服务消费者缺省值配置。配置类： org.apache.dubbo.config.ConsumerConfig 。
    public ConsumerConfig consumerConfig() {
        ConsumerConfig consumerConfig = new ConsumerConfig();
//        consumerConfig.setTimeout(3000);  // 远程服务调用超时时间(毫秒)
//        consumerConfig.setRetries(3);  //远程服务调用重试次数，不包括第一次调用，不需要重试请设为0,仅在cluster为failback/failover时有效
//        consumerConfig.setLoadbalance("random"); //负载均衡策略，可选值：random - 随机;roundrobin - 轮询;leastactive - 最少活跃调用;consistenthash - 哈希一致 (2.1.0以上版本);shortestresponse - 最短响应 (2.7.7以上版本);
//        consumerConfig.setAsync(false);//是否缺省异步执行，不可靠异步，只是忽略返回值，不阻塞执行线程 默认false
//        consumerConfig.setConnections(100); //每个服务对每个提供者的最大连接数，rmi、http、hessian等短连接协议支持此配置，dubbo协议长连接不支持此配置
//        consumerConfig.setOwner("1113193990@qq.com");  //调用服务负责人，用于服务治理，请填写负责人公司邮箱前缀
        consumerConfig.setCheck(false);//启动时检查提供者是否存在，true报错，false忽略

        return consumerConfig;
    }

    @Bean // 配置注册中心
    public RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setProtocol("zookeeper");
        registryConfig.setAddress("192.168.236.131");
        registryConfig.setPort(2181);
        return registryConfig;
    }
    @Bean
    public ReferenceConfig<ProviderService> referenceConfig(){
        ReferenceConfig<ProviderService> referenceConfig=new ReferenceConfig<>();
        referenceConfig.setGroup("lwt");
        referenceConfig.setTimeout(1000);
        referenceConfig.setInterface(ProviderService.class);
        return referenceConfig;
    }
}
