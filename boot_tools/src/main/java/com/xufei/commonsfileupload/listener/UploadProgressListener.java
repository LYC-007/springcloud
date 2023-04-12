package com.xufei.commonsfileupload.listener;

import com.xufei.commonsfileupload.domain.ProgressEntity;
import org.apache.commons.fileupload.ProgressListener;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

/*
    由于需求要展示上传文件的进度条，因此在这里设置了进度条Listener
 */

@Component
public class UploadProgressListener implements ProgressListener {
    private HttpSession httpSession;

    public void setHttpSession(HttpSession httpSession) {
        this.httpSession = httpSession;
        ProgressEntity status = new ProgressEntity();
        //默认进度为0
        httpSession.setAttribute("status", status);
    }

    @Override
    public void update(long pBytesRead, long pContentLength, int pItems) {
        ProgressEntity status = (ProgressEntity) httpSession.getAttribute("status");
        status.setPBytesRead(pBytesRead);//到目前为止读取文件的比特数
        status.setPContentLength(pContentLength);//文件总大小
        status.setPItems(pItems);//目前正在读取第几个文件
    }

}