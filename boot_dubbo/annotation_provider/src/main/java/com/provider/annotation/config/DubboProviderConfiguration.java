package com.provider.annotation.config;

import com.alibaba.dubbo.config.*;
import com.xufei.common.service.ProviderService;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 注解方式配置
 *
 * 详细配置请查看官网:https://cn.dubbo.apache.org/zh/docs3-v2/java-sdk/reference-manual/config/properties/#registry
 */
@Configuration
public class DubboProviderConfiguration {

    @Bean // #1 服务提供者信息配置,服务提供者缺省值配置
    public ProviderConfig providerConfig() {
        ProviderConfig providerConfig = new ProviderConfig();
        providerConfig.setTimeout(1000);
        return providerConfig;
    }

    @Bean // #2 分布式应用信息配置,每个应用必须要有且只有一个 application 配置
    public ApplicationConfig applicationConfig() {
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("annotation-provider");
        return applicationConfig;
    }

    @Bean // #3 注册中心信息配置
    public RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setProtocol("zookeeper");
        registryConfig.setAddress("192.168.236.131");
        registryConfig.setPort(2181);
        return registryConfig;
    }

    @Bean // #4 使用协议配置，这里使用 dubbo
    public ProtocolConfig protocolConfig() {
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName("dubbo");
        protocolConfig.setPort(20882);
        return protocolConfig;
    }
    @Bean// #5服务提供者暴露服务配置。对应的配置类：org.apache.dubbo.config.ServiceConfig
    public ServiceConfig<ProviderService> providerServiceConfig(ProviderService providerService){
        ServiceConfig<ProviderService> serviceConfig = new ServiceConfig<>();
        serviceConfig.setInterface(ProviderService.class);
        serviceConfig.setRef(providerService);//服务对象实现引用
        //serviceConfig.setVersion("1.0.0");//服务版本，建议使用两位数字版本，如：1.0，通常在接口不兼容时版本号才需要升级
        serviceConfig.setGroup("lwt");//服务分组，当一个接口有多个实现，可以用分组区分

        //配置每一个method的信息
        MethodConfig methodConfig = new MethodConfig();
        methodConfig.setName("getUserAddressList");
        methodConfig.setTimeout(1000);

        //将method的设置关联到service配置中
        List<MethodConfig> methodsList = new ArrayList<>();
        methodsList.add(methodConfig);
        serviceConfig.setMethods(methodsList);
        return serviceConfig;
    }
}
