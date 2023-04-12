package com.room1.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.room1.mapper.LoginMapper;
import com.room1.po.Staff;
import com.room1.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 5)
@Service("loginservice")
public class LoginServiceImpl extends ServiceImpl<LoginMapper, Staff> implements LoginService {
    @Autowired
    LoginMapper loginmapper;

    public String getpwdbyname(String name) {
        Staff s = loginmapper.getpwdbyname(name);
        if (s != null)
            return s.getPassword();
        else
            return null;
    }

    public Long getUidbyname(String name) {
        Staff s = loginmapper.getpwdbyname(name);
        if (s != null)
            return (long) s.getStaff_id();
        else
            return null;
    }

    public String getnamebyid(long id) {
        Staff s = loginmapper.getnamebyid(id);
        if (s != null)
            return s.getUsername();
        else
            return null;
    }

    public ArrayList<Staff> getUserList() {

        return (ArrayList<Staff>) baseMapper.selectList(new QueryWrapper<>());
    }


}
