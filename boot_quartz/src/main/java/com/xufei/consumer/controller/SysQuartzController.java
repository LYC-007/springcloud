package com.xufei.consumer.controller;

import com.alibaba.fastjson.JSONObject;
import com.xufei.provicer.domain.SysQuartz;
import com.xufei.provicer.service.SysQuartzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

//CronExpression.isValidExpression(cron)
@RestController
public class SysQuartzController {

    @Autowired
    private SysQuartzService sysQuartzService;

    /**
     {
     "className":"com.xufei.quartz.job.PrintWordsJob",
     "triggerName":"triggerName","jobGroup":"triggerGroup",
     "triggerGroup":"triggerGroup","jobName":"triggerName",
     "cronExpression":"0/1 * * * * ?",
     "jsonObjectData":{"varcharId":1,"skuArray":"skuArray"},
     "description":"1",
     "pauseStatus":"0",
     "deleteStatus":"0",
     "createUser":"12344"
     }
     */
    

    @PostMapping("/add")
    public String addJob(@RequestBody SysQuartz job) {
        Optional.ofNullable(job.getJsonObjectData())
                .ifPresent(jsonObject -> job.setData(job.getJsonObjectData().toJSONString()));
        Boolean result = sysQuartzService.addJob(job);
        if (!result) {
            return "创建定时任务失败";
        }
        return "创建定时任务成功:"+job.getJobId()+job.getJobName();
    }

    @PostMapping("/run")
    public String runJob(@RequestBody SysQuartz job) {
        Boolean result = sysQuartzService.runJob(job);
        if (!result) {
            return "启动定时任务失败";
        }
        return "启动定时任务成功:"+job.getJobId()+job.getJobName();
    }

    @PostMapping("/update")
    public String updateJob(@RequestBody SysQuartz job) {
        Boolean result = sysQuartzService.updateJob(job);
        if (!result) {
            return "修改定时任务失败";
        }
        return "修改定时任务成功:"+job.getJobId()+job.getJobName();
    }

    @PostMapping("/pause")
    public String pauseJob(@RequestBody SysQuartz job) {
        Boolean result = sysQuartzService.pauseJob(job);
        if (!result) {
            return "暂停定时任务失败";
        }
        return "暂停定时任务成功:"+job.getJobId()+job.getJobName();
    }

    @PostMapping("/resume")
    public String resumeJob(@RequestBody SysQuartz job) {
        Boolean result = sysQuartzService.resumeJob(job);
        if (!result) {
            return "唤醒定时任务失败";
        }
        return "唤醒定时任务成功:"+job.getJobId()+job.getJobName();
    }

    @PostMapping("/delete")
    public String deleteJob(@RequestBody SysQuartz job) {
        Boolean result = sysQuartzService.deleteJob(job);
        if (!result) {
            return "删除定时任务失败";
        }
        return "删除定时任务成功:"+job.getJobId()+job.getJobName();
    }

    @GetMapping("/query")
    public String queryJob(@RequestBody SysQuartz job) {
        JSONObject result = sysQuartzService.queryJob(job);
        if (null == result) {
            return "不存在对应的任务:"+job.getJobId()+job.getJobName();
        }
        return result.toString();
    }
}
