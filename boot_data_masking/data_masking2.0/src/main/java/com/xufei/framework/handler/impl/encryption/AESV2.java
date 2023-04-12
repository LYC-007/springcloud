package com.xufei.framework.handler.impl.encryption;

import com.xufei.framework.enums.HandleType;
import com.xufei.framework.util.AESUtil;
import com.xufei.framework.handler.DataHandlerAbstract;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 字段加密工具类
 */
@Slf4j
@Component("rDSImpl")
public class AESV2 extends DataHandlerAbstract {
    private final static HandleType TYPE=HandleType.ENCRYPA;
    public String key="456";
    @Override
    public HandleType getHandleType() {
        return TYPE;
    }
    /**
     *
     * @param encryptValue 加密值
     * @return 加密后的值
     */
    @Override
    public String preProcess(String encryptValue){
        log.info("RDS加密："+encryptValue);
        return AESUtil.encrypt(encryptValue, key);
    }

    /**
     * 解密
     *
     * @param decryptValue 解密值
     * @return 解密后的值
     */
    @Override
    public String posProcess(String decryptValue) {
        log.info("RDS解密："+decryptValue);
        return AESUtil.decrypt(decryptValue, key);
    }





}
