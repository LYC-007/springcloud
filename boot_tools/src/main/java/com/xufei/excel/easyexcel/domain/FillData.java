package com.xufei.excel.easyexcel.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FillData {
    private String name;
    private double number;
    // lombok 会生成getter/setter方法

    // 构建数据
    private static List<FillData> getFillData() {
        List<FillData> fillDataList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            // 构建数据
            FillData fillData = FillData.builder()
                    .name("小米" + i)
                    .number(i * 1000 + 88.88)
                    .build();
            fillDataList.add(fillData);
        }
        return fillDataList;
    }
}
