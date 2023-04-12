package com.xufei.excel.easyexcel.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {
    //将Java对象中指定的属性, 插入到Eexcel表格中的指定列(在Excel表格中进行列排序), 使用index属性指定列顺序.
    @ExcelProperty(value = "用户编号", index = 0)
    private Integer userId;
    @ExcelProperty(value = "姓名", index = 1)
    private String userName;
    @ExcelProperty(value = "性别", index = 3)
    private String gender;

    @NumberFormat(value = "###.#") // 数字格式化,保留1位小数
    //注意: @NumberFormat对于Double类型的数据格式化会失效,建议使用String类型接收数据进行格式化
    @ExcelProperty(value = "工资", index = 4)
    private Double salary;

    @ExcelProperty(value = "入职时间", index = 2)
    @DateTimeFormat(value = "yyyy年MM月dd日 HH时mm分ss秒") // 日期格式化
    private Date hireDate;


    // 根据user模板构建数据
    public static List<User> getUserData() {
        List<User> users = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            User user = User.builder()
                    .userId(i)
                    .userName("admin" + i)
                    .gender(i % 2 == 0 ? "男" : "女")
                    .salary(i * 1000.00)
                    .hireDate(new Date())
                    .build();
            users.add(user);
        }
        return users;
    }

}
