package com.xufei.provicer.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xufei.provicer.domain.SysQuartz;
import com.xufei.mapper.SysQuartzMapper;
import com.xufei.provicer.service.SysQuartzService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 定时任务信息表 服务实现类
 */
@Slf4j
@Service
public class SysQuartzServiceImpl extends ServiceImpl<SysQuartzMapper, SysQuartz> implements SysQuartzService {

    @Autowired
    private Scheduler scheduler;

    private static Job getClass(String className) throws Exception {
        Class<?> aClass = Class.forName(className);
        return (Job) aClass.newInstance();
    }

    @Override
    public Boolean addJob(SysQuartz job) {
        try {
            QueryWrapper<SysQuartz> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("class_name", job.getClassName());
            queryWrapper.ge("", job.getDeleteStatus());//比

            List<SysQuartz> sysQuartzList = list(queryWrapper);
            if (null != sysQuartzList && !sysQuartzList.isEmpty()) {
                return false;
            }
            save(job);
            JSONObject data = job.getJsonObjectData();
            log.info("当前任务携带的业务参数={}", data.toJSONString());
            JobDataMap jobDataMap = new JobDataMap();
            Optional.ofNullable(job.getJsonObjectData()).ifPresent(jsonObject -> {
                Map<String, String> dataMap = JSONObject.parseObject(jsonObject.toJSONString(), new TypeReference<Map<String, String>>() {
                });
                log.info("当前任务携带的业务参数={}", job.getJsonObjectData().toJSONString());
                jobDataMap.putAll(dataMap);
            });
            JobDetail jobDetail = JobBuilder
                    // 指定执行类
                    .newJob(getClass(job.getClassName()).getClass())
                    // 指定name和group
                    .withIdentity(job.getJobName(), job.getJobGroup())
                    .requestRecovery() //执行中应用发生故障，需要重新执行
                    .withDescription(job.getDescription())
                    .setJobData(jobDataMap)
                    .build();
            // 创建表达式调度构建器
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder
                    .cronSchedule(job.getCronExpression())
                    .withMisfireHandlingInstructionDoNothing();//不触发立即执行,等待下次Cron触发频率到达时刻开始按照Cron频率依次执行
            /**
             CronTrigger
             withMisfireHandlingInstructionDoNothing
             ——不触发立即执行,等待下次Cron触发频率到达时刻开始按照Cron频率依次执行
             withMisfireHandlingInstructionIgnoreMisfires
             ——以错过的第一个频率时间立刻开始执行,重做错过的所有频率周期后,当下一次触发频率发生时间大于当前时间后，再按照正常的Cron频率依次执行
             withMisfireHandlingInstructionFireAndProceed
             ——以当前时间为触发频率立刻触发一次执行,然后按照Cron频率依次执行
             */

            // 创建触发器
            CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                    .withIdentity(job.getTriggerName(), job.getTriggerGroup())
                    .withDescription(job.getDescription())
                    .withSchedule(cronScheduleBuilder)
                    //.startNow()//表示立即触发任务，否则需要等待下一个触发点
                    .build();

            //使用调度器组合Trigger和JobDetail
            scheduler.scheduleJob(jobDetail, cronTrigger);
            scheduler.start();
            log.info("定时任务[{}]创建成功！", job.getJobName());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean runJob(SysQuartz job) {
        try {
            scheduler.triggerJob(JobKey.jobKey(job.getJobName(), job.getJobGroup()));
            log.info("定时任务[{}]执行成功", job.getJobName());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //更新什么:只能更新cron表达式
    @Override
    public Boolean updateJob(SysQuartz job) {
        try {
            TriggerKey triggerKey = new TriggerKey(job.getTriggerName(), job.getTriggerGroup());
            CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
            // 重新构件表达式
            CronTrigger trigger = cronTrigger.getTriggerBuilder()
                    .withIdentity(triggerKey).withSchedule(cronScheduleBuilder)
                    .withDescription(job.getDescription())
                    .build();
            scheduler.rescheduleJob(triggerKey, trigger);
            log.info("定时任务[{}]更新成功", job.getJobName());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean pauseJob(SysQuartz job) {
        try {
            scheduler.pauseJob(JobKey.jobKey(job.getJobName(), job.getJobGroup()));
            log.info("定时任务[{}]暂停成功", job.getJobName());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean resumeJob(SysQuartz job) {//唤醒
        try {
            scheduler.resumeJob(JobKey.jobKey(job.getJobName(), job.getJobGroup()));
            log.info("定时任务[{}]唤醒成功", job.getJobName());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Boolean deleteJob(SysQuartz job) {
        try {
            scheduler.deleteJob(JobKey.jobKey(job.getJobName(), job.getJobGroup()));
            log.info("定时任务[{}]删除成功", job.getJobName());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public JSONObject queryJob(SysQuartz job) {
        TriggerKey triggerKey = new TriggerKey(job.getTriggerName(), job.getTriggerGroup());
        try {
            CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (null == cronTrigger) {
                return null;
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("cronExpression", cronTrigger.getCronExpression());
            jsonObject.put("state", scheduler.getTriggerState(triggerKey));
            jsonObject.put("description", cronTrigger.getDescription());
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
