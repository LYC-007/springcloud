package com.xufei.security.filter;

import com.xufei.utils.RedisCacheUtils;
import com.xufei.security.entity.LoginUser;
import com.xufei.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 *  我们需要自定义一个过滤器，这个过滤器会去获取请求头中的token，对token进行解析取出其中的userid。
 *
 * 使用userid去redis中获取对应的LoginUser对象。然后封装Authentication对象存入SecurityContextHolder
 *
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCacheUtils redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = request.getHeader("token");

        if (!StringUtils.hasText(token)) {//没有获取到就直接放行，因为不一定每个请求都带有token
            filterChain.doFilter(request, response);//放行
            return;
        }
        //解析token
        String userid;
        try {
            Claims claims = JwtUtil.parseJWT(token);
            userid = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("token非法");
        }
        //从redis中获取用户信息
        String redisKey = "login:" + userid;
        LoginUser loginUser = redisCache.getCacheObject(redisKey);
        if(Objects.isNull(loginUser)){
            throw new RuntimeException("用户未登录！");
        }

        /**
         * 在SpringSecurity中，会使用默认的FilterSecurityInterceptor来进行权限校验。在FilterSecurityInterceptor中会从SecurityContextHolder获取其中的Authentication，
         * 然后获取其中的权限信息。当前用户是否拥有访问当前资源所需的权限。所以我们在项目中只需要把当前登录用户的权限信息也存入Authentication。然后设置我们的资源所需要的权限即可。
         * UsernamePasswordAuthenticationToken 实现 Authentication
         */
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(loginUser,null,loginUser.getAuthorities()));

        filterChain.doFilter(request, response);
    }
}
