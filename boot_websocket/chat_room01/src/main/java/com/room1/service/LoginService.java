package com.room1.service;


import com.room1.po.Staff;

import java.util.ArrayList;

public interface LoginService {
	String getpwdbyname(String name);
	Long getUidbyname(String name);
	String getnamebyid(long id);
	ArrayList<Staff> getUserList();
}
