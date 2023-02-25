package com.xufei.controller;

import com.xufei.constant.BrandEntity;
import com.xufei.utils.R;
import com.xufei.valid.AddGroup;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;

/**
 * 验证请求体(RequestBody)
 * 给需要校验的注解加上:@Valid 注解
 */
@RestController
@RequestMapping("/index")
public class BrandEntityController {
    /**
     * 版本一:只需要添加一个注解@Valid和定义好字段的校验规则,如果校验参数失败它会返回一个默认的错误信息
     * 这种方式如果传入的参数不符合定义的校验规则它也会返回错误信息，但是这种错误信息的格式可能不是我们所期望的，可能和我们之前出现错误返回的格式不一样;
     * @param brand
     * @return
     */
    @GetMapping("/1")
    public R save01(@Valid @RequestBody BrandEntity brand){
        return R.ok();
    }

    /**
     * 版本二:只需要添加一个注解@Valid和定义好字段的校验规则,我们通过BindingResult获取到出现错误的字段然后自己来拼装返回的格式;
     * @param brand
     * @param result
     * @return
     */
    @GetMapping("/2")
    public R save02(@Valid @RequestBody  BrandEntity brand, BindingResult result){//
        //前面字段的校验结果被封装在BindingResult里面，注意BindingResult需要紧跟前面字段
        if(result.hasErrors()){//判断校验结果是否出现错误
            HashMap<String, String> errorMap = new HashMap<>();
            result.getFieldErrors().forEach((item)->{//获取出现错误的字段和提示信息
                errorMap.put(item.getField(),item.getDefaultMessage());
            });
            return R.error(400,"提交的数据不合法").put("data",errorMap);
        }
        return R.ok();
    }

    /**
     * 版本三:@Valid+字段的校验规则+统一的异常处理(如果参数校验失败它会报一个“MethodArgumentNotValidException”异常，我们通过统一的异常处理来封装出现异常的返回错误信息格式，效果和版本二一样)
     * 具体的封装代码查看:MyExceptionControllerAdvice
     * 如果每个方法都需要自己拼装参数出现错误的返回信息格式那么就会出现很多重复的代码
     * @param brand
     * @return
     */
    @GetMapping("/3")
    public R save03(@Valid @RequestBody  BrandEntity brand){

        /**
         * @Valid对于标注分组校验的字段将不会进行校验,如果想要校验则使用@Validated注解
         * 什么叫标有分组校验的字段:
         *     @NotBlank(message = "品牌名必须提交", groups = {AddGroup.class, UpdateGroup.class})
         *     private String name;
         * 没有标注分组校验的字段:
         *     @NotBlank(message = "品牌名必须提交")
         *     private String name;
         */
        return R.ok();
    }

    /**
     * 分组校验: 对于有些字段在某些时候有不同的校验规则(比如"id",新增时不需要携带，但是在修改时必须携带)
     *
     * @Validated是Spring家提供的校验注解而@Valid是JSR303规范提供的注解，它们的作用一样都是需要对请求参数进行校验
     * @Validated可以指定一个或多个校验分组
     *
     * 注意:对于没有标注分组校验的字段将不会进行校验
     * @param brand
     * @return
     */
    @GetMapping("/4")
    public R save04(@Validated({AddGroup.class}) @RequestBody  BrandEntity brand){//
        return R.ok();
    }

    /**
     * 自定义校验:
     * 1.编写一个自定义校验注解-->ListValue
     * 2.编写一个自定义校验器-->ListValueConstraintValidator
     * 3.关联自定义校验注解和自定义校验器
     * @param brand
     * @return
     */
    @GetMapping("/5")
    public R save05(@Validated({AddGroup.class}) @RequestBody  BrandEntity brand){//
        return R.ok();
    }
}
