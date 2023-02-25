package com.xufei.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Description: 自定义注解规则
 * @Created: with IntelliJ IDEA.
 * @author: 夏沫止水
 * @createTime: 2020-05-27 17:48
 **/

/**
 * 自定义校验步骤:
 * 1.编写一个自定义校验注解-->ListValue
 * 2.编写一个自定义校验器-->ListValueConstraintValidator
 * 3.关联自定义校验注解和自定义校验器
 */
@Documented
@Constraint(validatedBy = { ListIntegerConstraintValidator.class,ListStringConstraintValidator.class})//添加自定义校验器，可以添加多个
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
public @interface ListValue {

    //对于不是自定义的注解message的提示信息如果你没有自定义默认会到系统的ValidationMessages.properties配置文件中去取，
    //所以自定义的注解如果你要给该注解添加默认值，你只需要编写一个名为ValidationMessages.properties配置文件
    //按照系统给定的“ValidationMessages.properties”配置文件的格式写自定义的就可以
    String message() default "{com.xufei.demo.valid.ListValue.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    int[] valueInteger() default { };

    String[] valueString() default {};

}
