package com.xufei.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xufei.security.entity.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuMapper extends BaseMapper<Menu> {


    List<String> selectPermsByUserId(Long userid);


}
