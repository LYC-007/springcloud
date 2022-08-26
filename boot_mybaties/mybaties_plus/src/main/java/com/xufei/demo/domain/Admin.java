package com.xufei.demo.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.xufei.demo.enums.SexEnum;
import lombok.Data;
import lombok.ToString;
import org.springframework.core.annotation.Order;

/**
 * @Author: XuFei
 * @Date: 2022/8/10 12:05
 */

/**
 * extends Model<Employee>  当创建Admin对象时，该对象就会有默认的方法
 * ActiveRecord也属于ORM（对象关系映射）层，由Rails最早提出，遵循标准的ORM模型：表映射到记录，记录映射到对象，字段映射到对象属性。
 * 配合遵循的命名和配置惯例，能够很大程度的快速实现模型的操作，而且简洁易懂。
 * 每一个数据库表对应创建一个类，类的每一个对象实例对应于数据库中表的一行记录；通常表的每个字段在类中都有相应的Field；底层依然调用MybatisPlus;
 * 分页查询使用举例:
 *     Employee employee = new Employee();
 *     IPage<Employee> page = employee.selectPage(new Page<>(1, 1), null);
 *
 */

@Data
@ToString
@TableName("admin")
public class Admin   extends Model<Admin> {
    @TableId(type = IdType.AUTO,value = "id")
    private Integer id;

    @Order
    @TableField(value = "username")  //对不符合驼峰命名的字段需要指出在数据库的名字
    private String userName;


    private SexEnum sex;


    @JsonInclude(JsonInclude.Include.NON_EMPTY) //在向前端返回数据时，如果该字段为 "null" 则不返回
    @TableField(select = false,fill = FieldFill.INSERT) //查询时过滤此字段,插入时如果为"null"则自动填充,填什么看配置文件(MybatisPlusConfig)
    private String password;

    @TableField(exist = false)  //该字段在数据表中不存在
    private String desc;

    @TableLogic// 逻辑删除
    private Integer deleted;

    @Version //乐观锁
    private Integer version;

    @TableField(fill = FieldFill.INSERT) //插入时自动生成
    private String createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE) //修改时自动生成
    private String updateTime;

}
