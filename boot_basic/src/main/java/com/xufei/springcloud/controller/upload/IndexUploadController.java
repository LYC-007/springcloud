package com.xufei.springcloud.controller.upload;



import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.*;



/***
 * Spring MVC对文件上传做了简化，在Spring Boot中对此做了更进一步的简化，文件上传更为方便。
 *
 * Java中的文件上传一共涉及两个组件，一个是CommonsMultipartResolver，另一个是StandardServletMultipartResolver;
 * 其中 CommonsMultipartResolver使用commons-fileupload来处理multipart 请求，而StandardServletMultipartResolver则是基于Servlet 3.0来处理multipart 请求的;
 * Tomcat 7.0开始就支持Servlet 3.0 了，因此可以直接使用StandardServletMultipartResolver。因此若使用StandardServletMultipartResolver，则不需要添加额外的jar包。
 * 而在Spring Boot 提供的文件上传自动化配置类MultipartAutoConfiguration中，默认也是采用StandardServletMultipartResolver;
 */
@RestController
@Slf4j
public class IndexUploadController {
    /**
     *文件保存到服务器
     */
    @PostMapping("/upload")
    public String upload(@RequestParam("email") String email,
                         @RequestParam("username") String username,
                         @RequestPart("headerImg") MultipartFile headerImg,
                         @RequestPart("photos") MultipartFile[] photos) throws IOException {
        log.info("上传的信息：email={}，username={}，headerImg={}，photos={}", email, username, headerImg.getSize(), photos.length);
        if (!headerImg.isEmpty()) {
            //保存到文件服务器，OSS服务器
            String originalFilename = headerImg.getOriginalFilename();//注意上传多个文件的重名问题
            headerImg.transferTo(new File("D:\\" + originalFilename));
        }
        if (photos.length > 0) {
            for (MultipartFile photo : photos) {
                if (!photo.isEmpty()) {
                    String originalFilename = photo.getOriginalFilename();
                    photo.transferTo(new File("D:\\" + originalFilename));//注意上传多个文件的重名问题
                }
            }
        }
        return "文件上传成功";
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
    @PostMapping("/upload01")
    public String upload01(MultipartFile uploadFile, HttpServletRequest req) {
        /**
         *  String realPath =req.getSession().getServletContext().getRealPath("/uploadFile/");
         * 上传图片到服务器根路径下的文件夹里，若重启服务器，图片又无法访问，这是因为每次重启服务器之后，都会在系统临时文件夹内，创建一个新的服务器，图片就保存在这里;
         * 若重启，又会产生一个新的服务器，此时访问的就是新服务器的图片资源，而图片根本就不在新服务器内。还有就是上面一个传相同日期的文件的时候会出现无法上传文件。
         */
        String realPath =req.getSession().getServletContext().getRealPath("/uploadFile/");
        String format = sdf.format(new Date());
        File folder = new File(realPath + format);
        String filePath="";
        if (!folder.isDirectory()) {
            folder.mkdirs();
            String oldName = uploadFile.getOriginalFilename();
            String newName = UUID.randomUUID().toString() +oldName.substring(oldName.lastIndexOf("."), oldName.length());
            try {
                uploadFile.transferTo(new File(folder, newName));
                filePath = req.getScheme() + "://" + req.getServerName() + ":" +req.getServerPort() + "/uploadFile/" + format + newName;
            } catch (IOException e) {
                e.printStackTrace();
                return "上传失败! ";
            }
        }
        return filePath;
    }
    @PostMapping("/upload02")  //upload01(MultipartFile uploadFile, HttpServletRequest req) 的改进版
    public String upload02(MultipartFile uploadFile, HttpServletRequest req) {
        String filePath = "";
        String format = sdf.format(new Date());
        File folder = new File("D:\\" + format);
        if (!folder.isDirectory()) {
            folder.mkdirs();
            String oldName = uploadFile.getOriginalFilename();
            String newName = UUID.randomUUID().toString() +
                    oldName.substring(oldName.lastIndexOf("."), oldName.length());
            try {
                uploadFile.transferTo(new File(folder, newName));
                filePath = req.getScheme() + "://" + req.getServerName() + ":" +req.getServerPort() + "/uploadFile/" + format + newName;
            } catch (IOException e) {
                e.printStackTrace();
                return "上传失败! ";
            }
        }
        String oldName = uploadFile.getOriginalFilename();
        String newName = UUID.randomUUID().toString() +oldName.substring(oldName.lastIndexOf("."));
        try {
            uploadFile.transferTo(new File(folder, newName));
            filePath = req.getScheme() + "://" + req.getServerName() + ":" +
                    req.getServerPort() + "/uploadFile/" + format + newName;
        } catch (IOException e) {
            e.printStackTrace();
            return "上传失败! ";
        }
        return filePath;
    }
    @PostMapping("/upload03")// 多文件上传示例
    public ArrayList<String> upload03(MultipartFile[] uploadFiles, HttpServletRequest req) {
        ArrayList<String> list=new ArrayList<>();
        String filePath = "";
        for(MultipartFile uploadFile:uploadFiles){
            String format = sdf.format(new Date());
            File folder = new File("D:\\" + format);
            if (!folder.isDirectory()) {
                folder.mkdirs();
                String oldName = uploadFile.getOriginalFilename();
                String newName = UUID.randomUUID().toString() +oldName.substring(oldName.lastIndexOf("."));
                try {
                    uploadFile.transferTo(new File(folder, newName));
                    filePath = req.getScheme() + "://" + req.getServerName() + ":" +req.getServerPort() + "/uploadFile/" + format + newName;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            String oldName = uploadFile.getOriginalFilename();
            String newName = UUID.randomUUID().toString() +
                    oldName.substring(oldName.lastIndexOf("."), oldName.length());
            try {
                uploadFile.transferTo(new File(folder, newName));
                filePath = req.getScheme() + "://" + req.getServerName() + ":" +req.getServerPort() + "/uploadFile/" + format + newName;
            } catch (IOException e) {
                e.printStackTrace();
            }

            list.add(filePath);
        }
        return list;
    }

    /**
     * 文件保存在服务器，url地址保存在数据库
     */
    @PostMapping("/upload04")
    public String upload04(@RequestParam MultipartFile file, HttpServletRequest request){
        if(!file.isEmpty()){
            String uploadPath = "D:\\uploadFile";
            // 如果目录不存在则创建
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String OriginalFilename = file.getOriginalFilename();//获取原文件名
            String suffixName = OriginalFilename.substring(OriginalFilename.lastIndexOf("."));//获取文件后缀名
            //重新随机生成名字
            String filename = UUID.randomUUID().toString() +suffixName;
            File localFile = new File(uploadPath+"\\"+filename);
            try {
                file.transferTo(localFile); //把上传的文件保存至本地
                /**
                 * 这里应该把filename保存到数据库,供前端访问时使用
                 */
                return filename;//上传成功，返回保存的文件地址
            }catch (IOException e){
                e.printStackTrace();
                System.out.println("上传失败");
                return "";
            }
        }else{
            System.out.println("文件为空");
            return "";
        }
    }
}
