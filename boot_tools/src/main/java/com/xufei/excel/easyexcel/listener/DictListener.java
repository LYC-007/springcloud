package com.xufei.excel.easyexcel.listener;


import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.xufei.excel.easyexcel.domain.Dict;
import com.xufei.excel.easyexcel.domain.DictEeVo;
import com.xufei.excel.easyexcel.mapper.DictMapper;
import org.springframework.beans.BeanUtils;

public class DictListener extends AnalysisEventListener<DictEeVo> {

    private final DictMapper dictMapper;

    public DictListener(DictMapper dictMapper) {
        this.dictMapper = dictMapper;
    }

    @Override
    public void invoke(DictEeVo dictEeVo, AnalysisContext analysisContext) {
        Dict dict = new Dict();
        BeanUtils.copyProperties(dictEeVo, dict);

        dictMapper.insert(dict);//每读取一行数据就调一次invoke方法
    }

    // 全部解析完成被调用
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("解析完成...");
        // 可以将解析的数据保存到数据库
    }
}
