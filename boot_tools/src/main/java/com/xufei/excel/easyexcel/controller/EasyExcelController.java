package com.xufei.excel.easyexcel.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.xufei.excel.easyexcel.domain.UserExcel;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/xlsx")
public class EasyExcelController {

    @RequestMapping("/toExcelPage")
    public String todownloadPage() {
        return "excelPage";
    }

    /**
     * 下载Excel
     */
    @RequestMapping(value = "/downloadExcel")
    public void downloadExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 设置响应头
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 设置防止中文名乱码
        String filename = URLEncoder.encode("员工信息", "utf-8");
        // 文件下载方式(附件下载还是在当前浏览器打开)
        response.setHeader("Content-disposition", "attachment;filename=" +
                filename + ".xlsx");
        // 构建写入到excel文件的数据
        List<UserExcel> userExcels = new ArrayList<>();
        UserExcel userExce1 = new UserExcel(1001, "张三", "男", 1333.33, new Date());
        UserExcel userExce2 = new UserExcel(1002, "李四", "男", 1356.83, new Date());
        UserExcel userExce3 = new UserExcel(1003, "王五", "男", 1883.66, new Date());
        UserExcel userExce4 = new UserExcel(1004, "赵六", "男", 1393.39, new Date());
        userExcels.add(userExce1);
        userExcels.add(userExce2);
        userExcels.add(userExce3);
        userExcels.add(userExce4);
        // 写入数据到excel
        EasyExcel.write(response.getOutputStream(), UserExcel.class)
                .sheet("用户信息")
                .doWrite(userExcels);
    }

    /**
     * Excel文件上传
     */
    @RequestMapping("/uploadExcel")
    public void uploadExcel(HttpServletRequest request) throws Exception {
        ServletFileUpload fileUpload = new ServletFileUpload(new DiskFileItemFactory());
        // 设置单个文件大小为3M 2的10次幂=1024
        fileUpload.setFileSizeMax((long) (3 * Math.pow(2, 20)));
        // 总文件大小为30M
        fileUpload.setSizeMax((long) (30 * Math.pow(2, 20)));
        List<FileItem> list = fileUpload.parseRequest(request);
        for (FileItem fileItem : list) {
            // 判断是否为附件
            if (!fileItem.isFormField()) {
                // 是附件
                InputStream inputStream = fileItem.getInputStream();
                EasyExcel.read(inputStream, UserExcel.class,
                        new AnalysisEventListener<UserExcel>() {
                            // 每解析一行数据,该方法会被调用一次
                            @Override
                            public void invoke(UserExcel data, AnalysisContext analysisContext) {
                                System.out.println("解析数据为:" + data.toString());
                            }

                            // 全部解析完成被调用
                            @Override
                            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                                System.out.println("解析完成...");
                                // 可以将解析的数据保存到数据库
                            }
                        }).sheet().doRead();
            }
        }
    }

}
