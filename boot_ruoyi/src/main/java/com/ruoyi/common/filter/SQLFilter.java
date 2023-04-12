package com.ruoyi.common.filter;


import com.ruoyi.common.exception.BaseException;
import org.apache.commons.lang.StringUtils;

/**
 * SQL过滤
 *
 * @author Mark sunlightcs@gmail.com
 */
public class SQLFilter {
    /**
     * 仅支持字母、数字、下划线、空格、逗号、小数点（支持多个字段排序）
     */
    public static String SQL_PATTERN = "[a-zA-Z0-9_\\ \\,\\.]+";

    /**
     * 验证 order by 语法是否符合规范
     */
    public static boolean isValidOrderBySql(String value) {
        return value.matches(SQL_PATTERN);
    }

    /**
     * SQL注入过滤
     *
     * @param str 待验证的字符串
     */
    public static String sqlInject(String str) {
        if (StringUtils.isBlank(str)&& !isValidOrderBySql(str)) {//
            throw new BaseException("包含非法字符");
        }
        //去掉'|"|;|\字符
        str = StringUtils.replace(str, "'", "");
        str = StringUtils.replace(str, "\"", "");
        str = StringUtils.replace(str, ";", "");
        str = StringUtils.replace(str, "\\", "");
        //转换成小写
        str = str.toLowerCase();
        //非法字符
        String[] keywords = {"master", "truncate", "insert", "select", "delete", "update", "declare", "alter", "drop"};
        //判断是否包含非法字符
        for (String keyword : keywords) {
            if (str.contains(keyword)) {
                throw new BaseException("包含非法字符");
            }
        }
        return str;
    }
}
