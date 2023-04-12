package com.xufei.utils;


import javax.servlet.http.HttpServletResponse;

public class WebUtils {


    public static String renderString(HttpServletResponse response,String str){


        try {
           response.setStatus(200);
           response.setContentType("application/json");
           response.setCharacterEncoding("utf-8");
           response.getWriter().print(str);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }



}
