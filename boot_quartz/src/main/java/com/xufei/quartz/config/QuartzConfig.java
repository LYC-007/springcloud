package com.xufei.quartz.config;

import com.sun.istack.internal.NotNull;
import org.quartz.Scheduler;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import java.io.IOException;
import java.util.Properties;
// SchedulerFactoryBeanCustomizer：在自动配置的基础上自定义配置需要实现的此接口。
@Configuration
public class QuartzConfig implements SchedulerFactoryBeanCustomizer {
    @Autowired
    private JobFactory jobFactory;

    @Bean
    public Properties properties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        // 对quartz.properties文件进行读取
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        // 在quartz.properties中的属性被读取并注入后再初始化对象
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setQuartzProperties(properties());
        return schedulerFactoryBean;
    }
    /*
     * 通过SchedulerFactoryBean获取Scheduler的实例
     */
    @Bean
    public Scheduler scheduler() throws IOException {
        return schedulerFactoryBean().getScheduler();
    }

    /*
     * quartz初始化监听器
     */
    @Bean
    public QuartzInitializerListener executorListener() {
        return new QuartzInitializerListener();
    }



    /**
     * 使用阿里的druid作为数据库连接池
     */
    @Override
    public void customize(@NotNull SchedulerFactoryBean schedulerFactoryBean) {
        schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContextKey");
        // 设置自定义Job Factory，用于Spring管理Job bean
        schedulerFactoryBean.setJobFactory(jobFactory);
        // 延迟2s启动quartz
        schedulerFactoryBean.setStartupDelay(2);
        // 设置自动启动，默认为true
        schedulerFactoryBean.setAutoStartup(true);
        // 启动时更新己存在的Job，这样就不用每次修改targetObject后删除qrtz_job_details表对应记录了
        schedulerFactoryBean.setOverwriteExistingJobs(true);
    }
}
