package com.lkadoc.model;

import com.lk.api.annotation.LKAModel;
import com.lk.api.annotation.LKAProperty;

import java.util.List;

@LKAModel
public class User {
    @LKAProperty(value="用户ID^1001")
    private Integer id;
    @LKAProperty(value="n~用户名称^张三")
    private String name;
    @LKAProperty(value="n~年龄^20",description="范围0-120",groups = {"getUser-n"})
    private String age;
    @LKAProperty(value="角色对象")
    private Role role;
    @LKAProperty(value="用户爱好^运动")
    private String[] likes;
    @LKAProperty(value="地址信息",groups = {"getUser"})
    private List<Address> addresses;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAge() { return age; }
    public void setAge(String age) { this.age = age; }
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    public String[] getLikes() { return likes; }
    public void setLikes(String[] likes) { this.likes = likes; }
    public List<Address> getAddresses() { return addresses; }
    public void setAddresses(List<Address> addresses) { this.addresses = addresses; }
}
