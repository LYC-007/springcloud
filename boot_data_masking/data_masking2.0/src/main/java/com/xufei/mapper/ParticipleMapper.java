package com.xufei.mapper;



import com.xufei.constant.Participle;
import org.apache.ibatis.annotations.Param;

import javax.annotation.Resource;
import java.util.List;

@Resource
public interface ParticipleMapper {

    Long addParticiple(Participle participle);


    int batchInsert(@Param("cuList") List<Participle> participle);
}
