package com.xufei.constant;



import com.xufei.framework.annotation.MybatisDataProcess;
import com.xufei.framework.enums.HandleType;
import com.xufei.framework.handler.impl.DesensitizationDataHandler;
import com.xufei.framework.handler.impl.encryption.AES;
import com.xufei.framework.annotation.DataHandle;
import lombok.Data;

import java.io.Serializable;


@Data
@MybatisDataProcess(encryptionMode = AES.class,desensitizationMode = DesensitizationDataHandler.class)
public class Customer implements Serializable {

    private Integer id;

    @DataHandle(HandleType.DESENSITIZE)//表示要进行数据脱敏
    private String phone;


    @DataHandle(HandleType.ENCRYPA)//表示要加密
    private String address;


    private Integer age;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}

