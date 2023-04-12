package com.xufei.excel.easyexcel.fill;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.enums.WriteDirectionEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.xufei.excel.easyexcel.domain.FillData;
import com.xufei.excel.easyexcel.domain.MemberVip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @description: some desc
 * @Date: 2023/1/10 23:45
 */
public class FillTest {

    /**
     * 简单填充
     * 填充单个属性使用{}作为占位符, 在大括号里面定义属性名称, 如果{}想不作为占位符展示出来,可以使用反斜杠进行转义.
     */
    public void testFillExcel() {
        // 根据哪个模板进行填充
        String template = "D:\\study\\excel\\template.xlsx";  //根据这个模板:简单填充Excel模板.png
        // 填充完成之后的excel
        String fillname = "D:\\study\\excel\\fill.xlsx"; //效果 : 简单填充后的效果.png
        // 构建数据
        FillData fillData = FillData.builder().name("小米").number(888.888).build();
        // 填充excel 单组数据填充
        EasyExcel.write(fillname).withTemplate(template).sheet(0).doFill(fillData);
    }

    /**
     * 列表填充
     */
    public void testFillExcel2() {
        // 根据哪个模板进行填充
        String template = "D:\\study\\excel\\template2.xlsx";  //列表填充Excel模板.png
        // 填充完成之后的excel
        String fillname = "D:\\study\\excel\\fill2.xlsx";  //  列表填充完成之后的excel.png
        // 填充excel 多组数据重复填充
        EasyExcel.write(fillname)
                .withTemplate(template)
                .sheet(0)
                .doFill(FillData.builder());
    }
    /**
     * 组合填充
     */
    public void testFillExcel3() {
        // 根据哪个模板进行填充
        String template = "D:\\study\\excel\\template3.xlsx"; //组合填充Excel模板.png
        // 填充完成之后的excel
        String fillname = "D:\\study\\excel\\fill3.xlsx"; // 组合填充完成之后的excel.png
        // 创建填充配置 换行填充  如果没有设置填充配置换行FillConfig为true , 效果将是单组填充的数据会覆盖所在行的多组数据填充效果.
        FillConfig fillConfig = FillConfig.builder().forceNewRow(true).build();
        // 创建写对象
        ExcelWriter excelWriter =EasyExcel.write(fillname).withTemplate(template).build();
        // 创建Sheet对象
        WriteSheet sheet = EasyExcel.writerSheet(0).build();
        // 多组填充excel
        excelWriter.fill(FillData.builder(), fillConfig, sheet);
        // 单组填充
        HashMap<String, Object> unitData = new HashMap<>();
        unitData.put("nickname", "张三");
        unitData.put("salary", 8088.66);
        excelWriter.fill(unitData, sheet);
        // 关闭流
        excelWriter.finish();
    }

    /**
     * 水平填充
     */
    public void testFillExcel4() {
        // 根据哪个模板进行填充
        String template = "D:\\study\\excel\\template4.xlsx";  // 水平填充Excel模板.png
        // 填充完成之后的excel
        String fillname = "D:\\study\\excel\\fill4.xlsx";  //水平填充完成之后的excel.png
        // 创建填充配置 水平填充
        FillConfig fillConfig = FillConfig.builder()
                // .forceNewRow(true)
                .direction(WriteDirectionEnum.HORIZONTAL).build();
        // 创建写对象
        ExcelWriter excelWriter = EasyExcel.write(fillname,FillData.class).withTemplate(template).build();
        // 创建Sheet对象
        WriteSheet sheet = EasyExcel.writerSheet(0).build();
        // 多组填充excel
        excelWriter.fill(FillData.builder(), fillConfig, sheet);
        // 关闭流
        excelWriter.finish();
    }

    /**
     * 报表导出案例
     */
    public void testFillExcel5() {
        // 根据哪个模板进行填充
        String template = "D:\\study\\excel\\template5.xlsx"; //报表导出案例Excel模板.png
        // 填充完成之后的excel
        String fillname = "D:\\study\\excel\\fill5.xlsx";  //报表导出案例填充完成之后的excel.png
        // 创建填充配置
        FillConfig fillConfig = FillConfig.builder().forceNewRow(true).build();
        // 创建写对象
        ExcelWriter excelWriter = EasyExcel.write(fillname).withTemplate(template).build();
        // 创建Sheet对象
        WriteSheet sheet = EasyExcel.writerSheet(0).build();
        /***准备数据 start*****/
        HashMap<String, Object> dateMap = new HashMap<>();
        dateMap.put("date", "2021-08-08");
        HashMap<String, Object> memberMap = new HashMap<>();
        memberMap.put("increaseCount", 500);
        memberMap.put("totalCount", 999);
        HashMap<String, Object> curMonthMemberMap = new HashMap<>();
        curMonthMemberMap.put("increaseCountWeek", 100);
        curMonthMemberMap.put("increaseCountMonth", 200);
        List<MemberVip> memberVips = MemberVip.getMemberVip();
        /***准备数据 end*****/
        // 多组填充excel
        excelWriter.fill(dateMap, sheet);
        excelWriter.fill(memberMap, sheet);
        excelWriter.fill(curMonthMemberMap, sheet);
        excelWriter.fill(memberVips, fillConfig, sheet);
        // 关闭流
        excelWriter.finish();
    }
}
