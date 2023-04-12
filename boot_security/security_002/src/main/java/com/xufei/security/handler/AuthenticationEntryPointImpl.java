package com.xufei.security.handler;

import com.alibaba.fastjson.JSON;

import com.xufei.resultresp.R;
import com.xufei.security.constant.HttpStatus;
import com.xufei.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * 认证失败处理类 返回未授权:
 * 我们还希望在认证失败或者是授权失败的情况下也能和我们的接口一样返回相同结构的json，这样可以让前端能对响应进行统一的处理。要实现这个功能我们需要知道SpringSecurity的异常处理机制。
 * 在SpringSecurity中，如果我们在认证或者授权的过程中出现了异常会被ExceptionTranslationFilter捕获到。在ExceptionTranslationFilter中会去判断是认证失败还是授权失败出现的异常。
 *
 * 如果是认证过程中出现的异常会被封装成AuthenticationException然后调用AuthenticationEntryPoint对象的方法去进行异常处理。
 *
 * 所以如果我们需要自定义异常处理，我们只需要自定义AuthenticationEntryPoint然后配置给SpringSecurity即可。
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {
    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)throws IOException{
        String msg = "请求访问："+request.getRequestURI()+"，认证失败，无法访问系统资源！";

        WebUtils.renderString(response, JSON.toJSONString(R.error(HttpStatus.UNAUTHORIZED,msg)));
    }
    public static void renderString(HttpServletResponse response, String string){
        try{
            response.setStatus(HttpStatus.SUCCESS);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
