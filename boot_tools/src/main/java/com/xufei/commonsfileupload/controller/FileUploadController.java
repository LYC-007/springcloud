package com.xufei.commonsfileupload.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.xufei.excel.easyexcel.domain.UserExcel;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
public class FileUploadController {
    @PostMapping("/upload")
    public String upLoad(@RequestParam(value = "Email",required = false) String Email,
                         @RequestParam(value = "userName",required = false) String userName,
                         @RequestPart(value = "headerImg") MultipartFile headerImg,
                         @RequestPart(value = "photos") MultipartFile[] photos) throws IOException {
        if(photos[0].getSize()==0){
            throw new RuntimeException();
        }
        if(!headerImg.isEmpty()){
            String originalFilename = headerImg.getOriginalFilename();
            headerImg.transferTo(new File("D:\\abcd\\"+originalFilename));
        }
        for (MultipartFile photo : photos) {
            String originalFilename = photo.getOriginalFilename();
            photo.transferTo(new File("D:\\abcd\\"+originalFilename));
        }
        return "redirect:/main";
    }
    /**
     * Excel文件上传
     */
    @RequestMapping("/upload")
    public void uploadExcel(HttpServletRequest request) throws Exception {
        ServletFileUpload fileUpload = new ServletFileUpload(new DiskFileItemFactory());
        // 设置单个文件大小为3M 2的10次幂=1024
        fileUpload.setFileSizeMax((long) (3 * Math.pow(2, 20)));
        // 总文件大小为30M
        fileUpload.setSizeMax((long) (30 * Math.pow(2, 20)));
        List<FileItem> list = fileUpload.parseRequest(request);
        for (FileItem fileItem : list) {
            if (!fileItem.isFormField()) {// 判断是否为附件
                // 是附件
                InputStream inputStream = fileItem.getInputStream();
                EasyExcel.read(inputStream, UserExcel.class,
                        new AnalysisEventListener<UserExcel>() {
                            // 每解析一行数据,该方法会被调用一次
                            @Override
                            public void invoke(UserExcel data, AnalysisContext analysisContext) {
                                System.out.println("解析数据为:" + data.toString());
                            }

                            // 全部解析完成被调用
                            @Override
                            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                                System.out.println("解析完成...");
                                // 可以将解析的数据保存到数据库
                            }
                        }).sheet().doRead();
            }
        }
    }


    // windows版本文件路径
}
