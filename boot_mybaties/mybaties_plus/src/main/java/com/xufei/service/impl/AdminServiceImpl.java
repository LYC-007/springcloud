package com.xufei.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xufei.constant.Admin;
import com.xufei.enums.SexEnum;
import com.xufei.mapper.AdminMapper;
import com.xufei.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @Author: XuFei
 * @Date: 2022/8/11 16:10
 */

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    @Autowired
    AdminMapper adminMapper;

    public ArrayList<Admin> findAll(){
       return (ArrayList<Admin>) adminMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public void insertOne() {
        Admin admin = new Admin();
        admin.setPassword("11111");
        admin.setUserName("Mary");
        admin.setSex(SexEnum.WOMEN);
        adminMapper.insert(admin);
    }

    @Override
    public void updateOne() {

        Admin admin = adminMapper.selectById(9);
        admin.setPassword("1899");
        adminMapper.updateById(admin);
        adminMapper.deleteById(7);
    }
}
