package com.xufei.excel.easyexcel.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
public class DictEeVo {

    @ExcelProperty(value = "id" ,index = 0)
    private Long id;

    @ExcelProperty(value = "上级id" ,index = 1)
    private Long parentId;

    @ExcelProperty(value = "名称" ,index = 2)    //---->value对应Excel每列的标题，index代表第几列
    private String name;

    @ExcelProperty(value = "值" ,index = 3)
    private String value;



    @ExcelProperty(value = "编码" ,index = 4)
    private String dictCode;


    @DateTimeFormat(fallbackPatterns = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "操作时间",index = 5)
    private Date time;
}

