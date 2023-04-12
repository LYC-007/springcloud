package com.xufei.excel.easyexcel.service;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xufei.excel.easyexcel.domain.DictEeVo;
import com.xufei.excel.easyexcel.listener.DictListener;
import com.xufei.excel.easyexcel.domain.Dict;
import com.xufei.excel.easyexcel.mapper.DictMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> {
    /**
     * 读取数据库中数据到 Excel表格中
     */
    public void simpleWriteTestXlsx(){
        ArrayList<DictEeVo> excelStudentDTOList = new ArrayList<>();
        String fileName = "d:/excel200921/simpleWrite.xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel
                .write(fileName, DictEeVo.class)  //声明你要写出的文件地址和对应的bean
                .excelType(ExcelTypeEnum.XLSX) //选择你要写的Excel文件的类型（有两种类型分别问：xlsx【建议使用】,xls）
                .sheet("模板")  //命名
                .doWrite(excelStudentDTOList);//你需要传入一个List集合---->把内存中的list集合写道Excel表格中
    }

    /**
     * 插入数据库测试
     */
    public void importData(MultipartFile multipartFile){//前端通过文件的方式传入Excel表格到  MultipartFile
        try {
            //他是通过监听器一行一行数据读到内存中，每一行数据被封装到一个DictEeVo对象里面,所以我们需要一个监听器，
            //由于数据库的表结构是一个Dict,所以我们需要在监听器里面把DictEeVo对象复制到Dict里面去，在进行数据库的插入操作
            //在进行数据库插入时我们需要一个DictMapper，所以传一个baseMapper

            EasyExcel.read(multipartFile.getInputStream(), DictEeVo.class,new DictListener(baseMapper)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
