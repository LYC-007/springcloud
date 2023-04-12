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
public class MemberVip {
    private Integer id;
    private String name;
    private String gender;
    private String birthday;
    // 构建数据
    public static List<MemberVip> getMemberVip() {
        List<MemberVip> fillDataList = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            // 构建数据
            MemberVip fillData = MemberVip.builder()
                   .id(i)
                    .name("root"+i)
                    .gender("男")
                    .birthday("2002-12-0"+i)
                    .build();
            fillDataList.add(fillData);
        }
        return fillDataList;
    }
}
