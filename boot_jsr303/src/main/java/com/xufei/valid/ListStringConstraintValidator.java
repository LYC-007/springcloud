package com.xufei.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author: XuFei
 * @Date: 2022/8/13 12:43
 */
public class ListStringConstraintValidator implements ConstraintValidator<ListValue, String> {
    private final Set<String> set = new HashSet<>();
    @Override
    public void initialize(ListValue constraintAnnotation) {
        String[] strings = constraintAnnotation.valueString();
        if (strings.length > 0) {
            Collections.addAll(set,strings);
        }

    }
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return set.contains(s);
    }
}
