package com.xufei.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xufei.constant.Customer;
import org.apache.ibatis.annotations.Param;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Resource
public interface CustomerMapper extends BaseMapper<Customer> {
    int addCustomer(@Param("customer") Customer customer);

    int batchInsert(@Param("cuList") List<Customer> customers);

    Customer findCustomer(@Param("customer") Customer customer);

    List<Customer> findCustomerList(Customer customer);

    List<Map> findCustomerListMap(Customer customer);
}
