package com.xufei.excel.easyexcel;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.xufei.excel.easyexcel.domain.ImageData;
import com.xufei.excel.easyexcel.domain.User;
import javax.swing.filechooser.FileSystemView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.xufei.excel.easyexcel.domain.User.getUserData;

public class EasyExcelWriterTest {

    /**
     * 最简单的写
     */
    public static void testWriteExcel() {
        // 获取桌面路径
        FileSystemView fsv = FileSystemView.getFileSystemView();
        String desktop = fsv.getHomeDirectory().getPath();
        String filename = desktop + "/user.xls";
        //设置不需要导出的属性
        Set<String> excludeField = new HashSet<>();
        excludeField.add("hireDate");
        excludeField.add("salary");

        // 设置要导出的字段
        Set<String> includeFields = new HashSet<>();
        includeFields.add("userName");
        includeFields.add("hireDate");
        // 向Excel中写入数据 也可以通过 head(Class<?>) 指定数据模板
        EasyExcel.write(filename, User.class)
                .excludeColumnFieldNames(excludeField)// 设置排除的属性 也可以在数据模型的字段上加@ExcelIgnore注解排除
                //.includeColumnFieldNames(includeFields)
                .sheet("用户信息")
                .doWrite(getUserData());
    }

    /**
     * 最简单的写
     */
    public void testWriteExcel2() {
        // 获取桌面路径
        FileSystemView fsv = FileSystemView.getFileSystemView();
        String desktop = fsv.getHomeDirectory().getPath();
        String filename = desktop + "/user.xls";
        // 创建ExcelWriter对象
        ExcelWriter excelWriter = EasyExcel.write(filename, User.class).build();
        // 创建Sheet对象
        WriteSheet writeSheet = EasyExcel.writerSheet("用户信息").build();
        // 向Excel中写入数据
        excelWriter.write(getUserData(), writeSheet);
        // 关闭流
        excelWriter.finish();
    }
    public void testWriteExcel3() {
        // 获取桌面路径
        FileSystemView fsv = FileSystemView.getFileSystemView();
        String desktop = fsv.getHomeDirectory().getPath();
        String filename = desktop + "/user.xls";

        Set<String> excludeField = new HashSet<>();
        excludeField.add("hireDate");
        excludeField.add("salary");
        // 写Excel
        EasyExcel.write(filename, User.class)
                .excludeColumnFieldNames(excludeField)// 设置排除的属性 也可以在数据模型的字段上加@ExcelIgnore注解排除
                .sheet("用户信息")
                .doWrite(getUserData());
    }

    /**
     * 写入图片到Excel
     */
    public void testWriteImageToExcel() throws IOException {
        // 获取桌面路径
        FileSystemView fsv = FileSystemView.getFileSystemView();
        String desktop = fsv.getHomeDirectory().getPath();
        String filename = desktop + "/ImageData.xls";
        // 图片位置
        String imagePath = "D:\\study\\excel\\me.jpg";
        // 网络图片
        URL url = new URL("https://cn.bing.com/th?id=OHR.TanzaniaBeeEater_ZH-CN3246625733_1920x1080.jpg&rf=LaDigue_1920x1080.jpg&pid=hp");
        // 将图片读取到二进制数据中
        byte[] bytes = new byte[(int) new File(imagePath).length()];
        InputStream inputStream = new FileInputStream(imagePath);
        inputStream.read(bytes, 0, bytes.length);

        List<ImageData> imageDataList = new ArrayList<>();

        // 创建数据模板
        ImageData imageData = ImageData.builder()
                .file(new File(imagePath))
                .inputStream(new FileInputStream(imagePath))
                .str(imagePath)
                .byteArr(bytes)
                .url(url)
                .build();
        // 添加要写入的图片模型
        imageDataList.add(imageData);

        // 写数据
        EasyExcel.write(filename, ImageData.class)
                .sheet("帅哥")
                .doWrite(imageDataList);
    }

}
