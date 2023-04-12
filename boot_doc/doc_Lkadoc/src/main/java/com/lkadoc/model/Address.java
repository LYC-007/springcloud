package com.lkadoc.model;

import com.lk.api.annotation.LKAModel;
import com.lk.api.annotation.LKAProperty;

@LKAModel
public class Address {
    @LKAProperty(value="地址ID^1")
    private Integer id;
    @LKAProperty(value="n~地址信息^深圳市龙华区",groups= {"getUser-n"})
    private String info;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getInfo() { return info; }
    public void setInfo(String info) { this.info = info; }
}
