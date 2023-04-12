package com.xufei.springcloud.controller.upload;

import com.xufei.springcloud.result.AjaxResult;
import com.xufei.springcloud.utils.NewBeeMallUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/admin")
public class UploadController {

    @Autowired
    private StandardServletMultipartResolver standardServletMultipartResolver;

    @PostMapping({"/upload/file"})
    @ResponseBody
    public AjaxResult upload(HttpServletRequest httpServletRequest, @RequestParam("file") MultipartFile file) throws URISyntaxException, IOException {
        String fileName = file.getOriginalFilename();
        BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
        if (bufferedImage == null) {
            return  AjaxResult.error("请上传图片类型的文件！");
        }
        String suffixName = Objects.requireNonNull(fileName).substring(fileName.lastIndexOf("."));
        //生成文件名称通用方法
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Random r = new Random();
        String newFileName = sdf.format(new Date()) + r.nextInt(100) + suffixName;
        File fileDirectory = new File( "D:\\upload\\");
        //创建文件
        File destFile = new File( "D:\\upload\\" + newFileName);
        try {
            if (!fileDirectory.exists()) {
                if (!fileDirectory.mkdir()) {
                    throw new IOException("文件夹创建失败,路径为：" + fileDirectory);
                }
            }
            file.transferTo(destFile);
            URI uri = new URI(httpServletRequest.getRequestURL().toString());

            URI effectiveURI = new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(), "/upload/" + newFileName, null, null);
            return AjaxResult.success().setData(effectiveURI);
        } catch (IOException e) {
            e.printStackTrace();
            return  AjaxResult.error("文件上传失败");
        }
    }

    @PostMapping({"/upload/files"})
    @ResponseBody
    public AjaxResult uploadV2(HttpServletRequest httpServletRequest) throws URISyntaxException, IOException {
        List<MultipartFile> multipartFiles = new ArrayList<>(8);
        if (standardServletMultipartResolver.isMultipart(httpServletRequest)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) httpServletRequest;
            Iterator<String> iter = multiRequest.getFileNames();
            int total = 0;
            while (iter.hasNext()) {
                if (total > 5) {
                    return  AjaxResult.error("最多上传5张图片");
                }
                total += 1;
                MultipartFile file = multiRequest.getFile(iter.next());
                BufferedImage bufferedImage = ImageIO.read(Objects.requireNonNull(file).getInputStream());
                // 只处理图片类型的文件
                if (bufferedImage != null) {
                    multipartFiles.add(file);
                }
            }
        }
        if (CollectionUtils.isEmpty(multipartFiles)) {
            return  AjaxResult.error("请选择图片类型的文件上传");
        }
        if ( multipartFiles.size() > 5) {
            return  AjaxResult.error("最多上传5张图片");
        }
        List<String> fileNames = new ArrayList<>(multipartFiles.size());
        for (MultipartFile multipartFile : multipartFiles) {
            String fileName = multipartFile.getOriginalFilename();
            String suffixName = Objects.requireNonNull(fileName).substring(fileName.lastIndexOf("."));
            //生成文件名称通用方法
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            Random r = new Random();
            String newFileName = sdf.format(new Date()) + r.nextInt(100) + suffixName;
            File fileDirectory = new File("D:\\upload\\");
            //创建文件
            File destFile = new File("D:\\upload\\" + newFileName);
            try {
                if (!fileDirectory.exists()) {
                    if (!fileDirectory.mkdir()) {
                        throw new IOException("文件夹创建失败,路径为：" + fileDirectory);
                    }
                }
                multipartFile.transferTo(destFile);
                fileNames.add(NewBeeMallUtils.getHost(new URI(httpServletRequest.getRequestURL() + "")) + "/upload/" + newFileName);
            } catch (IOException e) {
                e.printStackTrace();
                return AjaxResult.error("文件上传失败");
            }
        }
        return AjaxResult.success().setData(fileNames);
    }

}
