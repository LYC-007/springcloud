package com.xufei.security.handler;


import com.alibaba.fastjson.JSON;
import com.xufei.resultresp.R;
import com.xufei.security.constant.HttpStatus;
import com.xufei.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 授权的异常处理:
 *
 * 我们还希望在认证失败或者是授权失败的情况下也能和我们的接口一样返回相同结构的json，这样可以让前端能对响应进行统一的处理。要实现这个功能我们需要知道SpringSecurity的异常处理机制。
 * 在SpringSecurity中，如果我们在认证或者授权的过程中出现了异常会被ExceptionTranslationFilter捕获到。在ExceptionTranslationFilter中会去判断是认证失败还是授权失败出现的异常。
 *
 * 如果是授权过程中出现的异常会被封装成AccessDeniedException然后调用AccessDeniedHandler对象的方法去进行异常处理。
 *
 * 所以如果我们需要自定义异常处理，我们只需要自定义AccessDeniedHandler然后配置给SpringSecurity即可。
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) {
        String json = JSON.toJSONString( R.error(HttpStatus.FORBIDDEN,"您的权限不足！"));
        WebUtils.renderString(response,json);
    }
}
