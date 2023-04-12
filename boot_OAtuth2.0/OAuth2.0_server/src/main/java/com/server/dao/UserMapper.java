package com.server.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.server.model.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 *  UserMapper
 */
@Mapper
public interface UserMapper extends BaseMapper<UserInfo> {

    /**
     * 根据用户名查询用户
     */
    @Select("select username,password,enabled from t_user where username = #{username} and enabled = 1")
    UserInfo queryUserByUserName(@Param("username") String username);
}
