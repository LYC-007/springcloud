package com.datasource.service.service2;


import com.datasource.aspectj.annotaion.ChangeDataSource;
import com.datasource.aspectj.enums.DataSourceName;
import com.datasource.dao.StudentsMapper;
import com.datasource.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@ChangeDataSource(DataSourceName.SLAVE)
public class UserServiceV2 {
    @Autowired
    private StudentsMapper studentsMapper;
    /**
     * 方法上未指定数据源，类上指定
     */
    public void save_1() {
        studentsMapper.save(UserUtil.createUser());
    }

}
