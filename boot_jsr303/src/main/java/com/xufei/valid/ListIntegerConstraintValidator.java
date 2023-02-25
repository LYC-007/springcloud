package com.xufei.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description:
 * @Created: with IntelliJ IDEA.
 * @author: 夏沫止水
 * @createTime: 2020-05-27 17:54
 **/

/**
 * ConstraintValidator<ListValue,Integer> ---->参数一:自定义注解;参数二:标注该自定义注解的字段的数据类型
 */
public class ListIntegerConstraintValidator implements ConstraintValidator<ListValue,Integer> {

    private final Set<Integer> set = new HashSet<>();//被final修饰的引用表示该引用的地址值不可变，里面的值可变

    /**
     * 初始化方法
     * @param constraintAnnotation
     */
    @Override
    public void initialize(ListValue constraintAnnotation) {
        int[] vals = constraintAnnotation.valueInteger();
        for (int val : vals) {
            set.add(val);
        }
    }

    /**
     * 判断是否效验成功
     * @param value 需要效验的值
     * @param context
     * @return
     */
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        //判断是否有包含的值
        return set.contains(value);
    }
}
