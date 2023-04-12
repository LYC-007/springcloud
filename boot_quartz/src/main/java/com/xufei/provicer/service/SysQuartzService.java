package com.xufei.provicer.service;

import com.alibaba.fastjson.JSONObject;
import com.xufei.provicer.domain.SysQuartz;

public interface SysQuartzService {
    /**
     * 创建Job
     * @param sysQuartz
     */
    Boolean addJob(SysQuartz sysQuartz);

    /**
     * 执行Job
     * @param sysQuartz
     */
    Boolean runJob(SysQuartz sysQuartz);

    /**
     * 修改Job
     * @param sysQuartz
     */
    Boolean updateJob(SysQuartz sysQuartz);

    /**
     * 暂定Job
     * @param sysQuartz
     */
    Boolean pauseJob(SysQuartz sysQuartz);

    /**
     * 唤醒Job
     * @param sysQuartz
     */
    Boolean resumeJob(SysQuartz sysQuartz);

    /**
     * 删除Job
     * @param sysQuartz
     */
    Boolean deleteJob(SysQuartz sysQuartz);

    /**
     * 获取Job
     * @param sysQuartz
     */
    JSONObject queryJob(SysQuartz sysQuartz);

}
