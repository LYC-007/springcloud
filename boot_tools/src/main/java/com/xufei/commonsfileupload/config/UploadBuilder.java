package com.xufei.commonsfileupload.config;

import com.xufei.commonsfileupload.listener.UploadProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/*
创建一个文件上传解析器
我们知道上传有一些限制条件，比如上传文件大小，显示的上传进度等，如果是多文件上传，还有总文件大小的限制条件。
由于是个组件，这些参数都需要开发者自己定义，因此，在创建文件上传解析器时，我才用了builder模式。
 */
public class UploadBuilder {
    public ServletFileUpload upload = new ServletFileUpload();

    public UploadBuilder(Builder builder){
        upload.setSizeMax(builder.sizeMax);
        upload.setFileSizeMax(builder.fileSizeMax);
        upload.setHeaderEncoding(builder.headerEncoding);
        upload.setFileItemFactory(builder.factory);
        upload.setProgressListener(builder.listener);
    }

    public static final class Builder {
        //总文件大小上限，默认无限大
        private long sizeMax = -1;
        //单个文件的大小上限，默认无限大
        private long fileSizeMax = -1;
        //编码方式
        private String headerEncoding;
        private final DiskFileItemFactory factory;
        //进度条Listener
        private UploadProgressListener listener;
        public Builder(DiskFileItemFactory factory) {
            this.factory = factory;
        }

        public Builder sizeMax(long value){
            this.sizeMax = value;
            return this;
        }

        public Builder fileSizeMax(long value){
            this.fileSizeMax = value;
            return this;
        }

        public Builder headerEncoding(String value){
            this.headerEncoding = value;
            return this;
        }

        public Builder listener(UploadProgressListener listener){
            this.listener = listener;
            return this;
        }

        public ServletFileUpload build() {
            return new UploadBuilder(this).upload;
        }

    }

}