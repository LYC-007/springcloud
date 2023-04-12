package com.xufei.provicer.domain;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.io.Serializable;
/**
 * 创建定时任务的 POJO
 */

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
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_quartz")
@ApiModel(value = "SysQuartz对象", description = "定时任务信息表")
public class SysQuartz extends Model<SysQuartz> {
    @ApiModelProperty(value = "主键id")
    @TableId(value = "job_id", type = IdType.AUTO)
    private Long jobId;

    @ApiModelProperty(value = "任务类名")
    @TableField("class_name")
    private String className;

    @ApiModelProperty(value = "触发器名称")
    @TableField("trigger_name")
    private String triggerName;

    @ApiModelProperty(value = "任务所属组名")
    @TableField("job_group")
    private String jobGroup;

    @ApiModelProperty(value = "触发器所属组")
    @TableField("trigger_group")
    private String triggerGroup;

    @ApiModelProperty(value = "任务名称")
    @TableField("job_name")
    private String jobName;

    @ApiModelProperty(value = "cron表达式")
    @TableField("cron_expression")
    private String cronExpression;

    @ApiModelProperty(value = "参数")
    @TableField(exist = false)  //该字段在数据表中不存在
    private JSONObject jsonObjectData;
    //JSONObject是一种数据结构,“key-value”结构,其格式为“{"key1":value1,"key2",value2....}”

    @ApiModelProperty(value = "参数")
    @TableField("data")  //该字段在数据表中不存在
    private String data;

    @ApiModelProperty(value = "描述")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "启动状态（0--启动1--停止）")
    @TableField("pause_status")
    private Integer pauseStatus;

    @ApiModelProperty(value = "删除状态（0--未删除1--已删除）")
    @TableField("delete_status")
    @TableLogic
    private Integer deleteStatus;

    @ApiModelProperty(value = "创建者的id")
    @TableField("create_user")
    private Long createUser;

    @Version //乐观锁
    @TableField("version")
    private Integer version;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time")//插入时自动生成
    private String createTime;


    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time")
    private String updateTime;

    @Override
    protected Serializable pkVal() {
        return this.jobId;
    }
}
