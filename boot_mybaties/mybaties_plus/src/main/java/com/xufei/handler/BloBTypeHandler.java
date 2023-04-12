package com.xufei.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.io.ByteArrayInputStream;
import java.sql.*;
/**
 * 举例:将MySQL中的blob类型转换成Java中的String类型
 *
 * 步骤 1.映射字段上添加如下注解，指定自定义的handler:
 *      @TableField(typeHandler=BloBTypeHandler.class)
 * 步骤 2.application.yml
 * mybatis:
 *   configuration:
 *     map-underscore-to-camel-case: true
 *     type-handlers-package: com.xxx.handler   #指定BloBTypeHandler所在的包位置
 * 步骤 3.Mapper接口
 *     @Select("SELECT * FROM wx_public_user WHERE wxId=#{wxId} LIMIT #{page},#{pageNumber}")
 *     @Results(value = {@Result(property = "nickname", column = "nickname", javaType = String.class, jdbcType = JdbcType.BLOB)})
 *     List<WxMpUser> findWxID(String wxId, int page, int pageNumber);
 * 步骤4 实体类需要加个注解
 *      @TableName(autoResultMap = true)
 */

@MappedJdbcTypes(JdbcType.BLOB)//MappedJdbcTypes 指定数据库里的类型
@MappedTypes(value = String.class)//MappedTypes 指定要转换的类型
public class BloBTypeHandler extends BaseTypeHandler<String> {
    private static final String DEFAULT_CHARSET = "UTF-8";
    /**
     * 设置参数 一个set方法，表示向PreparedStatement里面设置值。
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {
        try {
            ps.setBlob(i, new ByteArrayInputStream(parameter.getBytes(DEFAULT_CHARSET)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 根据列名获取值
     */
    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return blobToString(rs.getBlob(columnName));
    }
    /**
     * 根据列索引位置获取值
     */
    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return blobToString(rs.getBlob(columnIndex));
    }
    /**
     * 根据存储过程获取值
     */
    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return blobToString(cs.getBlob(columnIndex));
    }


    private String blobToString(Blob blob) {
        String result = null;
        try {
            byte[] returnValue = null;
            if (null != blob) {
                returnValue = blob.getBytes(1, (int) blob.length());
            }
            if (null != returnValue) {
                result = new String(returnValue, DEFAULT_CHARSET);
            }
        } catch (Exception e) {
            throw new RuntimeException("Blob Encoding Error!");
        }
        return result;
    }
}
