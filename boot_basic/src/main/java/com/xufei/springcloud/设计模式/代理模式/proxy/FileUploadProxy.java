package com.xufei.springcloud.设计模式.代理模式.proxy;

import com.xufei.springcloud.设计模式.代理模式.service.FileUpload;
import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
@Data
@ConfigurationProperties(prefix = "upload")
public class FileUploadProxy implements ApplicationContextAware {

    private ApplicationContext ac;
    //aliyunOSSFileUpload -> mp4,avi
    private Map<String, List<String>> fileSuffix;//  文件的后缀名，通过配置文件指定
    public String upload(MultipartFile multipartFile) throws IOException {

        //文件名字 1.mp4
        String filename = multipartFile.getOriginalFilename();
        //扩展名 mp4,jpg
        String extension = StringUtils.getFilenameExtension(filename);//获取文件的后缀名
        for (Map.Entry<String, List<String>> listEntry : fileSuffix.entrySet()) {
            for (String value : listEntry.getValue()) {
                //匹配当前extension和当前map中对应的类型是否匹配
                if (value.equalsIgnoreCase(extension)) {
                    //一旦匹配，则把key作为唯一值，从容器中获取对应实例
                    return ac.getBean(listEntry.getKey(), FileUpload.class).//根据Bean的名字和Bean实现的接口获取Bean的实例
                            /**所以配置文件的书写方式为下面的格式:
                             * #指定服务处理指定的文件类型
                             * upload:
                             * 	fileSuffix:
                             * 		aliyunOSSFileUpload: mp4,avi
                             * 		fastdfsFileUpload: png,jpg
                             */
                            upload(multipartFile.getBytes(), extension);
                }
            }
        }
        return null;
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        ac = applicationContext;
    }
}