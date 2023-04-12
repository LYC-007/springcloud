package com.xufei.service;




import com.xufei.constant.Customer;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public interface CustomerService {
    int addCustomer(Customer customer) throws IOException;

    Customer findCustomer(Customer customer);

    List<Customer> findCustomerList(Customer customer);

    List<Map> findCustomerListMap(Customer customer);

    int batchInsert(List<Customer> list) throws IOException;

    List<Customer> findList();
}
