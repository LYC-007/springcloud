package com.xufei.excel.easyexcel.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.xufei.excel.easyexcel.converter.GenderConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 写的数据转换器
 * 在实际应用场景中, 我们系统db存储的数据可以是枚举, 在界面或导出到Excel文件需要展示为对于的枚举值形式.
 *
 * 比如: 性别, 状态等. EasyExcel提供了转换器接口Converter供我们使用, 我们只需要自定义转换器实现接口,
 * 并将自定义转换器类型传入要转换的属性字段中. 以下面的性别gender字段为例:
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserModel {

    @ExcelProperty(value = "用户编号", index = 0)
    private Integer userId;

    @ExcelProperty(value = "姓名", index = 1)
    private String userName;

    // 性别添加了转换器, db中存入的是integer类型的枚举 0 , 1 ,2
    @ExcelProperty(value = "性别", index = 3, converter = GenderConverter.class)
    private Integer gender;

    @ExcelProperty(value = "工资", index = 4)
    @NumberFormat(value = "###.#")
    private Double salary;

    @ExcelProperty(value = "入职时间", index = 2)
    @DateTimeFormat(value = "yyyy年MM月dd日 HH时mm分ss秒")
    private Date hireDate;
    // lombok 会生成getter/setter方法
}
