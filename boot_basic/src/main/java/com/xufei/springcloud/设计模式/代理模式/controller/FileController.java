package com.xufei.springcloud.设计模式.代理模式.controller;

import com.xufei.springcloud.设计模式.代理模式.proxy.FileUploadProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/file")
public class FileController {
    @Autowired
    private FileUploadProxy fileUploadProxy;
    /*** * 文件上传 * @param file * @return * @throws IOException */
    @PostMapping(value = "/upload")
    public String upload(MultipartFile file) throws IOException {

        return fileUploadProxy.upload(file);
    }
}