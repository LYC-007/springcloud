package com.xufei.constant;


import com.xufei.framework.annotation.MybatisDataProcess;
import com.xufei.framework.enums.HandleType;
import com.xufei.framework.handler.impl.ReplaceDataHandler;
import com.xufei.framework.handler.impl.encryption.AESV2;
import com.xufei.framework.annotation.DataHandle;
import lombok.Data;

import java.io.Serializable;


@Data
@MybatisDataProcess(encryptionMode = AESV2.class,desensitizationMode = ReplaceDataHandler.class)
public class Participle implements Serializable {
    /**
     * Customer的id
     */
    private Integer id;

    @DataHandle(HandleType.ENCRYPA)
    private String text;

}
