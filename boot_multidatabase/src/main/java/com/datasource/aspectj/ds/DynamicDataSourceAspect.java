package com.datasource.aspectj.ds;

import com.datasource.aspectj.annotaion.ChangeDataSource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

@Aspect
@Component
@Order(1)
public class DynamicDataSourceAspect {
    @Pointcut("@annotation(com.datasource.aspectj.annotaion.ChangeDataSource)"
    +"||@within(com.datasource.aspectj.annotaion.ChangeDataSource)")
    public void pointCut(){}
    @Around(value = "pointCut()")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        ChangeDataSource  dataSource = AnnotationUtils.findAnnotation(signature.getMethod(), ChangeDataSource.class);
        if (!Objects.nonNull(dataSource)) {
            dataSource= AnnotationUtils.findAnnotation(signature.getDeclaringType(), ChangeDataSource.class);
        }
        if (!ObjectUtils.isEmpty(dataSource)) {
            DynamicDataSourceContextHolder.setDataSourceType(dataSource.value().name());
        }
        try {
            return point.proceed();
        } finally {
            DynamicDataSourceContextHolder.clearDataSourceType();// 销毁数据源 在执行方法之后
        }
    }
}
