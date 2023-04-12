package com.lkadoc.model;

import com.lk.api.annotation.LKAModel;
import com.lk.api.annotation.LKAProperty;

@LKAModel
/**
 * @LKAModel#用来描述实体类的信息,常用属性：
 * value:属性的作用【可选】
 * description:属性的描述【可选】
 */
public class Role {
    /**
     * @LKAProperty#用来描述实体类的属性的信息，和@LKAParam注解属性用法差不多，常用属性:
     * value:属性的作用【必须】
     * description:属性的描述【可选】
     * hidden:是否在UI界面隐藏该属性，默认为false【可选】
     * testData:测试数据【可选】（更简便的用法是在value后面的"^"符号后面加上测试数据）
     * required:是否必传，默认为true【可选】（更简便的用法是在value前面加"n~"代表非必传）
     * isArray:是否是数组或集合，不设置也可自动识别【可选】
     * type:当属性为对象类型时，可以用type来指定，不设置也可自动识别【可选】
     * groups:用来进行参数分组设置，可设置多个组名【可选】（required在分组时用法是在groups属性里面的组名后面加"-n"代表不是必传，不加默认是必传）
     * #例如: groups={"add","update-n","info"}
     */
    @LKAProperty(value = "角色id^101")
    private Integer id;
    @LKAProperty(value = "n~角色^名称")
    private String name;

    public Integer getId() {return id;}
    public void setId(Integer id) {this.id = id;}
    public String getName() {return name;}
    public void setName(String name) { this.name = name; }
}
