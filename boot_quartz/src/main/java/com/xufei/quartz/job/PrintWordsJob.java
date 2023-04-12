package com.xufei.quartz.job;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

public class PrintWordsJob extends QuartzJobBean {
    @Override
    public void executeInternal(JobExecutionContext jobExecutionContext){
        JobDataMap mergedJobDataMap = jobExecutionContext.getMergedJobDataMap();
        mergedJobDataMap.forEach((key, value) -> System.out.println(key+":"+value));
        String printTime = new SimpleDateFormat("yy-MM-dd HH-mm-ss").format(new Date());
        System.out.println("PrintWordsJob start at:" + printTime + ", prints: Hello Job-" + new Random().nextInt(100));
    }
}
