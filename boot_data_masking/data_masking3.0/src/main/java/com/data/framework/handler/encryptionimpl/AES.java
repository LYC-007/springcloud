package com.data.framework.handler.encryptionimpl;

import com.data.framework.annotation.HandleType;
import com.data.framework.handler.DataHandlerAbstract;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 字段加密工具类
 */
@Slf4j
@Component("aesImpl")
public class AES extends DataHandlerAbstract {
    private final static HandleType TYPE=HandleType.ENCRYPA;
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
        log.info("AES加密："+encryptValue);
        if (!encryptValue.startsWith(KEY_SENSITIVE)) {
            return AESUtil.encrypt(encryptValue, key);
        }
        return encryptValue;
    }
    /**
     * 解密
     *
     * @param decryptValue 解密值
     * @return 解密后的值
     */
    @Override
    public String posProcess(String decryptValue) {
        log.info("AES解密："+decryptValue);
        return AESUtil.decrypt(decryptValue, key);
    }
}
