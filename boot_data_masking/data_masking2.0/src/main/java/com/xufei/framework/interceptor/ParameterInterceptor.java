package com.xufei.framework.interceptor;


import com.xufei.framework.annotation.MybatisDataProcess;
import com.xufei.framework.handler.DataHandler;
import com.xufei.framework.util.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.plugin.*;
import org.springframework.beans.BeansException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.util.*;
@Slf4j
@Component
@Intercepts({@Signature(type = ParameterHandler.class, method = "setParameters", args = PreparedStatement.class),})
public class ParameterInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //拦截 ParameterHandler 的 setParameters 方法 动态设置参数
        if (invocation.getTarget() instanceof ParameterHandler) {
            //@Signature 指定了 type= parameterHandler 后，这里的 invocation.getTarget() 便是parameterHandler
            //若指定ResultSetHandler ，这里则能强转为ResultSetHandler
            ParameterHandler parameterHandler = (ParameterHandler) invocation.getTarget();
            // 获取参数对像，即 mapper 中 paramsType 的实例
            Field parameterField = parameterHandler.getClass().getDeclaredField("parameterObject");
            parameterField.setAccessible(true);
            //取出实例
            Object parameterObject = parameterField.get(parameterHandler);
            if (parameterObject != null) {
                boolean isParamMap = parameterObject instanceof MapperMethod.ParamMap;
                if (isParamMap) {
                    //noinspection unchecked
                    Map<String, Object> uniqueMap = removeDuplicate((Map<String, Object>) parameterObject);
                    paramMap(uniqueMap);
                } else {
                    entity(parameterObject);
                }
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {

        return Plugin.wrap(target, this);//// 返回代理类
    }

    @Override
    public void setProperties(Properties properties) {
        // 可以调用properties.setProperty方法来给拦截器设置一些自定义参数
    }


    /**
     * 移除重复parameterField
     *
     * @param obj Map
     * @return uniqueMap
     */
    private Map<String, Object> removeDuplicate(Map<String, Object> obj) {
        Map<String, Object> uniqueMap = new HashMap<>(obj.size());
        for (Map.Entry<String, Object> entry : obj.entrySet()) {
            Object value = entry.getValue();
            String hashCode = String.valueOf(value.getClass().hashCode());
            if (!uniqueMap.containsKey(hashCode)) {
                uniqueMap.put(hashCode, value);
            }
        }
        return uniqueMap;
    }


    private void paramMap(Map<String, Object> parameterObject) throws IllegalAccessException, InstantiationException {
        for (Map.Entry<String, Object> entry : parameterObject.entrySet()) {
            Object value = entry.getValue();
            if (value == null) {
                continue;
            }
            if (value instanceof ArrayList) {
                //noinspection rawtypes
                ArrayList list = (ArrayList) value;
                if (!list.isEmpty()) {
                    Object firstItem = list.get(0);
                    MybatisDataProcess mybatisDataProcess = AnnotationUtils.findAnnotation(firstItem.getClass(), MybatisDataProcess.class);
                    if ((Objects.nonNull(mybatisDataProcess))) {
                        for (Object item : list) {
                            Field[] fields = item.getClass().getDeclaredFields();
                            startProcess(mybatisDataProcess,fields,item);
                        }
                    }
                }
            } else {
                entity(value);
            }
        }
    }

    private void entity(Object parameter) throws IllegalAccessException {
        //校验该实例的类是否被@SensitiveData所注解
        MybatisDataProcess mybatisDataProcess = AnnotationUtils.findAnnotation(parameter.getClass(), MybatisDataProcess.class);
        if ((Objects.nonNull(mybatisDataProcess))) {
            Field[] fields = parameter.getClass().getDeclaredFields();
            //提取加密字段
            startProcess(mybatisDataProcess,fields,parameter);
        }
    }
    private void startProcess (MybatisDataProcess mybatisDataProcess,Field[] fields,Object parameter) throws IllegalAccessException {
        DataHandler encryptHandler = getDataProcessHandler(mybatisDataProcess.encryptionMode());
        encryptHandler.startProcess(fields, parameter);
        //提取脱敏字段
        DataHandler desensitizationHandler = getDataProcessHandler(mybatisDataProcess.desensitizationMode());
        desensitizationHandler.startProcess(fields, parameter);
    }

    private DataHandler getDataProcessHandler(Class c) {
        try {
            return (DataHandler) SpringUtils.getBean(c);
        } catch (BeansException e) {
            log.error("no such bean like:{}", c);
            return null;
        }
    }



}

