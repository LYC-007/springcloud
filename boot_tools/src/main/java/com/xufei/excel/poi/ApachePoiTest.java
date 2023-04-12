package com.xufei.excel.poi;

import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ApachePoiTest {




    /**
     * 示例一：在桌面上生成一个Excel文件
     * 在4.1之前采用的是枚举类型，setColor(HSSFColor.RED.index);
     *
     * 在4.1之后采用的是枚举类型，setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
     *
     */
    public static void createExcel() throws IOException {
        // 获取桌面路径
        FileSystemView fsv = FileSystemView.getFileSystemView();
        String desktop = fsv.getHomeDirectory().getPath();
        String filePath = desktop + "/template.xls";

        File file = new File(filePath);
        OutputStream outputStream = new FileOutputStream(file);
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Sheet1");
        /**
         * 创建文档摘要信息
         */
        workbook.createInformationProperties();//创建文档信息
        DocumentSummaryInformation dsi= workbook.getDocumentSummaryInformation();//摘要信息
        dsi.setCategory("类别:Excel文件");//类别
        dsi.setManager("管理者:李志伟");//管理者
        dsi.setCompany("公司:--");//公司
        SummaryInformation si = workbook.getSummaryInformation();//摘要信息
        si.setSubject("主题:--");//主题
        si.setTitle("标题:测试文档");//标题
        si.setAuthor("作者:李志伟");//作者
        si.setComments("备注:POI测试文档");//备注
        /**
         * 创建批注
         */
        HSSFPatriarch patr = sheet.createDrawingPatriarch();
        /**
         * dx1         第1个单元格中x轴的偏移量
         * dy1         第1个单元格中y轴的偏移量
         * dx2         第2个单元格中x轴的偏移量
         * dy2         第2个单元格中y轴的偏移量
         * col1        第1个单元格的列号
         * row1        第1个单元格的行号
         * col2        第2个单元格的列号
         * row2        第2个单元格的行号
         */
        HSSFClientAnchor anchor = patr.createAnchor(0, 0, 0, 0, 5, 1, 8, 3);//创建批注位置
        HSSFComment comment = patr.createCellComment(anchor);//创建批注
        comment.setString(new HSSFRichTextString("这是一个批注段落！"));//设置批注内容
        comment.setAuthor("李志伟");//设置批注作者
        comment.setVisible(true);//设置批注默认显示
        HSSFCell cell = sheet.createRow(2).createCell(1);
        cell.setCellValue("测试");
        cell.setCellComment(comment);//把批注赋值给单元格
        /**
         * 创建页眉和页脚
         * 也可以使用Office自带的标签定义，你可以通过HSSFHeader或HSSFFooter访问到它们，都是静态属性，列表如下：
         * HSSFHeader.tab                  &A	表名
         * HSSFHeader.file                 &F	文件名
         * HSSFHeader.startBold            &B	粗体开始
         * HSSFHeader.endBold              &B	粗体结束
         * HSSFHeader.startUnderline       &U	下划线开始
         * HSSFHeader.endUnderline         &U	下划线结束
         * HSSFHeader.startDoubleUnderline &E	双下划线开始
         * HSSFHeader.endDoubleUnderline   &E	双下划线结束
         * HSSFHeader.time                 &T	时间
         * HSSFHeader.date                 &D	日期
         * HSSFHeader.numPages             &N	总页面数
         * HSSFHeader.page                 &P	当前页号
         */
        HSSFHeader header =sheet.getHeader();//得到页眉
        header.setLeft(HSSFHeader.tab());
        header.setRight("页眉右边");
        header.setCenter("页眉中间");
        HSSFFooter footer =sheet.getFooter();//得到页脚
        footer.setLeft("页脚左边");
        footer.setRight("页脚右边");
        footer.setCenter("页脚中间");




        //创建第一行
        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("id");
        row.createCell(1).setCellValue("订单号");
        row.createCell(2).setCellValue("下单时间");
        row.createCell(3).setCellValue("个数");
        row.createCell(4).setCellValue("单价");
        row.createCell(5).setCellValue("订单金额");
        row.setHeightInPoints(30); // 设置行的高度

        HSSFRow row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue("1");
        row1.createCell(1).setCellValue("NO00001");

        // 日期格式化
        HSSFCellStyle cellStyle2 = workbook.createCellStyle();
        HSSFCreationHelper creationHelper = workbook.getCreationHelper();
        cellStyle2.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));
        sheet.setColumnWidth(2, 20 * 256); // 设置列的宽度

        HSSFCell cell2 = row1.createCell(2);
        cell2.setCellStyle(cellStyle2);
        cell2.setCellValue(new Date());

        row1.createCell(3).setCellValue(2);


        // 保留两位小数
        HSSFCellStyle cellStyle3 = workbook.createCellStyle();
        cellStyle3.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
        HSSFCell cell4 = row1.createCell(4);
        cell4.setCellStyle(cellStyle3);
        cell4.setCellValue(29.5);


        // 货币格式化
        HSSFCellStyle cellStyle4 = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setFontName("华文行楷");
        font.setFontHeightInPoints((short) 15);
        font.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
        cellStyle4.setFont(font);

        HSSFCell cell5 = row1.createCell(5);
        cell5.setCellFormula("D2*E2");  // 设置计算公式

        // 获取计算公式的值
        HSSFFormulaEvaluator e = new HSSFFormulaEvaluator(workbook);
        cell5 = e.evaluateInCell(cell5);
        System.out.println(cell5.getNumericCellValue());


        workbook.setActiveSheet(0);
        workbook.write(outputStream);
        outputStream.close();
    }

    /**
     * 示例2：读取Excel，解析数据
     */
    public static void readExcel() throws IOException {
        FileSystemView fsv = FileSystemView.getFileSystemView();
        String desktop = fsv.getHomeDirectory().getPath();
        String filePath = desktop + "/template.xls";

        FileInputStream fileInputStream = new FileInputStream(filePath);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        POIFSFileSystem fileSystem = new POIFSFileSystem(bufferedInputStream);
        HSSFWorkbook workbook = new HSSFWorkbook(fileSystem);
        HSSFSheet sheet = workbook.getSheet("Sheet1");

        int lastRowIndex = sheet.getLastRowNum();
        System.out.println(lastRowIndex);
        for (int i = 0; i <= lastRowIndex; i++) {
            HSSFRow row = sheet.getRow(i);
            if (row == null) {
                break;
            }

            short lastCellNum = row.getLastCellNum();
            for (int j = 0; j < lastCellNum; j++) {
                String cellValue = row.getCell(j).getStringCellValue();
                System.out.println(cellValue);
            }
        }


        bufferedInputStream.close();
    }


    /**
     * Java Web 中导出和导入Excel 示例
     */
    @SuppressWarnings("resource")
    @RequestMapping("/export")
    public void exportExcel(HttpServletResponse response) throws Exception {

        String[] tableHeaders = {"id", "姓名", "年龄"};

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Sheet1");
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        Font font = workbook.createFont();
        font.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
        font.setBold(true);
        cellStyle.setFont(font);

        // 将第一行的三个单元格给合并
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
        HSSFRow row = sheet.createRow(0);
        HSSFCell beginCell = row.createCell(0);
        beginCell.setCellValue("通讯录");
        beginCell.setCellStyle(cellStyle);

        row = sheet.createRow(1);
        // 创建表头
        for (int i = 0; i < tableHeaders.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(tableHeaders[i]);
            cell.setCellStyle(cellStyle);
        }

        List<User> users = new ArrayList<>();
        users.add(new User(1L, "张三", 20));
        users.add(new User(2L, "李四", 21));
        users.add(new User(3L, "王五", 22));

        for (int i = 0; i < users.size(); i++) {
            row = sheet.createRow(i + 2);

            User user = users.get(i);
            row.createCell(0).setCellValue(user.getId());
            row.createCell(1).setCellValue(user.getName());
            row.createCell(2).setCellValue(user.getAge());
        }

        OutputStream outputStream = response.getOutputStream();
        response.reset();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=template.xls");

        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

}
