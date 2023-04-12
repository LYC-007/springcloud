package com.datasource.dao;

import com.datasource.aspectj.annotaion.ChangeDataSource;
import com.datasource.aspectj.enums.DataSourceName;
import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * SLAVE
 */
@Repository
@ChangeDataSource(DataSourceName.SLAVE)
public interface StudentsMapper {
    @Insert("insert into students(name,age,sex) values(#{name},#{age},#{sex})")
    void save(Map<String, Object> user);

}