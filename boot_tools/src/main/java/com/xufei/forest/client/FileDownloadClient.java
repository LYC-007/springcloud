package com.xufei.forest.client;

import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.GetRequest;
import com.dtflys.forest.callback.OnProgress;
import com.dtflys.forest.extensions.DownloadFile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;

@Component
public interface FileDownloadClient {
    /**
     * 在方法上加上@DownloadFile注解
     * dir属性表示文件下载到哪个目录
     * filename属性表示文件下载成功后以什么名字保存，如果不填，这默认从URL中取得文件名
     * OnProgress参数为监听上传进度的回调函数
     * @param dir
     * @param filename
     * @param onProgress
     * @return
     */
    @Get(url = "http://localhost:8080/images/xxx.jpg")
    @DownloadFile(dir = "${0}", filename = "${1}")
    File testDownloadFile(String dir, String filename, OnProgress onProgress);

    /**
     * 如果您不想将文件下载到硬盘上，而是直接在内存中读取，可以去掉@DownloadFile注解，并且用以下几种方式定义接口:
     */

    /**
     * 返回类型用byte[]，可将下载的文件转换成字节数组
     * @return
     */
    @GetRequest(url = "http://localhost:8080/images/test-img.jpg")
    byte[] downloadImageToByteArray();

    /**
     * 返回类型用InputStream，用流的方式读取文件内容
     * @return
     */
    @GetRequest(url = "http://localhost:8080/images/test-img.jpg")
    InputStream downloadImageToInputStream();


}
