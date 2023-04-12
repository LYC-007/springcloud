package com.data.framework.util;

import java.util.Map;

public class StringBuilderUtil {
    public static StringBuilder replaceAll(StringBuilder stb, String oldStr, String newStr) {
        if (stb == null || oldStr == null || newStr == null || stb.length() == 0 || oldStr.length() == 0)
            return stb;
        int index = stb.indexOf(oldStr);
        if (index > -1 && !oldStr.equals(newStr)) {
            int lastIndex = 0;
            while (index > -1) {
                stb.replace(index, index + oldStr.length(), newStr);
                lastIndex = index + newStr.length();
                index = stb.indexOf(oldStr, lastIndex);
            }
        }
        return stb;
    }
    public static StringBuilder replaceMap(StringBuilder stb, Map<String,String> desensitizationMap) {
        for (Map.Entry<String,String> entry : desensitizationMap.entrySet()) {
            stb = replaceAll(stb, entry.getKey(), entry.getValue());
        }
        return stb;
    }
    public static StringBuilder replaceFlipMap(StringBuilder stb, Map<String,String> desensitizationMap) {
        for (Map.Entry<String,String> entry : desensitizationMap.entrySet()) {
            replaceAll(stb, entry.getValue(), entry.getKey());
        }
        return stb;
    }
}
