package com.server.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 公钥私钥信息配置文件
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
@ConfigurationProperties(prefix = "jks")
public class JksProperties {

    /**
     * jks文件的名称
     */
    private String name;
    /**
     * 存储密码
     */
    private String storePassword;

    /**
     * 秘钥密码
     */
    private String keyPassword;
    /**
     * 别名
     */
    private String alias;
}
