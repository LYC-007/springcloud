package com.xufei.framework.handler;

import com.xufei.framework.annotation.MybatisDataProcess;
import com.xufei.framework.enums.HandleType;
import com.xufei.framework.annotation.DataHandle;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


public interface DataHandler {
    /**判断是否加密过**/
   String KEY_SENSITIVE = "#encryption#_";
   String IMPL_SENSITIVE = "$%SENSITIVE%$";
    /**
     * 获取类型
     */
    HandleType getHandleType();
    /**
     *前置处理，，，用于数据的加密和 脱敏 后存入数据库
     */
    String preProcess(String Value);

    /**
     *后置处理  查询到结果后 ，，，进行数据脱敏和解密
     */
    String posProcess(String Value);


    /**
     * 获取需要加密的字段
     *
     * @param declaredFields 声明的字段
     * @return 需要加密的字段
     */
    default  Set<Field> getProcessField(Field[] declaredFields) {
        //暂时只实现String类型的加密
        Set<Field> fieldSet = Arrays
                .stream(declaredFields)
                .filter(field ->field.isAnnotationPresent(DataHandle.class) &&field.getAnnotation(DataHandle.class).value().equals(getHandleType()) &&field.getType() == String.class)
                .collect(Collectors.toSet());
        for (Field field : fieldSet) {
            field.setAccessible(true);
        }
        return fieldSet;
    }

    /**
     * 数据脱敏
     */
    default <T> void startProcess(Field[] declaredFields, T paramsObject) throws IllegalAccessException {
        Set<Field> fields = getProcessField(declaredFields);
        for (Field field : fields) {
            Object object = field.get(paramsObject);
            String encryptValue = (String) object;
            if (encryptValue != null) {
                String encrypted ;
                //加密
                if(field.getAnnotation(DataHandle.class).value().equals(HandleType.ENCRYPA)){
                    if(!encryptValue.startsWith(KEY_SENSITIVE)){
                        encrypted =KEY_SENSITIVE+this.getClass().getSimpleName()+IMPL_SENSITIVE+preProcess(encryptValue);
                    }else{
                        encrypted= encryptValue;
                    }
                }else{
                    encrypted =preProcess(encryptValue);
                }
                field.set(paramsObject, encrypted);
            }

        }
    }

    /**
     * 解密
     *
     * @param <T>    T
     * @param result resultType的实例
     * @throws IllegalAccessException 字段不可访问异常
     */
    default <T> void resultProcess(T result) throws IllegalAccessException {
        //取出resultType的类
        Class<?> resultClass = result.getClass();
        Set<Field> decryptField = getProcessField(resultClass.getDeclaredFields());
        for (Field field : decryptField) {
            //result相当于字段的访问器
            Object object = field.get(result);
            //String的解密
            if (object instanceof String) {
                String decryptValue = (String) object;
                if(decryptValue.startsWith(KEY_SENSITIVE) && field.getAnnotation(DataHandle.class).value().equals(HandleType.ENCRYPA)){
                    decryptValue = decryptValue.substring(KEY_SENSITIVE.length());
                    if(isOneIpml(decryptValue)){
                        decryptValue = decryptValue.substring((this.getClass().getSimpleName()+IMPL_SENSITIVE).length());
                        String decrypted = posProcess(decryptValue);
                        field.set(result, decrypted);
                    }
                }else if(!field.getAnnotation(DataHandle.class).value().equals(HandleType.ENCRYPA)&& Objects.requireNonNull(AnnotationUtils.findAnnotation(result.getClass(), MybatisDataProcess.class)).desensitizationMode().equals(this.getClass())){
                    String decrypted = posProcess(decryptValue);
                    field.set(result, decrypted);
                }
            }
        }
    }

    default boolean isOneIpml(String str) throws IllegalAccessException {
        return  str.startsWith(this.getClass().getSimpleName()+IMPL_SENSITIVE);
    }


}
