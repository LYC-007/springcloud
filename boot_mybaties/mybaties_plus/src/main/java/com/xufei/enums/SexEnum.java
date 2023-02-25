package com.xufei.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 还需要在application.yml中配置扫描枚举类路径，配置文件中追加配置type-enums-package: com.example.springbootmp.domain.enums：
 */
@Getter
public enum SexEnum {
    MAN(1,"男"),WOMEN(0,"女");

    @EnumValue//@EnumValue用来标记数据库存的值
    // mybatis原生默认是以枚举的名称： Enum.name()作为默认值，即Admin类中定义的属性 private SexEnum sex;默认向数据库存的时候会将SexEnum.MAN.name()的值存入数据库(String类型)
    // 使用@EnumValue注解标识SexEnum类中的code属性后，保存数据库时就会取值code保存进数据库。同样如果标识value 保存时会取value的值(男/女)保存入库
    private int code;


    @JsonValue
    //需要前端展示的属性上添加@JsonValue注解；
    //@JsonValue 可以用在get方法或者属性字段上，一个类只能用一个，当加上@JsonValue注解是，序列化是只返回这一个字段的值。
    private String value;
    SexEnum(int code,String value){
        this.code=code;
        this.value=value;
    }
}
