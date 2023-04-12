package com.xufei.excel.easyexcel.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xufei.excel.easyexcel.domain.Dict;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface DictMapper extends BaseMapper<Dict> {
}

