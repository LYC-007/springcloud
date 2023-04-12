package com.xufei.jpa.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="sys_channel")
@Data
public class Channel extends BaseModel {

    //渠道名称
    private String channelName;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="channelId")//name属性关联的是你需要关联的另一张表的外键
    private List<User> users;
}
