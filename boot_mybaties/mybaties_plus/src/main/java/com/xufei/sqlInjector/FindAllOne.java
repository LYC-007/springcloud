package com.xufei.sqlInjector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;

public class FindAllOne extends AbstractMethod {
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        /* 执行 SQL ，动态 SQL 参考类 SqlMethod */
        String sql = "select * from " + tableInfo.getTableName();//tableInfo.getTableName()=表名
        String method = "findAllOne";  /* mapper 接口方法名一致 */
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        // 查询方法对应addSelectMappedStatementForTable
        return addSelectMappedStatementForTable(mapperClass, method, sqlSource, tableInfo);
    }
}


