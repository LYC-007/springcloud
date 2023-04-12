package com.xufei.commonsfileupload.resolver;

import com.xufei.commonsfileupload.listener.UploadProgressListener;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * commons-fileupload 和 spring boot/mvc 自带的上传底层框架有冲突:
 *      spring mvc在处理请求的时候，有一个判断是否是上传请求，如果是上传请求，则进行解析转为MultipartHttpServletRequest请求，导致 commons-fileupload的根本获取不了请求数据.
 *      @SpringBootApplication(exclude = {MultipartAutoConfiguration.class}) 在启动类上的注解,排除MultipartAutoConfiguration,很明显这是springboot底层上传框架的自动配置类
 *      MultipartResolver 就是 springmvc自带的底层上传框架,用来解析文件的接口,那就不要配置它的bean.采用我们自己的；
 */
@Configuration
public class MyCommonsMultipartResolver extends CommonsMultipartResolver {
    @Autowired
    private UploadProgressListener listener;
    @Override
    public boolean isMultipart(HttpServletRequest request) {
        if (request.getRequestURI().contains("/file/upload")) {
            //根据自己的请求路径判断当前请求是否是文件上次请求，如果是则不做任何事，不是则调用Spring底层框架采用默认方法来处理该请求
            return false;
        }
        return super.isMultipart(request);
    }
    @Override
    public MultipartHttpServletRequest resolveMultipart(final HttpServletRequest request) {
        return super.resolveMultipart(request);
    }
    @Override
    protected MultipartParsingResult parseRequest(HttpServletRequest request) throws MultipartException {
        String encoding = determineEncoding(request);
        FileUpload fileUpload = prepareFileUpload(encoding);
        // 文件上传进度监听器设置session用于存储上传进度
        listener.setHttpSession(request.getSession());

        fileUpload.setProgressListener(listener);// 将文件上传进度监听器加入到 fileUpload 中
        try {
            List<FileItem> fileItems = ((ServletFileUpload) fileUpload).parseRequest(request);
            return parseFileItems(fileItems, encoding);
        } catch (FileUploadBase.SizeLimitExceededException ex) {
            throw new MaxUploadSizeExceededException(fileUpload.getSizeMax(), ex);
        } catch (FileUploadBase.FileSizeLimitExceededException ex) {
            throw new MaxUploadSizeExceededException(fileUpload.getFileSizeMax(), ex);
        } catch (FileUploadException ex) {
            throw new MultipartException("Failed to parse multipart servlet request", ex);
        }
    }
}