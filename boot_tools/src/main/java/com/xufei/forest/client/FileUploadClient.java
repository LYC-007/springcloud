package com.xufei.forest.client;

import com.dtflys.forest.annotation.DataFile;
import com.dtflys.forest.annotation.Post;
import com.dtflys.forest.annotation.PostRequest;
import com.dtflys.forest.callback.OnProgress;
import com.dtflys.forest.http.ForestRequest;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Component
public interface FileUploadClient {
    /**
     * 上传下载
     * Forest从 1.4.0 版本开始支持多种形式的文件上传和文件下载功能
     */

    /**
     * 用@DataFile注解修饰要上传的参数对象
     * OnProgress 参数为监听上传进度的回调函数
     * @param filePath
     * @param onProgress
     * @return
     */
    @Post(url = "/upload")
    Map testUpload(@DataFile("file") String filePath, OnProgress onProgress);

    /**
     * File类型对象
     */
    @Post(url = "/upload")
    Map testUpload(@DataFile("file") File file, OnProgress onProgress);

    /**
     * byte数组
     * 使用byte数组和Inputstream对象时一定要定义fileName属性
     */
    @Post(url = "/upload")
    Map testUpload(@DataFile(value = "file", fileName = "${1}") byte[] bytes, String filename);

    /**
     * Inputstream 对象
     * 使用byte数组和Inputstream对象时一定要定义fileName属性
     */
    @Post(url = "/upload")
    Map testUpload(@DataFile(value = "file", fileName = "${1}") InputStream in, String filename);

    /**
     * Spring Web MVC 中的 MultipartFile 对象
     */
    @PostRequest(url = "/upload")
    Map testUpload(@DataFile(value = "file") MultipartFile multipartFile, OnProgress onProgress);

    /**
     * Spring 的 Resource 对象
     */
    @Post(url = "/upload")
    Map testUpload(@DataFile(value = "file") Resource resource);





    /**
     * ********************************************************批量上传************************************************************************
     */

    /**
     * 上传Map包装的文件列表
     * 其中 ${_key} 代表Map中每一次迭代中的键值
     * @param byteArrayMap
     * @return
     */
    @PostRequest(url = "/upload")
    ForestRequest<Map> uploadByteArrayMap(@DataFile(value = "file", fileName = "${_key}") Map<String, byte[]> byteArrayMap);

    /**
     * 上传List包装的文件列表
     * 其中 ${_index} 代表每次迭代List的循环计数（从零开始计）
     * @param byteArrayList
     * @return
     */
    @PostRequest(url = "/upload")
    ForestRequest<Map> uploadByteArrayList(@DataFile(value = "file", fileName = "test-img-${_index}.jpg") List<byte[]> byteArrayList);

    /**
     * 上传数组包装的文件列表
     * 其中 ${_index} 代表每次迭代List的循环计数（从零开始计）
     * @param byteArrayArray
     * @return
     */
    @PostRequest(url = "/upload")
    ForestRequest<Map> uploadByteArrayArray(@DataFile(value = "file", fileName = "test-img-${_index}.jpg") byte[][] byteArrayArray);



}
