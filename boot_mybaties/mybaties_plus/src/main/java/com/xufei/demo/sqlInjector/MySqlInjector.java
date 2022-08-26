package com.xufei.demo.sqlInjector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;

import java.util.List;

/**
 * @Author: XuFei
 * @Date: 2022/8/11 16:35
 */
public class MySqlInjector extends DefaultSqlInjector {
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        //MP自定义的SQL语句，如果不添加，MP自定义的语句就不能用了
        List<AbstractMethod> methodList = super.getMethodList(mapperClass);
        methodList.add(new FindAllOne());




        /**
         * 可以发现：虽然saveBatch(Collection<T> entityList)这个方法我们代码中只是一行代码，但是底层其实还是单条插入的。
         * 但是这样批量插入的速度有时其实是很慢的，那么有没有真正的批量插入方法呢？
         * 其实mybatis-plus给我们预留了一个真正批量插入的扩展插件InsertBatchSomeColumn
         */
        //methodList.add(new InsertBatchSomeColumn(i -> i.getFieldFill() != FieldFill.UPDATE));





        //根据Id更新固定的某些字段，更新时可以设置哪些字段需要更新，哪些字段不需要更新；
        // 如果设置需要更新的字段没有设置值，更新为null。
        //methodList.add(new AlwaysUpdateSomeColumnById());


        //根据id进行逻辑删除数据，并带自动填充功能
        //使用场景：当进行逻辑删除时，有些字段也要跟着更改（注销用户积分清零）
        //methodList.add(new LogicDeleteByIdWithFill());
        return methodList;
    }
}
