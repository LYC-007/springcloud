package com.xufei.handler;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
/*
数据库里有一个ids字段，里面存放的都是一些其他表的主键id，逗号分隔<1,6,3,5,8,4>。按照我一开始的解决思路，查出来转换成String，然后再分割一下，转换成Set。
实现步骤:
    1.实体类需要加个注解 @TableName(autoResultMap = true)
    2.对应对象的属性也需要加注解 @TableField(typeHandler = SetTypeHandler.class)
    3.自定义Handler  --> SetTypeHandler
 */
@MappedTypes(Set.class)//实体类要转换的类型：Set
@MappedJdbcTypes(JdbcType.VARCHAR)//对应数据库表的字段类型：varchar
public class SetTypeHandler extends BaseTypeHandler<Set<Integer>> {

    /*
    一个set，用来给PreparedStatement对象对应的列设置参数，预编译SQL语句，在SQL语句执行之前改变语句。
    向数据库中保存数据时Set被序列化成为 [1，2，3，4]先去掉Set的“[]”就变成了类似“ 1，2，3，4”，然后替换进SQL语句，这是完成了Set到varchar的转换,然后存入数据库;
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Set parameter, JdbcType jdbcType)
            throws SQLException {
        String param = parameter.toString().replaceAll("[\\[\\] ]", "");
        ps.setString(i, param);
    }

    /**

     */
    @Override
    public Set<Integer> getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        String sqlSet = rs.getString(columnName);
        return getSet(sqlSet);
    }

    @Override
    public Set<Integer> getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        String sqlSet = rs.getString(columnIndex);
        return getSet(sqlSet);
    }

    @Override//CallableStatement接口添加了调用存储过程核函数以及处理输出参数的方法。
    public Set<Integer> getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        String sqlSet = cs.getString(columnIndex);
        return getSet(sqlSet);
    }

    private Set<Integer> getSet(String sqlSet) {
        if (StringUtils.isNotBlank(sqlSet)) {
            return Arrays.stream(sqlSet.split(","))
                    .map(Integer::parseInt)
                    .collect(Collectors.toSet());
        }
        return new HashSet<>();
    }
}
