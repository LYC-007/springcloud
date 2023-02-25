package com.xufei.contorller;

import com.xufei.constant.Admin;
import com.xufei.service.AdminService;
import com.xufei.util.PageUtils;
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
    /**
     * 分页查询测试
     *
     * http://localhost:8008/admin/page?pageNum=2&pageSize=6&orderByColumn=updateTime&isAsc=desc
     */
    @GetMapping("/page")
    public void pageList() {
        PageUtils.startPage();
        ArrayList<Admin> arrayList = adminService.findAll();
        for (Admin admin : arrayList) {
            System.out.println(admin);
        }
    }


}
