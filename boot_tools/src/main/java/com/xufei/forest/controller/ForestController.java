package com.xufei.forest.controller;

import com.dtflys.forest.exceptions.ForestRuntimeException;
import com.dtflys.forest.http.ForestRequest;
import com.dtflys.forest.http.ForestResponse;
import com.xufei.forest.client.CallbackClient;
import com.xufei.forest.client.DemoForestClient;
import com.xufei.forest.client.FileDownloadClient;
import com.xufei.forest.client.FileUploadClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
public class ForestController {

    @Autowired
    private DemoForestClient myForestClient;
    @Autowired
    private FileUploadClient fileUploadClient;
    @Autowired
    private FileDownloadClient fileDownloadClient;

    @Autowired
    private CallbackClient callbackClient;


    /**
     * 回调函数（可用于成功和失败默认回调)  回调使用演示
     */

    public void test(){
        callbackClient.sendAndCallback("小熙", (String resultString, ForestRequest request, ForestResponse response) -> {
                    // 成功响应回调（如果是异步，这里可以操作响应数据封装）
                    System.out.println(resultString);
                },
                (ForestRuntimeException ex, ForestRequest request, ForestResponse response) -> {
                    // 异常回调
                    System.out.println(ex.getMessage());
                });
    }

    /**
     * 异步请求 ,回调演示
     */

    public void test1() throws ExecutionException, InterruptedException {
        // 回调异步
        callbackClient.testAsyncGet("小熙", (String resultString, ForestRequest request, ForestResponse response) -> {
            // 打印成功请求返回的结果
            System.out.println(resultString);
        });

        // future异步
        Future<String> stringFuture = callbackClient.testAsyncGet("小熙");
        // 获取future异步成功调用的返回结果
        String string = stringFuture.get();

    }

    /**
     * 文件上传演示
     */
    public void test2(){
        Map result = fileUploadClient.testUpload("D:\\TestUpload\\xxx.jpg", progress -> {
            System.out.println("total bytes: " + progress.getTotalBytes());   // 文件大小
            System.out.println("current bytes: " + progress.getCurrentBytes());   // 已上传字节数
            System.out.println("progress: " + Math.round(progress.getRate() * 100) + "%");  // 已上传百分比
            if (progress.isDone()) {   // 是否上传完成
                System.out.println("--------   Upload Completed!   --------");
            }
        });
    }

    /**
     * 文件下载演示
     */
    public void test3(){
        File file = fileDownloadClient.testDownloadFile("D:\\TestDownload", "", progress -> {
            System.out.println("total bytes: " + progress.getTotalBytes());   // 文件大小
            System.out.println("current bytes: " + progress.getCurrentBytes());   // 已下载字节数
            System.out.println("progress: " + Math.round(progress.getRate() * 100) + "%");  // 已下载百分比
            if (progress.isDone()) {   // 是否下载完成
                System.out.println("--------   Download Completed!   --------");
            }
        });

    }
}
