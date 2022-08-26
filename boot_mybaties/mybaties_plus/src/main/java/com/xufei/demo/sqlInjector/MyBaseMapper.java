package com.xufei.demo.sqlInjector;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.ArrayList;

/**
 * @Author: XuFei
 * @Date: 2022/8/11 16:36
 */
public interface MyBaseMapper<T> extends BaseMapper<T> {
    ArrayList<T> findAllOne();
}
