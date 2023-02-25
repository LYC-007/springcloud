package com.xufei.service;

import com.xufei.constant.Admin;

import java.util.ArrayList;

/**
 * @Author: XuFei
 * @Date: 2022/8/11 16:10
 */

public interface AdminService {
    ArrayList<Admin> findAll();

    void insertOne();

    void updateOne();
}
