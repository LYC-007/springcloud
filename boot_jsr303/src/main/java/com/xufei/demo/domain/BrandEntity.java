package com.xufei.demo.domain;

import com.xufei.demo.valid.AddGroup;
import com.xufei.demo.valid.ListValue;
import com.xufei.demo.valid.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * @NotEmpty 被注释的字符串的不能为 null 也不能为空
 * @NotBlank 被注释的字符串非 null，并且必须包含一个非空白字符
 * @Null 被注释的元素必须为 null
 * @NotNull 被注释的元素必须不为 null
 * @AssertTrue 被注释的元素必须为 true
 * @AssertFalse 被注释的元素必须为 false
 * @Pattern(regex=,flag=) 被注释的元素必须符合指定的正则表达式
 * @Email 被注释的元素必须是 Email 格式。
 * @Min(value) 被注释的元素必须是一个数字，其值必须大于等于指定的最小值
 * @Max(value) 被注释的元素必须是一个数字，其值必须小于等于指定的最大值
 * @DecimalMin(value) 被注释的元素必须是一个数字，其值必须大于等于指定的最小值
 * @DecimalMax(value) 被注释的元素必须是一个数字，其值必须小于等于指定的最大值
 * @Size(max=, min=) 被注释的元素的大小必须在指定的范围内
 * @Digits (integer, fraction) 被注释的元素必须是一个数字，其值必须在可接受的范围内
 * @Past 被注释的元素必须是一个过去的日期
 * @Future 被注释的元素必须是一个将来的日期
 */
@Data
public class BrandEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 品牌id
     */
    //groups:用来标注那一种情况才用这种规则校验，这里表示更新时必须不为空
    @NotNull(message = "修改必须指定品牌id", groups = {UpdateGroup.class})
    // //groups:用来标注那一种情况才用这种规则校验，这里表示新增时必须为空
    @Null(message = "新增不能指定id", groups = {AddGroup.class})//
    private Long brandId;
    /**
     * 品牌名
     * message的提示信息如果你没有自定义默认会到ValidationMessages.properties配置文件中去取
     */
    @NotBlank(message = "品牌名必须提交", groups = {AddGroup.class, UpdateGroup.class})
    private String name;

    /**
     * 介绍
     */
    private String descript;

    /**
     * 显示状态[0-不显示；1-显示]
     */
    // @Pattern()
    //@NotNull(groups = {AddGroup.class})
    @ListValue(valueInteger = {1, 2}, groups = {AddGroup.class})
    //ListValue是一个自定义校验注解, 表示在showStatus的取值只有这两种
    private Integer showStatus;
    @ListValue(valueString = {"男", "女"}, groups = {AddGroup.class})
    private String sex;

    /**
     * 检索首字母
     */
    @NotEmpty(groups = {AddGroup.class})
    @Pattern(regexp = "^[a-zA-Z]$", message = "检索首字母必须是一个字母", groups = {AddGroup.class, UpdateGroup.class})
    private String firstLetter;
    /**
     * 排序
     */
    @NotNull(groups = {AddGroup.class})
    @Min(value = 0, message = "排序必须大于等于0", groups = {AddGroup.class, UpdateGroup.class})
    private Integer sort;
}

