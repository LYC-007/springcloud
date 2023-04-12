package com.xufei.springcloud.controller.upload;


import com.xufei.springcloud.exception.FileSizeLimitExceededException;
import com.xufei.springcloud.exception.FileTypeException;
import com.xufei.springcloud.utils.FileUploadUtils;
import com.xufei.springcloud.utils.MimeTypeUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.impl.FileUploadIOException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * String filePath = request.getServletContext().getRealPath("/");获取的是tomcat目录下webapps下的目录及类路径(class文件存放的路径),因为我使用的是SpringBoot项目,
 * 而SringBoot项目内嵌了tomcat,路径为C:\Users\L15096000421\AppData\Local\Temp\tomcat-docbase.2191751665660359817.8080\的一个临时目录，重启项目，文件就丢失了
 * <p>
 * <p>
 * 还有使用String filePath = request.getServletContext().getRealPath("/")做为下载的路径去下载文件,后台报错没有权限,使用绝对路径下载,及使用绝对路径上传
 * 可以使用 private static final String parentPath = ClassUtils.getDefaultClassLoader().getResource("static/images").getPath();获取springboot项目static/images的目录
 */
@Slf4j
@RestController
public class ExampleFileUploadController {


    @RequestMapping(value = "/upload07")
    public String upload(HttpServletRequest request, @RequestParam("file") MultipartFile file, Model model) {
        // 测试MultipartFile接口的各个方法
        System.out.println("文件类型ContentType=" + file.getContentType());
        System.out.println("文件组件名称Name=" + file.getName());
        System.out.println("文件原名称OriginalFileName=" + file.getOriginalFilename());
        System.out.println("文件大小Size=" + file.getSize() / 1024 + "KB");
        try {
            if (file.isEmpty()) {
                return "文件为空";
            }
            // 获取文件名
            String fileName = file.getOriginalFilename();
            log.info("上传的文件名为：" + fileName);
            // 获取文件的后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            log.info("文件的后缀名为：" + suffixName);


            //原文链接：https://blog.csdn.net/weixin_45535665/article/details/124087545
            //String filePath = request.getServletContext().getRealPath("/"); // 获取文件相对类路径,每启动一次服务器该地址就会改变
            File dir = new File("E:/Java_Project/springcloud/boor_basic/target/classes/static/upload/");
            if (!dir.exists()) { //检测目录是否存在
                dir.mkdirs();   //创建当前的目录
            }
            //String filePath = request.getServletContext().getRealPath("/");

            String newImg = UUID.randomUUID() + suffixName;
            //构造一个路径

            File dest = new File(dir,newImg);
            file.transferTo(dest);// 文件写入
            model.addAttribute("msg", "<font color=\"green\">上传成功</font>");
            model.addAttribute("img", newImg);
            return "upload";
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
        model.addAttribute("msg", "<font color=\"green\">上传失败</font>");
        return "upload";
    }

    @PostMapping("/batch")
    public String handleFileUpload(Model model, HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            //文件绝对路径,项目中一般使用相对类路径,即使文件变更路径也会跟着变
            String filePath = request.getServletContext().getRealPath("D:\\dev_workspace\\springboot-learning-examples\\springboot-13-fileupload\\src\\main\\resources\\static");
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    stream = new BufferedOutputStream(new FileOutputStream(
                            new File(filePath + file.getOriginalFilename())));//设置文件路径及名字
                    stream.write(bytes);// 写入
                    stream.close();
                } catch (Exception e) {
                    stream = null;
                    model.addAttribute("msg", "第 " + i + " 个文件上传失败 ==> "
                            + e.getMessage());
                    return "upload";
                }
            } else {
                model.addAttribute("msg", "第 " + i + " 个文件上传失败因为文件为空");
                return "upload";
            }
        }
        model.addAttribute("msg", "上传成功");
        return "upload";
    }

    @PostMapping("/download")
    public String downloadFile(Model model, HttpServletRequest request, HttpServletResponse response, String fileName) {
        if (fileName != null) {
            //设置文件路径   真实环境是存放在数据库中的
            // String filePath = request.getServletContext().getRealPath("/");
            //文件绝对路径,项目中一般使用相对类路径,即使文件变更路径也会跟着变
            String filePath = request.getServletContext().getRealPath("D:\\dev_workspace\\springboot-learning-examples\\springboot-13-fileupload\\src\\main\\resources\\static");


            File file = new File("E:/Java_Project/springcloud/boor_basic/target/classes/static/upload/");
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);

                    bis = new BufferedInputStream(fis);
                    // 创建输出对象
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    model.addAttribute("msg", "<font color=\"green\">下载成功</font>");
                    return "upload";
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        model.addAttribute("msg", "<font color=\"red\">下载失败</font>");
        return "upload";
    }


    /**
     * 单文件上传(简单demo)
     *
     * @param file
     *            接收到的文件
     */
    @RequestMapping(value = "/file/upload", method = RequestMethod.POST)
    public void fileUpload(@RequestParam("fileName") MultipartFile file) {
        // 先设定一个放置上传文件的文件夹(该文件夹可以不存在，下面会判断创建)
        String deposeFilesDir = "C:\\Users\\dengshuai.ASPIRE\\Desktop\\file\\";
        // 判断文件是否有内容
        if (file.isEmpty()) {
            System.out.println("该文件无任何内容!!!");
        }
        // 获取附件原名(有的浏览器如chrome获取到的是最基本的含 后缀的文件名,如myImage.png)
        // 获取附件原名(有的浏览器如ie获取到的是含整个路径的含后缀的文件名，如C:\\Users\\images\\myImage.png)
        String fileName = file.getOriginalFilename();
        // 如果是获取的含有路径的文件名，那么截取掉多余的，只剩下文件名和后缀名
        int index = Objects.requireNonNull(fileName).lastIndexOf("\\");
        if (index > 0) {
            fileName = fileName.substring(index + 1);
        }
        // 判断单个文件大于1M
        long fileSize = file.getSize();
        if (fileSize > 1024 * 1024) {
            System.out.println("文件大小为(单位字节):" + fileSize);
            System.out.println("该文件大于1M");
        }
        // 当文件有后缀名时
        if (fileName.contains(".")) {
            // split()中放正则表达式; 转义字符"\\."代表 "."
            String[] fileNameSplitArray = fileName.split("\\.");
            // 加上random戳,防止附件重名覆盖原文件
            fileName = fileNameSplitArray[0] + (int) (Math.random() * 100000) + "." + fileNameSplitArray[1];
        }
        // 当文件无后缀名时(如C盘下的hosts文件就没有后缀名)
        if (!fileName.contains(".")) {
            // 加上random戳,防止附件重名覆盖原文件
            fileName = fileName + (int) (Math.random() * 100000);
        }
        System.out.println("fileName:" + fileName);

        // 根据文件的全路径名字(含路径、后缀),new一个File对象dest
        File dest = new File(deposeFilesDir + fileName);
        // 如果该文件的上级文件夹不存在，则创建该文件的上级文件夹和其祖辈级文件夹;
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            // 将获取到的附件file,transferTo写入到指定的位置(即:创建dest时，指定的路径)
            file.transferTo(dest);
        } catch (IllegalStateException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("文件的全路径名字(含路径、后缀)>>>>>>>" + deposeFilesDir + fileName);
    }

    /**
     * 多文件上传(简单demo)
     *
     * @param files
     *            文件数组
     * @throws IOException
     */
    @RequestMapping(value = "/file/mulFileUpload", method = RequestMethod.POST)
    public void mulFileUpload(@RequestParam("fileName") MultipartFile[] files) {
        // 先设定一个放置上传文件的文件夹(该文件夹可以不存在，下面会判断创建)
        String deposeFilesDir = "C:\\Users\\dengshuai.ASPIRE\\Desktop\\file\\";

        for (MultipartFile file : files) {
            // 判断文件是否有内容
            if (file.isEmpty()) {
                System.out.println("该文件无任何内容!!!");
            }
            // 获取附件原名(有的浏览器如chrome获取到的是最基本的含 后缀的文件名,如myImage.png)
            // 获取附件原名(有的浏览器如ie获取到的是含整个路径的含后缀的文件名，如C:\\Users\\images\\myImage.png)
            String fileName = file.getOriginalFilename();
            // 如果是获取的含有路径的文件名，那么截取掉多余的，只剩下文件名和后缀名
            if (Objects.requireNonNull(fileName).indexOf("\\") > 0) {
                int index = fileName.lastIndexOf("\\");
                fileName = fileName.substring(index + 1);
            }
            // 判断单个文件大于1M
            long fileSize = file.getSize();
            if (fileSize > 1024 * 1024) {
                System.out.println("文件大小为(单位字节):" + fileSize);
                System.out.println("该文件大于1M");
            }
            // 当文件有后缀名时
            if (fileName.contains(".")) {
                // split()中放正则表达式; 转义字符"\\."代表 "."
                String[] fileNameSplitArray = fileName.split("\\.");
                // 加上random戳,防止附件重名覆盖原文件
                fileName = fileNameSplitArray[0] + (int) (Math.random() * 100000) + "." + fileNameSplitArray[1];
            }
            // 当文件无后缀名时(如C盘下的hosts文件就没有后缀名)
            if (!fileName.contains(".")) {
                // 加上random戳,防止附件重名覆盖原文件
                fileName = fileName + (int) (Math.random() * 100000);
            }
            System.out.println("fileName:" + fileName);
            // 根据文件的全路径名字(含路径、后缀),new一个File对象dest
            File dest = new File(deposeFilesDir + fileName);
            // 如果pathAll路径不存在，则创建相关该路径涉及的文件夹;
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                // 将获取到的附件file,transferTo写入到指定的位置(即:创建dest时，指定的路径)
                file.transferTo(dest);
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
            System.out.println("文件的全路径名字(含路径、后缀)>>>>>>>" + deposeFilesDir + fileName);
        }
    }



}
