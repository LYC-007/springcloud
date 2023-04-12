package com.plus01.netty.standard;

import com.plus01.netty.annotation.ServerEndpoint;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Set;

/**
 * 通过ClassPathBeanDefinitionScanner实现使用自定义注解进行bean注入
 */
public class EndpointClassPathScanner extends ClassPathBeanDefinitionScanner {

    public EndpointClassPathScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters) {
        super(registry, useDefaultFilters);
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        addIncludeFilter(new AnnotationTypeFilter(ServerEndpoint.class));
        return super.doScan(basePackages);
    }
}