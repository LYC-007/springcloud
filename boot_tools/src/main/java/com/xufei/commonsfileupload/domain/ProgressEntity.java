package com.xufei.commonsfileupload.domain;

import lombok.Data;
@Data
public class ProgressEntity {
    // 读取的文件的比特数
    private long pBytesRead = 0L;
    // 文件的总大小
    private long pContentLength = 0L;
    // 目前正在读取第几个文件
    private int pItems;

    private long startTime = System.currentTimeMillis();
}
