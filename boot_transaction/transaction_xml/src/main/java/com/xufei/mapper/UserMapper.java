package com.xufei.mapper;

import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface UserMapper {
    @Insert("insert into students(name,age,sex) values(#{name},#{age},#{sex})")
    void save(Map<String, Object> map);
}
