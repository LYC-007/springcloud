package com.datasource.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class UserUtil {
    public static Map<String,Object> createUser(){
        Map<String, Object> map = new HashMap<>();
        map.put("name", "walking" + new Random().nextInt(Integer.MAX_VALUE));
        map.put("sex", new Random().nextInt(1));
        map.put("age", new Random().nextInt(80));
        return map;
    }
}
