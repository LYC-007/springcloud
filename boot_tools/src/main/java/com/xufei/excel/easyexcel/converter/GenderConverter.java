package com.xufei.excel.easyexcel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.util.Objects;

/**
 * 类描述：性别字段的数据转换器
 *
 * 在实际应用场景中, 我们系统db存储的数据可以是枚举, 在界面或导出到Excel文件需要展示为对于的枚举值形式.
 * 比如: 性别, 状态等. EasyExcel提供了转换器接口Converter供我们使用, 我们只需要自定义转换器实现接口, 并将自定义转换器类型传入要转换的属性字段中. 以下面的性别gender字段为例
 */
public class GenderConverter implements Converter<Integer> {

    private static final String MALE = "男";
    private static final String FEMALE = "女";
    private static final String NONE = "未知";

    // Java数据类型 integer
    @Override
    public Class<Integer> supportJavaTypeKey() {
        return Integer.class;
    }

    // Excel文件中单元格的数据类型  string
    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    // 读取Excel文件时将string转换为integer
    @Override
    public Integer convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        String value = cellData.getStringValue();
        if (Objects.equals(FEMALE, value)) {
            return 0; // 0-女
        } else if (Objects.equals(MALE, value)) {
            return 1; // 1-男
        }
        return 2; // 2-未知
    }

    // 写入Excel文件时将integer转换为string
    @Override
    public WriteCellData<Integer> convertToExcelData(Integer value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        if (value == 1) {
            return new WriteCellData<>(MALE);
        } else if (value == 0) {
            return new WriteCellData<>(FEMALE);
        }
        return new WriteCellData<>(NONE); // 不男不女
    }
}
