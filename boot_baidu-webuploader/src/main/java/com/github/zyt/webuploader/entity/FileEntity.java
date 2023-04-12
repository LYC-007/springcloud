package com.github.zyt.webuploader.entity;

import lombok.Data;

@Data
public class FileEntity {
    /**
     * 文件名称
     */
    String fileName;
    /**
     * 修改时间
     */
    String modifyTime;
    /**
     * 文件类别
     */
    String fileType;
    /**
     * 文件大小
     */
    String fileSize;

    public FileEntity(String fileName, String modifyTime, String fileType, String fileSize) {
        this.fileName = fileName;
        this.modifyTime = modifyTime;
        this.fileType = fileType;
        this.fileSize = fileSize;
    }
}
