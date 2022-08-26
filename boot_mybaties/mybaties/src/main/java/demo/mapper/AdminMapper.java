package demo.mapper;

import demo.domain.Admin;

import java.util.ArrayList;

/**
 * @Author: XuFei
 * @Date: 2022/8/10 12:06
 */
@Mapper   //或者在主启动类添加@MapperScan定义包扫描的范围
public interface AdminMapper {
    ArrayList<Admin> findAll();


    /**
     * 注解方式，不编写sql映射文件
     * @return
     */
    @Select("Select * from `admin`")
    ArrayList<Admin> findAll01();



    void insertOneAdmin(@Param("admin") Admin admin);

    @Insert("insert into `admin`(`username`,`password`) value (#{userName},#{password})")
    @Options(useGeneratedKeys = true,keyProperty = "id") //返回自增的主键
    void insertOneAdmin01(Admin admin);



}
