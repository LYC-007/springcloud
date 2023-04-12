package com.datasource.service.service1;

import com.datasource.aspectj.annotaion.ChangeDataSource;
import com.datasource.aspectj.enums.DataSourceName;
import com.datasource.dao.StudentsMapper;
import com.datasource.mapper.UserMapper;
import com.datasource.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceV1 {
    @Autowired
    private UserMapper userDAO;
    /**
     * 方法上指定数据源为SLAVE
     */
    @ChangeDataSource(DataSourceName.MASTER)
    public void save_1() {
        userDAO.save(UserUtil.createUser());
    }

    /**
     * 方法上未指定数据源，则看类上指定的是什么，没有，再看包
     */
    public void save_2() {
        userDAO.save(UserUtil.createUser());
    }
}
