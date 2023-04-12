package com.xufei.servlet;

import lombok.extern.slf4j.Slf4j;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
@Slf4j
@WebFilter(urlPatterns = "/login")//指定拦截路径
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("init方法开始了.....");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        log.info("我开始放行了");
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}

