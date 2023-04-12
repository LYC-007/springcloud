package com.xufei.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xufei.framework.util.ListUtils;
import com.xufei.constant.Customer;
import com.xufei.constant.Participle;
import com.xufei.mapper.CustomerMapper;
import com.xufei.mapper.ParticipleMapper;
import com.xufei.service.CustomerService;
import com.xufei.framework.util.MyIKAnalyzer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper,Customer> implements CustomerService {
    @Resource
    private CustomerMapper customerMapper;
    @Resource
    private ParticipleMapper participleMapper;

    @Override
    public int addCustomer(Customer customer) throws IOException {
        List<Participle> pc=new ArrayList<>();
        List<String> participle = MyIKAnalyzer.participle(customer.getAddress());
        int i = customerMapper.addCustomer(customer);
        for(String str:participle){
            Participle participle1 = new Participle();
            participle1.setId(customer.getId());
            participle1.setText(str);
            pc.add(participle1);
        }
        participleMapper.batchInsert(pc);
        return i;
    }

    @Override
    public Customer findCustomer(Customer customer) {
        return customerMapper.findCustomer(customer);
    }

    @Override
    public List<Customer> findCustomerList(Customer customer) {
        return customerMapper.findCustomerList(customer);
    }

    @Override
    public List<Map> findCustomerListMap(Customer customer) {
        return customerMapper.findCustomerListMap(customer);
    }

    @Override
    public int batchInsert(List<Customer> list) throws IOException {
        List<Customer> customers = ListUtils.deepCopy(list);

        int i = customerMapper.batchInsert(list);

        List<Participle> participleList=new ArrayList<>();
        for(int ii=0;ii<list.size();ii++ ){
            if(null!=customers.get(ii).getAddress()){
                List<String> participle = MyIKAnalyzer.participle(customers.get(ii).getAddress());
                System.out.println("分词："+participle);
                for(String str:participle){
                    Participle participle1 = new Participle();
                    participle1.setId(list.get(ii).getId());
                    participle1.setText(str);
                    participleList.add(participle1);
                }
            }
        }
        participleMapper.batchInsert(participleList);
        return i;
    }

    @Override
    public List<Customer> findList() {
        return customerMapper.selectList(new QueryWrapper<>());
    }
}
