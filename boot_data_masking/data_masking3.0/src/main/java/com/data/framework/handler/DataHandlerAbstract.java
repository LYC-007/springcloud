package com.data.framework.handler;

import com.data.framework.annotation.DataHandle;
import com.data.framework.annotation.HandleType;
import org.springframework.beans.factory.annotation.Value;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class DataHandlerAbstract implements DataHandler {

    @Value("${encryption.key}")
    public String key;

    public abstract HandleType getHandleType();

    public abstract String  preProcess(String Value)  ;

    public abstract String  posProcess(String Value) ;

    public static Set<Field> getField(Field[] declaredFields) {
        //暂时只实现String类型的加密
        Set<Field> fieldSet = Arrays.stream(declaredFields).filter(field ->
                field.isAnnotationPresent(DataHandle.class) &&
                        field.getType() == String.class)
                .collect(Collectors.toSet());
        for (Field field : fieldSet) {
            field.setAccessible(true);
        }
        return fieldSet;
    }
}