package com.xufei.demo.domain;

import lombok.Data;
import lombok.ToString;

/**
 * @Author: XuFei
 * @Date: 2022/8/12 16:46
 */

@Data
@ToString
public class User {

    private Integer id;
    private String name;
    private String sex;
}
