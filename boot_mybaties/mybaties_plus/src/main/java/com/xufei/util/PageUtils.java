package com.xufei.util;

import com.github.pagehelper.PageHelper;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class PageUtils extends PageHelper {
    /**
     * 当前记录起始索引
     */
    public static final String PAGE_NUM = "pageNum";

    /**
     * 每页显示记录数
     */
    public static final String PAGE_SIZE = "pageSize";

    /**
     * 排序列
     */
    public static final String ORDER_BY_COLUMN = "orderByColumn";

    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    public static final String IS_ASC = "isAsc";
    /**
     * 分页参数合理化
     */
    public static final String REASONABLE = "reasonable";

    /**
     * 设置请求分页数据
     */
    public static void startPage() {
        Integer pageNum = toInt(getParameter(PAGE_NUM), 1);
        Integer pageSize = toInt(getParameter(PAGE_SIZE), 10);
        String orderBy = getOrderBy(getParameter(ORDER_BY_COLUMN), getParameter(IS_ASC));
        Boolean reasonable = getReasonable(toBool(getParameter(REASONABLE), null));
        PageHelper.startPage(pageNum, pageSize, orderBy).setReasonable(reasonable);
    }

    public static Boolean getReasonable(Boolean reasonable) {
        if (reasonable == null) {
            return Boolean.TRUE;
        }
        return reasonable;
    }

    public static String getOrderBy(String orderByColumn, String isAsc) {//这里只是一个字段进行排序
        if (StringUtils.isEmpty(orderByColumn)) {
            return "";//没有排序字段
        }
        if (StringUtils.isEmpty(isAsc)) {
            isAsc = "asc";
        }
        if (!("asc".equalsIgnoreCase(isAsc.trim()) || "desc".equalsIgnoreCase(isAsc.trim()))) {
            isAsc = "asc";
        }
        StringBuilder sb = new StringBuilder(orderByColumn.length() + 2);
        // 前置字符是否大写
        boolean preCharIsUpperCase = true;
        // 当前字符是否大写
        boolean curreCharIsUpperCase = true;
        // 下一字符是否大写
        boolean nexteCharIsUpperCase = true;
        for (int i = 0; i < orderByColumn.length(); i++) {
            char c = orderByColumn.charAt(i);
            if (i > 0) {
                preCharIsUpperCase = Character.isUpperCase(orderByColumn.charAt(i - 1));
            } else {
                preCharIsUpperCase = false;
            }

            curreCharIsUpperCase = Character.isUpperCase(c);

            if (i < (orderByColumn.length() - 1)) {
                nexteCharIsUpperCase = Character.isUpperCase(orderByColumn.charAt(i + 1));
            }

            if (preCharIsUpperCase && curreCharIsUpperCase && !nexteCharIsUpperCase) {//AAbb   aa_bb
                sb.append('_');
            } else if ((i != 0 && !preCharIsUpperCase) && curreCharIsUpperCase) {//aB  a_b
                sb.append('_');
            }
            sb.append(Character.toLowerCase(c));
        }
        return sb + " " + isAsc;
    }

    /**
     * 转换为boolean<br>
     * String支持的值为：true、false、yes、ok、no，1,0 如果给定的值为空，或者转换失败，返回默认值<br>
     * 转换失败不会报错
     *
     * @param value        被转换的值
     * @param defaultValue 转换错误时的默认值
     * @return 结果
     */
    public static Boolean toBool(Object value, Boolean defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        String valueStr;
        if (value instanceof String) {
            valueStr = (String) value;
        } else {
            valueStr = value.toString();
        }
        valueStr = valueStr.trim().toLowerCase();
        switch (valueStr) {
            case "true":
            case "yes":
            case "ok":
            case "1":
                return true;
            case "false":
            case "no":
            case "0":
                return false;
            default:
                return defaultValue;
        }
    }

    public static String getParameter(String name) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(requestAttributes!=null){
            return requestAttributes.getRequest().getParameter(name);
        }
        return null;
    }


    /**
     * 转换为int<br>
     * 如果给定的值为空，或者转换失败，返回默认值<br>
     * 转换失败不会报错
     *
     * @param value        被转换的值
     * @param defaultValue 转换错误时的默认值
     * @return 结果
     */
    public static Integer toInt(Object value, Integer defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        if (value instanceof Integer) {
            return (Integer) value;
        }
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }

        String str = value.toString();
        if (StringUtils.isEmpty(str)) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(str.trim());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 转换为字符串<br>
     * 如果给定的值为null，或者转换失败，返回默认值<br>
     * 转换失败不会报错
     *
     * @param value        被转换的值
     * @param defaultValue 转换错误时的默认值
     * @return 结果
     */
    public static String toStr(Object value, String defaultValue) {
        if (null == value) {
            return defaultValue;
        }
        if (value instanceof String) {
            return (String) value;
        }
        return value.toString();
    }
}
