package com.xufei.springcloud.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginHandlerInterceptor implements HandlerInterceptor {
    /**
     * 在目标方法处理之前进行此方法
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        log.info("preHandle拦截的请求路径是{}", requestURI);
        HttpSession session = request.getSession();//登录检查逻辑
        Object loginUser = session.getAttribute("loginUser");
        if (loginUser != null) {
            //放行
            return true;
        }
        //拦截住。未登录。跳转到登录页
        request.setAttribute("msg", "请先登录！！");  //放在请求域中，为什么不放到Session中，页面为什么可以直接获取在request域中那这个“msg”；
        // response.sendRedirect("/");
        request.getRequestDispatcher("/").forward(request, response);
        //getRequestDispatcher, 转发器
        return false;
    }
    /**
     * 在目标方法处理后还没到达页面之前(页面渲染前，目标方法执行之后)，可能还想给返回的页面放点数据（比如放上面“msg”的信息）
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle执行{}", modelAndView);
    }
    /**
     * 页面渲染以后
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("afterCompletion执行异常{}", ex);
    }
}
