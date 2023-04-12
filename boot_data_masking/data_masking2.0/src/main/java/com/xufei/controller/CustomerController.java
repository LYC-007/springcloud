package com.xufei.controller;



import com.xufei.constant.Customer;
import com.xufei.service.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
public class CustomerController {

    @Resource
    private CustomerService customerService;

    public static List<String> ADDRESS=new ArrayList<>();
    static {
        ADDRESS.add("山西省平顺县在山西省长治市平顺县");
        ADDRESS.add("河南省郑州市中牟县文明路1号");
        ADDRESS.add("青海省海南藏族自治州共和县X301与政府路交叉口东北方向30米");
        ADDRESS.add("河北省石家庄市灵寿县城西南街33号");
        ADDRESS.add("河北省保定市顺平县神南镇永兴村");
        ADDRESS.add("北京市房山区石梯村南街28号");
        ADDRESS.add("北京市通州区通怀路");
        ADDRESS.add("唐山市迁安市");
        ADDRESS.add("河北省秦皇岛市青龙满族自治县京沈高速北出口方向25公里处");
        ADDRESS.add("葫芦岛市绥中县");
    }
    @RequestMapping("addCustomer")
    public String addCustomer(String phone, String address) throws IOException {
        Customer customer = new Customer();
        customer.setAddress(ADDRESS.get(0));
        customer.setPhone("1888888888888");
        customer.setAge(18);
        int result = customerService.addCustomer(customer);
        return "添加结果: " + result;
    }

    @RequestMapping("batchInset")
    public Integer batchInsert() throws IOException {
        List<Customer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Customer customer = new Customer();
            customer.setAge(i);
            customer.setPhone("1" + i);
            customer.setAddress(ADDRESS.get(i));
            list.add(customer);
        }
        return customerService.batchInsert(list);
    }

    @RequestMapping("findCustomer")
    public Customer findCustomer(Customer customer) {
        return customerService.findCustomer(customer);
    }

    @RequestMapping("findCustomerList")
    public List<Customer> findCustomerList(Customer customer) {
        return customerService.findCustomerList(customer);
    }
    @GetMapping("list")
    public List<Customer> findList() {
        return customerService.findList();
    }


    @RequestMapping("findCustomerListMap")
    public List<Map> findCustomerListMap(Customer customer) {
        return customerService.findCustomerListMap(customer);
    }
}
