package com.xufei.springcloud.设计模式.代理模式.service;

public interface FileUpload {

    /*** * 文件上传 * @param buffers：文件字节数组
     * * @param extName：后缀名 * @return */
    String upload(byte[] buffers,String extName);
}