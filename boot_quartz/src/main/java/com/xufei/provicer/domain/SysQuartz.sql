 sys_quartz  CREATE TABLE `sys_quartz` (
 `job_id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
 `class_name` varchar(225) NOT NULL COMMENT '定时任务的class路径',
 `cron_expression` varchar(32) NOT NULL COMMENT 'cron表达式',
 `job_name` varchar(20) NOT NULL COMMENT '任务名称',
 `job_group` varchar(20) NOT NULL COMMENT '任务所属组',
 `trigger_name` varchar(20) NOT NULL COMMENT '触发器名称',
 `trigger_group` varchar(20) NOT NULL COMMENT '触发器组',
 `data` varchar(225) DEFAULT NULL COMMENT '携带参数',
 `description` varchar(225) NOT NULL COMMENT '描述',
 `pause_status` tinyint NOT NULL COMMENT '启动状态(0--启动1--停止)',
 `delete_status` tinyint DEFAULT '0' COMMENT '删除状态（0，正常，1已删除）',
 `create_user` bigint NOT NULL COMMENT '创建人的id',
 `version` int unsigned DEFAULT '1' COMMENT '乐观锁',
 `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
 PRIMARY KEY (`job_id`),
 UNIQUE KEY `index_class_name` (`class_name`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='定时任务信息表'