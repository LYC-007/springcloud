package com.xufei.excel.poi;

import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;

import javax.swing.filechooser.FileSystemView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * 在4.1之前采用的是枚举类型，setColor(HSSFColor.RED.index);
 * 在4.1之后采用的是枚举类型，setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
 */
public class ApachePoi {
    /**
     * 示例一：在桌面上生成一个Excel文件
     */
    public static void main(String[] args) throws IOException {
        FileSystemView fsv = FileSystemView.getFileSystemView();// 获取桌面路径
        String desktop = fsv.getHomeDirectory().getPath();
        String filePath = desktop + "/testExcel.xls";
        HSSFWorkbook workbook = new HSSFWorkbook();//创建Excel文件(Workbook)
        workbook.setActiveSheet(2);//设置默认工作表
        HSSFSheet sheet = workbook.createSheet("Sheet1");//创建工作表(Sheet)
        sheet.setDisplayGridlines(true);//隐藏Excel网格线,默认值为true
        sheet.setGridsPrinted(true);//打印时显示网格线,默认值为false

        //创建文档摘要信息
        test(workbook);
        //创建批注
        test1(sheet);
        //创建页眉和页脚
        test2(sheet);
        //设置单元格格式
        test3(sheet, workbook);
        //合并单元格
        test4(sheet);
        //设置单元格格式
        test5(sheet, workbook);
        //设置单元格边框格式和颜色
        test6(sheet, workbook);
        //设置单元格字体的格式和颜色
        test7(sheet, workbook);
        //背景和纹理
        test8(sheet, workbook);
        test8_1(sheet, workbook);
        //设置宽度和高度
        test9(sheet);
        //判断单元格是否为日期
        test10(sheet, workbook);
        //设置函数
        test11(sheet, workbook);
        //设置单元格只允许读 ，修改需要输入密码
        test12(sheet, workbook);
        //锁定一列数据的类型，比如:日期
        test13(workbook);

        //................其他相关测试查看:https://www.cnblogs.com/LiZhiW/p/4313789.html#_label0

        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        workbook.write(fileOutputStream);//保存Excel文件
        fileOutputStream.close();//关闭文件流
        System.out.println("OK!");
    }

    /**
     * 遍历Sheet
     */
    public static void test15() throws IOException {

        String filePath = "d:\\users\\lizw\\桌面\\POI\\sample.xls";
        FileInputStream stream = new FileInputStream(filePath);
        HSSFWorkbook workbook = new HSSFWorkbook(stream);//读取现有的Excel
        HSSFSheet sheet = workbook.getSheet("Test0");//得到指定名称的Sheet
        for (Row row : sheet) {
            for (Cell cell : row) {
                System.out.print(cell + "\t");
            }
            System.out.println();
        }

    }

    public static void test14(HSSFWorkbook workbook) {
        HSSFSheet sheet = workbook.createSheet("生成下拉式菜单");
        /**
         * CellRangeAddressList类表示一个区域，构造函数中的四个参数分别表示起始行序号，终止行序号，起始列序号，终止列序号。而65535是一个Sheet的最大行数。
         */
        CellRangeAddressList regions = new CellRangeAddressList(0, 65535, 0, 0);
        DVConstraint constraint = DVConstraint.createExplicitListConstraint(new String[]{"C++", "Java", "C#"});
        HSSFDataValidation dataValidate = new HSSFDataValidation(regions, constraint);
        sheet.addValidationData(dataValidate);

    }

    public static void test13(HSSFWorkbook workbook) {
        /**
         * CellRangeAddressList类表示一个区域，构造函数中的四个参数分别表示起始行序号，终止行序号，起始列序号，终止列序号。而65535是一个Sheet的最大行数。
         */
        CellRangeAddressList regions = new CellRangeAddressList(1, 65535, 0, 0);//选定一个区域
        DVConstraint constraint = DVConstraint.createDateConstraint(DVConstraint.OperatorType.BETWEEN, "1993-01-01", "2014-12-31", "yyyy-MM-dd");
        HSSFSheet sheet = workbook.createSheet("锁定单元格的类型");
        HSSFDataValidation dataValidate = new HSSFDataValidation(regions, constraint);
        dataValidate.createErrorBox("错误", "你必须输入一个时间，格式为:2014-12-31");
        sheet.addValidationData(dataValidate);
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("日期列");
    }

    public static void test12(HSSFSheet sheet, HSSFWorkbook workbook) {
        HSSFRow row = sheet.createRow(8);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("已锁定");
        HSSFCellStyle locked = workbook.createCellStyle();
        locked.setLocked(true);//设置锁定
        cell.setCellStyle(locked);
        cell = row.createCell(1);
        cell.setCellValue("未锁定");
        HSSFCellStyle unlocked = workbook.createCellStyle();
        unlocked.setLocked(false);//设置不锁定
        cell.setCellStyle(unlocked);
        sheet.protectSheet("password");//设置保护密码
    }

    public static void test11(HSSFSheet sheet, HSSFWorkbook workbook) {
        /**
         * 基本计算
         */
        HSSFRow row = sheet.createRow(7);
        HSSFCell cell = row.createCell(0);
        cell.setCellFormula("2+3*4");//直接设置公式
        cell = row.createCell(1);
        cell.setCellValue(10);
        cell = row.createCell(2);
        cell.setCellFormula("A8*B8");//设置公式

        /**
         * SUM函数
         */
        row.createCell(3).setCellValue(4);
        row.createCell(4).setCellValue(5);
        row = sheet.createRow(8);
        row.createCell(0).setCellFormula("sum(A8,C8)");//等价于"A1+C1"
        row.createCell(1).setCellFormula("sum(B8:D8)");//等价于"B1+C1+D1"


        /**
         * 日期函数
         */
        HSSFSheet sheets = workbook.createSheet("日期函数");// 创建工作表(Sheet)
        HSSFCellStyle style = workbook.createCellStyle();
        style.setDataFormat(workbook.createDataFormat().getFormat("yyyy-mm-dd"));
        HSSFRow rows = sheets.createRow(0);
        Calendar date = Calendar.getInstance();//日历对象
        HSSFCell cells = rows.createCell(0);
        date.set(2011, 2, 7);
        cells.setCellValue(date.getTime());
        cells.setCellStyle(style);//第一个单元格开始时间设置完成
        cells = rows.createCell(1);
        date.set(2014, 4, 25);
        cells.setCellValue(date.getTime());
        cells.setCellStyle(style);//第一个单元格结束时间设置完成
        cells = rows.createCell(3);
        cells.setCellFormula("CONCATENATE(DATEDIF(A1,B1,\"y\"),\"年\")");
        cells = rows.createCell(4);
        cells.setCellFormula("CONCATENATE(DATEDIF(A1,B1,\"m\"),\"月\")");
        cells = rows.createCell(5);
        cells.setCellFormula("CONCATENATE(DATEDIF(A1,B1,\"d\"),\"日\")");

        /**
         * 字符串相关的函数
         * 以上代码中的公式说明：
         *         UPPER(String)：将文本转换成大写形式。
         *         PROPER(String)：将文字串的首字母及任何非字母字符之后的首字母转换成大写。将其余的字母转换成小写。
         *         更多Excel的字符串函数可参考：http://tonyqus.sinaapp.com/archives/289
         */
        HSSFSheet sheetStr = workbook.createSheet("字符串函数测试");// 创建工作表(Sheet)
        HSSFRow rowStr = sheetStr.createRow(0);
        rowStr.createCell(0).setCellValue("abcdefg");
        rowStr.createCell(1).setCellValue("aa bb cc dd ee fF GG");
        rowStr.createCell(3).setCellFormula("UPPER(A1)");
        rowStr.createCell(4).setCellFormula("PROPER(B1)");


    }

    /**
     * 判断单元格是否为日期
     * 判断单元格是否为日期类型，使用DateUtil.isCellDateFormatted(cell)方法
     */
    public static void test10(HSSFSheet sheet, HSSFWorkbook workbook) {
        HSSFRow row = sheet.createRow(6);
        HSSFCell cell = row.createCell(0);
        System.out.println(DateUtil.isCellDateFormatted(cell));//输出：false
        HSSFCellStyle style = workbook.createCellStyle();
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
        cell.setCellStyle(style);//设置日期样式
        System.out.println(DateUtil.isCellDateFormatted(cell));//输出：true
        cell.setCellValue(new Date());//设置日期数据
    }

    public static void test9(HSSFSheet sheet) {
        HSSFRow row = sheet.createRow(5);
        HSSFCell cell = row.createCell(7);
        cell.setCellValue("123456789012345678901234567890");
        sheet.setColumnWidth(7, 31 * 256);//设置第一列的宽度是31个字符宽度
        row.setHeightInPoints(50);//设置行的高度是50个点
        /**
         * 这里你会发现一个有趣的现象，setColumnWidth的第二个参数要乘以256，这是怎么回事呢？其实，这个参数的单位是1/256个字符宽度，也就是说，这里是把B列的宽度设置为了31个字符。
         *     设置行高使用HSSFRow对象的setHeight和setHeightInPoints方法，这两个方法的区别在于setHeightInPoints的单位是点，而setHeight的单位是1/20个点，所以setHeight的值永远是setHeightInPoints的20倍。
         *     你也可以使用HSSFSheet.setDefaultColumnWidth、HSSFSheet.setDefaultRowHeight和HSSFSheet.setDefaultRowHeightInPoints方法设置默认的列宽或行高。
         */

    }

    /**
     * 设置背景色颜色+前景色颜色+设置前景色平铺方式
     * <p>
     * 背景色只有在和前景色组合形成图案时有效,设置前景色必须同时设置填充样式，样式为无缝铺满时即为常规需求下的背景色效果
     * 设置图案时，前景色的设置要在背景色之前，否则背景色的设置将失效并使用黑色
     */
    public static void test8(HSSFSheet sheet, HSSFWorkbook workbook) {
        // 设置背景颜色
        CellStyle backStyle = workbook.createCellStyle();    // 创建单元格样式对象
        backStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.RED.getIndex());  // 设置前景色
        backStyle.setFillBackgroundColor(IndexedColors.GREEN.getIndex());  // 设置背景色
        backStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);  // 前景色平铺方式（SOLID_FOREGROUND：无缝铺满；SQUARES：网格；BRICKS：砖块）
        // 应用到单元格
        Row backRow = sheet.createRow(4);                 // 创建行对象
        Cell backCell = backRow.createCell(0);            // 创建单元格
        backCell.setCellStyle(backStyle);                 // 将对齐应用到单元格
        backCell.setCellValue("测试图案背景");            // 测试

    }

    /**
     * 前景色颜色+设置前景色平铺方式
     */
    public static void test8_1(HSSFSheet sheet, HSSFWorkbook workbook) {
        // 设置背景颜色
        CellStyle backStyle = workbook.createCellStyle();    // 创建单元格样式对象
        backStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.RED.getIndex());  // 设置前景色
        backStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);  // 前景色平铺方式（SOLID_FOREGROUND：无缝铺满；SQUARES：网格；BRICKS：砖块）
        // 应用到单元格
        Row backRow = sheet.createRow(4);                 // 创建行对象
        Cell backCell = backRow.createCell(1);            // 创建单元格
        backCell.setCellStyle(backStyle);                 // 将对齐应用到单元格
        backCell.setCellValue("测试背景色");              // 测试
    }

    public static void test7(HSSFSheet sheet, HSSFWorkbook workbook) {
        // 设置字体属性
        Font font = workbook.createFont();        // 创建字体对象
        font.setItalic(true);                     // 设置字体倾斜
        font.setColor(Font.COLOR_RED);            // 设置字体颜色
        font.setFontHeightInPoints((short) 22);    // 设置字体大小
        font.setFontName("宋体");                  // 设置字体名称
        font.setUnderline(Font.U_DOUBLE);         // 设置下划线(Font.U_SINGLE：单线；Font.U_DOUBLE：双线)
        font.setBold(true);                       // 设置字体加粗
        font.setStrikeout(true);                  // 设置删除线

        HSSFRow row = sheet.createRow(4);
        HSSFCell cell = row.createCell(0);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        cell.setCellStyle(style);
        cell.setCellValue("字体格式和颜色");
    }

    public static void test6(HSSFSheet sheet, HSSFWorkbook workbook) {
        HSSFRow row = sheet.createRow(3);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("设置边框");
        HSSFCellStyle borderStyle = workbook.createCellStyle();
        borderStyle.setBorderBottom(BorderStyle.THICK);        // 设置下边框（DOUBLE：双线；THIN：细线）
        borderStyle.setBorderTop(BorderStyle.DASHED);          // 设置上边框（MEDIUM：中等线；DASHED：虚线）
        borderStyle.setBorderLeft(BorderStyle.DOUBLE);         // 设置左边框（HAIR：圆点线；THICK：粗线）
        borderStyle.setBorderRight(BorderStyle.THIN);          // 设置右边框（旧版考虑：CellStyle.BORDER_THICK）
        borderStyle.setBottomBorderColor(HSSFColor.HSSFColorPredefined.RED.getIndex());    // 设置下边框颜色（通过HSSFColor的枚举类型获取索引）
        borderStyle.setTopBorderColor(HSSFColor.HSSFColorPredefined.GREEN.getIndex());     // 设置上边框颜色（通过XSSFColor的构造方法获取索引）
        borderStyle.setLeftBorderColor(HSSFColor.HSSFColorPredefined.GREEN.getIndex());//左边框颜色
        borderStyle.setRightBorderColor(HSSFColor.HSSFColorPredefined.PINK.getIndex());//右边框颜色
        // 应用到单元格
        cell.setCellStyle(borderStyle);            // 将边框应用到单元格
        cell.setCellValue("测试边框格式和颜色");
    }

    public static void test5(HSSFSheet sheet, HSSFWorkbook workbook) {
        HSSFRow row = sheet.createRow(2);
        HSSFCell cell = row.createCell(0);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);//设置水平对齐（CENTER：居中；LEFT：左；RIGHT：右；JUSTIFY:两端对齐；CENTER_SELECTION:跨列举中；FILL:填充。）
        style.setVerticalAlignment(VerticalAlignment.CENTER);// 设置垂直对齐（CENTER：居中；TOP：上；BOTTOM：下；JUSTIFY:两端对齐）
        style.setWrapText(true);//自动换行
        style.setIndention((short) 5);//缩进
        style.setRotation((short) 60);//文本旋转，这里的取值是从-90到90，而不是0-180度。
        cell.setCellStyle(style);
        cell.setCellValue("测试单元格格式");
    }

    public static void test4(HSSFSheet sheet) {
        HSSFRow row = sheet.createRow(1);//新 创建一行
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("合并行");
        /**
         * firstRow        区域中第一个单元格的行号
         * lastRow         区域中最后一个单元格的行号
         * firstCol        区域中第一个单元格的列号
         * lastCol         区域中最后一个单元格的列号
         */
        CellRangeAddress region = new CellRangeAddress(1, 1, 0, 5);
        //合并行
        sheet.addMergedRegion(region);
        cell = row.createCell(6);
        cell.setCellValue("合并列");
        region = new CellRangeAddress(0, 5, 6, 6);
        //合并列
        sheet.addMergedRegion(region);

    }

    /**
     * HSSFDataFormat.getFormat和HSSFDataFormat.getBuiltinFormat的区别：
     * 当使用Excel内嵌的（或者说预定义）的格式时，直接用HSSFDataFormat.getBuiltinFormat静态方法即可。当使用自己定义的格式时，必须先调用HSSFWorkbook.createDataFormat()，
     * 因为这时在底层会先找有没有匹配的内嵌FormatRecord，如果没有就会新建一个FormatRecord，所以必须先调用这个方法，然后你就可以用获得的HSSFDataFormat实例的getFormat方法了，
     * 当然相对而言这种方式比较麻烦，所以内嵌格式还是用HSSFDataFormat.getBuiltinFormat静态方法更加直接一些。
     */
    public static void test3(HSSFSheet sheet, HSSFWorkbook workbook) {
        HSSFRow row = sheet.createRow(0);
        //设置日期格式--使用Excel内嵌的格式
        HSSFCell cell = row.createCell(0);
        cell.setCellValue(new Date());
        HSSFCellStyle style = workbook.createCellStyle();
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));
        cell.setCellStyle(style);
        //设置保留2位小数--使用Excel内嵌的格式
        cell = row.createCell(1);
        cell.setCellValue(12.3456789);
        style = workbook.createCellStyle();
        style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
        cell.setCellStyle(style);
        //设置货币格式--使用自定义的格式
        cell = row.createCell(2);
        cell.setCellValue(12345.6789);
        style = workbook.createCellStyle();
        style.setDataFormat(workbook.createDataFormat().getFormat("￥#,##0"));
        cell.setCellStyle(style);
        //设置百分比格式--使用自定义的格式
        cell = row.createCell(3);
        cell.setCellValue(0.123456789);
        style = workbook.createCellStyle();
        style.setDataFormat(workbook.createDataFormat().getFormat("0.00%"));
        cell.setCellStyle(style);
        //设置中文大写格式--使用自定义的格式
        cell = row.createCell(4);
        cell.setCellValue(12345);
        style = workbook.createCellStyle();
        style.setDataFormat(workbook.createDataFormat().getFormat("[DbNum2][$-804]0"));
        cell.setCellStyle(style);
        //设置科学计数法格式--使用自定义的格式
        cell = row.createCell(5);
        cell.setCellValue(12345);
        style = workbook.createCellStyle();
        style.setDataFormat(workbook.createDataFormat().getFormat("0.00E+00"));
        cell.setCellStyle(style);
    }

    public static void test2(HSSFSheet sheet) {
        HSSFHeader header = sheet.getHeader();//得到页眉
        header.setLeft("页眉左边");
        header.setRight("页眉右边");
        header.setCenter("页眉中间");
        HSSFFooter footer = sheet.getFooter();//得到页脚
        footer.setLeft("页脚左边");
        footer.setRight("页脚右边");
        footer.setCenter(HSSFHeader.page());
        /**
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
    }

    public static void test1(HSSFSheet sheet) {
        HSSFPatriarch patr = sheet.createDrawingPatriarch();
        HSSFClientAnchor anchor = patr.createAnchor(0, 0, 0, 0, 5, 1, 8, 3);//创建批注位置
        HSSFComment comment = patr.createCellComment(anchor);//创建批注
        comment.setString(new HSSFRichTextString("这是一个批注段落！"));//设置批注内容
        comment.setAuthor("李志伟");//设置批注作者
        comment.setVisible(true);//设置批注默认显示
        HSSFCell cell = sheet.createRow(2).createCell(1);
        cell.setCellValue("测试");
        cell.setCellComment(comment);//把批注赋值给单元格
    }


    public static void test(HSSFWorkbook workbook) {
        workbook.createInformationProperties();//创建文档信息
        DocumentSummaryInformation dsi = workbook.getDocumentSummaryInformation();//摘要信息
        dsi.setCategory("类别:Excel文件");//类别
        dsi.setManager("管理者:李志伟");//管理者
        dsi.setCompany("公司:--");//公司
        SummaryInformation si = workbook.getSummaryInformation();//摘要信息
        si.setSubject("主题:--");//主题
        si.setTitle("标题:测试文档");//标题
        si.setAuthor("作者:李志伟");//作者
        si.setComments("备注:POI测试文档");//备注
    }

}
