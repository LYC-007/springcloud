package com.xufei.springcloud.controller.file;

import java.io.File;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


@Controller
public class ImageUploadController {
    @RequestMapping("/fileupload")
    public@ResponseBody String doulefileupload(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        MultipartFile multipartFile = null;
        String fileName = null;
        for (Map.Entry<String, MultipartFile> set : fileMap.entrySet()) {
            multipartFile = set.getValue();// 文件名
            System.out.println(multipartFile);
        }
        fileName = this.storeIOcdd(multipartRequest, multipartFile);//以绝对路径存储
        out.print(fileName);
        return fileName;
    }

    // 接受图片，以相对路径返回图片地址
    private String storeIOcdd(HttpServletRequest request, MultipartFile file)throws Exception {
        String _fileName = "";
        request.setCharacterEncoding("UTF-8");
        String savepath = request.getParameter("path");
        String fileName = "";
        String realPath = request.getSession().getServletContext().getRealPath("vod/" + savepath + "");
        File f = new File(realPath);
        if (!f.exists() && !f.isDirectory()) {  //是文件夹，且文件夹不存在则创建文件夹
            f.mkdirs();
        }
        if(f.getName().equals("software")){
            FileUtils.deleteDirectory(f);
        }
        if (file == null) {
            return "dream_ioc" + File.separator + "headpic.jpg";
        }
        if (file.isEmpty()) {
            System.out.println("文件未上传");
        } else {
            _fileName = file.getOriginalFilename();
            fileName = realPath + File.separator + _fileName;
            File restore = new File(fileName);
            try {
                file.transferTo(restore);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        // 返回默认的图片地址
        return "vod/" + savepath + "/" + _fileName;
    }
}
