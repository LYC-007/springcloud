package com.xufei.fastjson2;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
public class Hero{
    private String name;
    private Integer age;
    private Integer score;
    private String groupTag;

    public static List<Hero> getList(){
        return Arrays.asList(
                new Hero("张三", 10, 23, "A"),
                new Hero("李四", 20, 19, "A"),
                new Hero("王五", 30, 20, "B"),
                new Hero("赵六", 40, 21, "B"),
                new Hero("洪七", 50, 25, "C"),
                new Hero("重八", 60, 45, "C")
        );
    }
}
