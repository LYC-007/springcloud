package com.room1.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.room1.po.Staff;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginMapper extends BaseMapper<Staff> {
	Staff getpwdbyname(String name);
	Staff getnamebyid(long id);
}
