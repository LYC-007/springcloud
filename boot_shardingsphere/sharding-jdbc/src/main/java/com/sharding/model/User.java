package com.sharding.model;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    private Integer id;
    private String name;
    private Integer age;

    /**
     CREATE TABLE `user` (
     `id` int(11),
     `name` varchar(100) NOT NULL,
     `age` varchar(100) default '18',
     PRIMARY KEY (`id`),
     UNIQUE KEY `AK_nq_name` (`name`)
     ) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8
     */
}
