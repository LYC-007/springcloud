package com.xufei.framework.util;


import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


@Slf4j
public class AESUtil {
    private static final String KEY_ALGORITHM = "AES";
    //默认的加密算法
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

    /**
     * AES 加密操作
     *
     * @param content 待加密内容
     * @return 返回Base64转码后的加密数据
     */
    public static String encrypt(String content, String key) {
        try {
            // 创建密码器
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            byte[] byteContent = content.getBytes(StandardCharsets.UTF_8);
            // 初始化为加密模式的密码器
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(key));
            // 加密
            byte[] result = cipher.doFinal(byteContent);

            //Base64是一种基于64个可打印字符来表示二进制数据的表示方法。
            //通过Base64转码返回
            return Base64Utils.encodeToString(result);
        } catch (Exception ex) {
            log.info("加密失败：{}", ex);
        }

        return null;
    }


    /**
     * AES 解密操作
     *
     * @param content
     * @return
     */
    public static String decrypt(String content, String key) {

        try {
            //实例化
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);

            //使用密钥初始化，设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(key));

            //执行操作
            //Base64是一种基于64个可打印字符来表示二进制数据的表示方法。
            byte[] result = cipher.doFinal(Base64Utils.decodeFromString(content));

            return new String(result, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            log.info("解密操作：{}", ex);
        }
        return null;
    }

    /**
     * 生成加密秘钥
     *
     * @return
     */
    private static Key getSecretKey(String key) throws NoSuchAlgorithmException {
        //返回生成指定算法密钥生成器的 KeyGenerator 对象
        KeyGenerator kg;
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(key.getBytes());
        try {
            kg = KeyGenerator.getInstance(KEY_ALGORITHM);
            //AES 要求密钥长度为 128
            kg.init(128, random);
            //生成一个密钥
            SecretKey secretKey = kg.generateKey();
            // 转换为AES专用密钥
            return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException ex) {
            log.info("生成加密秘钥失败：{}",ex.getMessage());
        }
        return null;
    }

}
