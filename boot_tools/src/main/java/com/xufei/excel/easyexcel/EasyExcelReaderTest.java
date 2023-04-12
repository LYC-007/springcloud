package com.xufei.excel.easyexcel;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.xufei.excel.easyexcel.domain.DemoData;

import java.util.LinkedHashMap;

public class EasyExcelReaderTest {
    /**
     * 在读取Excel表格数据时, 将读取的每行记录映射成一条LinkedHashMap记录, 而没有映射成实体类.
     */
    public void testRead() {
        String filename = "D:\\study\\excel\\read.xlsx";
        // 创建ExcelReaderBuilder对象
        ExcelReaderBuilder readerBuilder = EasyExcel.read();
        // 获取文件对象
        readerBuilder.file(filename);

        // 文件的密码
        //readerBuilder.password("");

        // 指定映射的数据模板,如果没有指定数据模板, 解析的数据会封装成 LinkedHashMap返回
        //  readerBuilder.head(DemoData.class);
        // 指定sheet
        readerBuilder.sheet(0);
        // 自动关闭输入流
        readerBuilder.autoCloseStream(true);
        // 设置Excel文件格式
        readerBuilder.excelType(ExcelTypeEnum.XLSX);
        // 注册监听器进行数据的解析
        readerBuilder.registerReadListener(new AnalysisEventListener<Object>() {
            // 每解析一行数据,该方法会被调用一次
            @Override
            public void invoke(Object demoData, AnalysisContext analysisContext) {
                // 如果没有指定数据模板, 解析的数据会封装成 LinkedHashMap返回
                // demoData instanceof LinkedHashMap 返回 true
                System.out.println("解析数据为:" + demoData.toString());
            }

            // 全部解析完成被调用
            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                System.out.println("解析完成...");
                // 可以将解析的数据保存到数据库
            }
        });
        readerBuilder.doReadAll();//.doReadAll(); // 读取全部sheet,这一行相当于下面的三行
   /*
    ExcelReader excelReader = readerBuilder.build();// 构建读取器
    excelReader.readAll(); // 读所有sheet
    excelReader.finish();// 关闭流
    */

    }

    /**
     * 读取excel代码
     * <p>
     * 关键是写一个监听器,实现AnalysisEventListener, 每解析一行数据会调用invoke方法返回解析的数据, 当全部解析完成后会调用doAfterAllAnalysed方法.
     * 我们重写invoke方法和doAfterAllAnalysed方法即可.
     */
    public void testReadExcel() {
        // 读取的excel文件路径
        String filename = "D:\\study\\excel\\read.xlsx";
        // 读取excel
        EasyExcel.read(filename, DemoData.class, new AnalysisEventListener<DemoData>() {
            // 每解析一行数据,该方法会被调用一次
            @Override
            public void invoke(DemoData demoData, AnalysisContext analysisContext) {
                System.out.println("解析数据为:" + demoData.toString());
            }

            // 全部解析完成被调用
            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                System.out.println("解析完成...");
                // 可以将解析的数据保存到数据库
            }
        }).sheet().doRead();
    }

    public void testReadExcel2() {
        // 读取的excel文件路径
        String filename = "D:\\study\\excel\\read.xlsx";
        // 创建ExcelReader对象
        ExcelReader excelReader = EasyExcel.read(filename, DemoData.class, new AnalysisEventListener<DemoData>() {
            // 每解析一行数据,该方法会被调用一次
            @Override
            public void invoke(DemoData demoData, AnalysisContext analysisContext) {
                System.out.println("解析数据为:" + demoData.toString());
            }

            // 全部解析完成被调用
            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                System.out.println("解析完成...");
                // 可以将解析的数据保存到数据库
            }
        }).build();
        // 创建sheet对象,并读取Excel的第一个sheet(下标从0开始), 也可以根据sheet名称获取
        ReadSheet sheet = EasyExcel.readSheet(0).build();
        // 读取sheet表格数据, 参数是可变参数,可以读取多个sheet
        excelReader.read(sheet);
        // 需要自己关闭流操作,在读取文件时会创建临时文件,如果不关闭,会损耗磁盘,严重的磁盘爆掉
        excelReader.finish();
    }

    /**
     * 读所有sheet：读方式一, 使用ExcelReaderBuilder#doReadAll方法
     */
    public void testReadExcel_0() {
        // 读取的excel文件路径
        String filename = "D:\\study\\excel\\read.xlsx";
        // 读取excel
        EasyExcel.read(filename, DemoData.class, new AnalysisEventListener<DemoData>() {

            // 每解析一行数据,该方法会被调用一次
            @Override
            public void invoke(DemoData demoData, AnalysisContext analysisContext) {
                System.out.println("解析数据为:" + demoData.toString());
            }

            // 全部解析完成被调用
            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                System.out.println("解析完成...");
                // 可以将解析的数据保存到数据库
            }
        }).doReadAll(); // 读取全部sheet

    }
    /**
     * 读所有sheet：读方式二, 使用ExcelReader#readAll方法
     */
    public void testReadExcel_1() {
        // 读取的excel文件路径
        String filename = "D:\\study\\excel\\read.xlsx";
        // 创建一个数据格式来装读取到的数据
        Class<DemoData> head = DemoData.class;
        // 创建ExcelReader对象
        ExcelReader excelReader = EasyExcel.read(filename, head,
                new AnalysisEventListener<DemoData>() {
                    // 每解析一行数据,该方法会被调用一次
                    @Override
                    public void invoke(DemoData demoData, AnalysisContext analysisContext) {
                        System.out.println("解析数据为:" + demoData.toString());
                    }

                    // 全部解析完成被调用
                    @Override
                    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                        System.out.println("解析完成...");
                        // 可以将解析的数据保存到数据库
                    }
                }).build();
        excelReader.readAll(); // 读所有sheet
        excelReader.finish();// 需要自己关闭流操作,在读取文件时会创建临时文件,如果不关闭,会损耗磁盘,严重的磁盘爆掉


        // 创建sheet对象,并读取Excel的第一个sheet(下标从0开始), 也可以根据sheet名称获取
        ReadSheet sheet = EasyExcel.readSheet(0).build();
        // 读取sheet表格数据 , 参数是可变参数,可以读取多个sheet
        // excelReader.read(sheet);

    }

    /**
     * 读指定的多个sheet:不同sheet表格的数据模板可能不一样,这时候就需要分别构建不同的sheet对象,分别为其指定对于的数据模板.
     */
    public void testReadExcel_2() {
        // 读取的excel文件路径
        String filename = "D:\\study\\excel\\read.xlsx";
        // 构建ExcelReader对象
        ExcelReader excelReader = EasyExcel.read(filename).build();
        // 构建sheet对象
        ReadSheet sheet0 = EasyExcel.readSheet(0)
                .head(DemoData.class) // 指定sheet0的数据模板
                .registerReadListener(new AnalysisEventListener<DemoData>() {
                    // 每解析一行数据,该方法会被调用一次
                    @Override
                    public void invoke(DemoData demoData, AnalysisContext analysisContext) {
                        System.out.println("解析数据为:" + demoData.toString());
                    }
                    // 全部解析完成被调用
                    @Override
                    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                        System.out.println("解析完成...");
                        // 可以将解析的数据保存到数据库
                    }
                }).build();
        // 读取sheet,有几个就构建几个sheet进行读取
        excelReader.read(sheet0);
        // 需要自己关闭流操作,在读取文件时会创建临时文件,如果不关闭,会损耗磁盘,严重的磁盘爆掉
        excelReader.finish();
    }
}
