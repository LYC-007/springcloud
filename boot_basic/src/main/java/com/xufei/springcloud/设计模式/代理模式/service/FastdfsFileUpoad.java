package com.xufei.springcloud.设计模式.代理模式.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component(value = "fastdfsFileUpoad")
public class FastdfsFileUpoad implements FileUpload {
    @Value("${fastdfs.url}")
    private String url;
    /*** * 文件上传 * @param buffers：文件字节数组 * @param extName：后缀名 * @return */
    @Override
    public String upload(byte[] buffers, String extName) {

        return "上传到FastDFS";
    }
}