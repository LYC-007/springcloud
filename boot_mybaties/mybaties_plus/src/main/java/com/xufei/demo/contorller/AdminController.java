package com.xufei.demo.contorller;

import com.github.pagehelper.PageHelper;
import com.xufei.demo.domain.Admin;
import com.xufei.demo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * @Author: XuFei
 * @Date: 2022/8/11 16:09
 */

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping("/list")
    public ArrayList<Admin> findAll() {
        adminService.insertOne();
        ArrayList<Admin> arrayList = adminService.findAll();
        for (Admin admin : arrayList) {
            System.out.println(admin);
        }
        return arrayList;
    }

    @GetMapping("/updete")
    public void updateOne() {   //乐观锁和逻辑删除测试
        adminService.updateOne();
    }


    @GetMapping("/page")
    public void pageList() {   //分页查询测试

        PageHelper.startPage(2,3); //如果想自定义更多内容，请查看项目:mybatis_test
        ArrayList<Admin> arrayList = adminService.findAll();
        for (Admin admin : arrayList) {
            System.out.println(admin);
        }
    }


}
