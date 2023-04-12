package com.xufei.quartz.listener;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.listeners.JobListenerSupport;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class CustomGlobalJobListener extends JobListenerSupport {
    /**
     * 获取该JobListener的名称
     * @return
     */
    @Override
    public String getName() {
        String name = getClass().getSimpleName();
        log.info("Job监听器名字:{}",name);
        return name;
    }

    /**
     * Job将要被执行
     */
    @Override
    public void jobToBeExecuted(JobExecutionContext jobExecutionContext) {
        JobDetail jobDetail=jobExecutionContext.getJobDetail();
        JobKey jobKey=jobDetail.getKey();
        Trigger trigger=jobExecutionContext.getTrigger();
        log.info("Job将要被执行, jobName: {}, jobGroup: {}, jobDetail: {}, trigger: {}",jobKey.getName(),jobKey.getGroup(),
                JSON.toJSONString(jobDetail), JSON.toJSONString(trigger));
    }

    /**
     * Job被拒绝执行
     */
    @Override
    public void jobExecutionVetoed(JobExecutionContext jobExecutionContext) {
        JobDetail jobDetail=jobExecutionContext.getJobDetail();
        JobKey jobKey=jobDetail.getKey();
        log.info("Job被TriggerListener拒绝执行, jobName: {}, jobGroup: {}, jobDetail: {}",jobKey.getName(),jobKey.getGroup(), JSON.toJSONString(jobDetail));
    }

    /**
     * Job执行后
     */
    @Override
    public void jobWasExecuted(JobExecutionContext jobExecutionContext, JobExecutionException e) {
        JobDetail jobDetail=jobExecutionContext.getJobDetail();
        JobKey jobKey=jobDetail.getKey();
        log.info("Job执行完成, jobName: {}, jobGroup: {}, jobDetail: {}",jobKey.getName(),jobKey.getGroup(), JSON.toJSONString(jobDetail));
    }
}