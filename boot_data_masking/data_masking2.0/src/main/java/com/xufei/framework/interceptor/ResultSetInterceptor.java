package com.xufei.framework.interceptor;


import com.xufei.framework.annotation.MybatisDataProcess;
import com.xufei.framework.handler.DataHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;


@Slf4j
@Component
@Intercepts({//拦截  ResultSetHandler类的 handleResultSets方法 此方法参数为 :Statement.class
        @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})
})
public class ResultSetInterceptor implements Interceptor {
    @Autowired
    List<DataHandler> dataHandlerList;
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //取出查询的结果
        Object resultObject = invocation.proceed();
        if (Objects.isNull(resultObject)) {
            return null;
        }
        //基于selectList
        if (resultObject instanceof ArrayList) {
            ArrayList<?> resultList = (ArrayList<?>) resultObject;
            if(!resultList.isEmpty()){
                MybatisDataProcess sensitiveData = getAnnotation(resultList.get(0));
                if (sensitiveData != null) {
                    for (Object result : resultList) {
                        resultProcessOne(result);
                    }
                }
            }
            //基于selectOne
        } else {
            MybatisDataProcess mybatisDataProcess = getAnnotation(resultObject);
            if (mybatisDataProcess != null) {
                resultProcessOne(resultObject);
            }
        }
        return resultObject;
    }


    public void resultProcessOne(Object result) throws IllegalAccessException {
        Field[] fields = result.getClass().getDeclaredFields();
        if(fields.length>0){
            for (DataHandler dataHandler:dataHandlerList){
                dataHandler.resultProcess(result);
            }
        }
    }
    private MybatisDataProcess getAnnotation(Object object) {
        Class<?> objectClass = object.getClass();
        return AnnotationUtils.findAnnotation(objectClass, MybatisDataProcess.class);
    }


    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}

