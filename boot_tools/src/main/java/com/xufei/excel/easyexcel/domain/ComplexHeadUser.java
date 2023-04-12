package com.xufei.excel.easyexcel.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ExcelProperty注解的value属性是一个数组类型, 设置多个head时会自动合并.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ComplexHeadUser {
    @ExcelProperty(value = {"group1", "用户编号"}, index = 0)
    private Integer userId;
    @ExcelProperty(value = {"group1", "姓名"}, index = 1)
    private String userName;
    @ExcelProperty(value = {"group2", "入职时间"}, index = 2)
    private Date hireDate;
    // lombok 会生成getter/setter方法
}
