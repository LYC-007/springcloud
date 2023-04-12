package com.xufei.quartz.config;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

@Component
public class JobFactory extends AdaptableJobFactory {
    //AutowireCapableBeanFactory 可以将一个对象添加到 SpringIOC 容器中， 并且完成该对象注入，解决spring不能在quartz中注入bean的问题
    @Autowired
    private AutowireCapableBeanFactory autowireCapableBeanFactory;

    /*** 该方法需要将实例化的任务对象手动的添加到 springIOC 容器中并且完成对 象的注入 */
    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        Object obj = super.createJobInstance(bundle);
        //将 obj 对象添加 Spring IOC 容器中，并完成注入，否则定时任务调用业务对象会报错
        this.autowireCapableBeanFactory.autowireBean(obj);
        return obj;
    }

}