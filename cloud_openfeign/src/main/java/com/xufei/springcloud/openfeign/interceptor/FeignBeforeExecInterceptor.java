package com.xufei.springcloud.openfeign.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/*
  使用OpenFeign进行远程调用时。OpenFeign会创建一个新的请求，新创建的请求没有老请求头里面的Cookie信息，导致远程服务也就获取不到老请求的Cookie信息;
  OpenFeign在为新请求添加数据的时候会过一个拦截器（RequestInterceptor），我们可以利用该拦截器往新请求头里面添加老请求的Cookie信息;
  在请求从浏览器过来的时候Spring会把请求的一些属性放RequestContextHolder到里面;只要当前线程不改变，就能获取到请求里面的数据，因为RequestContextHolder底层使用ThreadLocal实现的;
  所以在异步调用的时候，不能直接从RequestContextHolder里面获取到老请求的一些属性，在开启分线程时应该把主线程的属性往分线程的RequestContextHolder放一份;
 */
@Component
public class FeignBeforeExecInterceptor implements RequestInterceptor {//解决远程调用丢失请求头问题
    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();// 拿到原始请求头数据
            String cookie = request.getHeader("Cookie");
            if (!StringUtils.isEmpty(cookie)) {
                // 同步
                template.header("Cookie", cookie);
            }
        }
    }
    /**
     *
     * 异步调用时拿到原请求头的信息，还需要向分线程设置请求头的信息:   RequestContextHolder.setRequestAttributes(requestAttributes);
     * @Override
     * public OrderConfirmVO confirmOrder() {
     *     // 原线程绑定的requestAttributes，异步,防止feign丢失请求头
     *     RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
     *     CompletableFuture<Void> orderItemsTask = CompletableFuture.runAsync(() -> {
     *         // 赋值到新线程绑定的request
     *         RequestContextHolder.setRequestAttributes(requestAttributes);
     *         ..........
     *     }, executor);
     *     ................
     * }
     */
}

