package com.xufei.constant;


import com.xufei.framework.annotation.MybatisDataProcess;
import com.xufei.framework.handler.impl.encryption.AESV2;
import lombok.Data;

import java.io.Serializable;


@Data
@MybatisDataProcess(encryptionMode = AESV2.class)
public class User implements Serializable {


    private Long id;


    private String city;


    private String name;


    private String phone;

}
