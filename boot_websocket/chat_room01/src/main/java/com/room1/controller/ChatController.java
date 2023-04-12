package com.room1.controller;


import com.room1.po.Staff;
import com.room1.po.User;
import com.room1.service.LoginService;
import com.room1.service.WebSocketServer;
import com.room1.util.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class ChatController {

	@Autowired
	LoginService loginservice;
	@RequestMapping("/onlineusers")
	@ResponseBody
	public Set<String> onlineusers(@RequestParam("currentuser") String currentuser) {
		ConcurrentHashMap<String, Session> map = WebSocketServer.getSessionPools();
		Set<String> set = map.keySet();
		Iterator<String> it = set.iterator();
		Set<String> nameset = new HashSet<String>();
		while (it.hasNext()) {
			String entry = it.next();
			if (!entry.equals(currentuser))
				nameset.add(entry);
		}
		return nameset;
	}
	@RequestMapping("getuid")
	@ResponseBody
	public User getuid(@RequestParam("username") String username) {
		Long a = loginservice.getUidbyname(username);
		User u = new User();
		u.setUid(a);
		return u;
	}

	/**
	 * http://localhost:8001/list?pageNum=1&pageSize=3
	 * 这里排序字段不好设置
	 * @return
	 */
	@RequestMapping("list")
	@ResponseBody
	public ArrayList<Staff> getUserList() {
		PageUtils.startPage();
		return loginservice.getUserList();
	}
}
