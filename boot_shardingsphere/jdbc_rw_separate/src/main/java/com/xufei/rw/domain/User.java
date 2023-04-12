package com.xufei.rw.domain;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("user")
@Data
public class User {
    //id为null时采用雪花算法自动填充id

    /**
     * 这里不用指定id的生成测略  由sharding-jdbc指定，具体查看配置文件
     *   @TableId(type = IdType.ASSIGN_ID,value = "id")
     *
     */
    private String id;
    private String name;
    private Integer age;

    /**
     CREATE TABLE `user` (
     `id` VARCHAR(32) not null,
     `name` VARCHAR(100) NOT NULL,
     `age` VARCHAR(100) DEFAULT '18',
     PRIMARY KEY (`id`),
     UNIQUE KEY `AK_nq_name` (`name`)
     ) ENGINE=INNODB DEFAULT CHARSET=utf8;
     */
}
