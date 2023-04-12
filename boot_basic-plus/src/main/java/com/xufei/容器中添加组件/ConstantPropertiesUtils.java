package com.xufei.容器中添加组件;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
/**

@Component
public class ConstantPropertiesUtils implements InitializingBean {
    //InitializingBean:该接口当Spring加载之后，就会初始化下面的值，最会会执行afterPropertiesSet()方法
    //读取配置文件里面的值    @Value
    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.file.keyid}")
    private String keyId;

    @Value("${aliyun.oss.file.keysecret}")
    private String keySecret;

    @Value("${aliyun.oss.file.bucketname}")
    private String bucketName;

    public static String END_POINT;
    public static String KEY_ID;
    public static String KEY_SECRET;
    public static String BUCKET_NAME;
    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT=endpoint;
        KEY_SECRET=keySecret;
        KEY_ID=keyId;
        BUCKET_NAME=bucketName;
    }
}

**/