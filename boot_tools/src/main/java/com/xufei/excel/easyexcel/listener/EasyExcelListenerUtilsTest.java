package com.xufei.excel.easyexcel.listener;


import com.alibaba.excel.EasyExcel;
import com.xufei.excel.easyexcel.domain.UserModel;

import java.util.List;
import java.util.function.Consumer;

public class EasyExcelListenerUtilsTest {
    /**
     * 采用解耦的自定义监听器读取Excel, 可以实现任何数据模型bean的读取
     */
    public void testReadExcelN() {
        // 读取的excel文件路径
        String filename = "D:\\study\\excel\\user1.xlsx";
        // 读取excel
        EasyExcel.read(filename, UserModel.class,
                EasyExcelListenerUtils.getReadListener(dataProcess()))
                .doReadAll(); // 读取全部sheet
    }

    /**
     *  传给监听器的是一个处理解析数据的函数, 当调用consumer的accept方法时就会调用传递的函数逻辑
     *  这里传递的函数是对解析结果集的遍历打印操作, 也可以是数据入库操作
     * @return
     */
    public Consumer<List<UserModel>> dataProcess() {
        Consumer<List<UserModel>> consumer = users -> users.forEach(System.out::println);
        return consumer;
    }


}
