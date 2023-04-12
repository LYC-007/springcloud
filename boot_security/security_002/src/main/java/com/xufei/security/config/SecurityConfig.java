package com.xufei.security.config;

import com.xufei.security.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration //    @Configuration启动容器+@Bean注册Bean

/**
 * 权限校验的方式有两种:
 * 1.基于注解    @EnableGlobalMethodSecurity(prePostEnabled =true) + @PreAuthorize
 * 2.基于配置    直接在下面的 configure(HttpSecurity http)方法中配置就可以:http.antMatchers("/hello3").hasAnyRole("权限1","权限2")
 */
@EnableGlobalMethodSecurity(prePostEnabled = true) //开启授权注解功能
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 允许匿名访问的地址
     */
    @Autowired
    private PermitAllUrlProperties permitAllUrl;

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;


    /**
     * 实际项目中我们不会把密码明文存储在数据库中。
     * SpringSecurity默认使用的PasswordEncoder来判断前端传过来的密码和数据库中查到的密码是否一致;
     * PasswordEncoder要求数据库中的密码格式为：{id}password(id用于判断密码的加密方式)。但是我们一般不会采用这种方式。所以就需要替换PasswordEncoder。
     * 我们一般使用SpringSecurity为我们提供的BCryptPasswordEncoder替换PasswordEncoder;
     * 只需要使用把BCryptPasswordEncoder对象注入Spring容器中，SpringSecurity就会使用该PasswordEncoder来进行密码校验
     */
    @Bean
/**
 * 强散列哈希加密实现
 */
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 我们需要把AuthenticationManager放入到Spring容器中，因为我们需要通过他的子类去调 DaoAuthenticationProvider，
     * DaoAuthenticationProvider再去调用UserDetailsService获取用户的信息;
     */

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Resource
    private IgnoreUrlsConfig ignoreUrlsConfig;

    /**
     * 放行登录页面等其他设置
     * anyRequest          |   匹配所有请求路径
     * access              |   SpringEl表达式结果为true时可以访问
     * anonymous           |   匿名可以访问
     * denyAll             |   用户不能访问
     * fullyAuthenticated  |   用户完全认证可以访问（非remember-me下自动登录）
     * hasAnyAuthority     |   如果有参数，参数表示权限，则其中任何一个权限可以访问
     * hasAnyRole          |   如果有参数，参数表示角色，则其中任何一个角色可以访问
     * hasAuthority        |   如果有参数，参数表示权限，则其权限可以访问
     * hasIpAddress        |   如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
     * hasRole             |   如果有参数，参数表示角色，则其角色可以访问
     * permitAll           |   用户可以任意访问
     * rememberMe          |   允许通过remember-me登录的用户访问
     * authenticated       |   用户登录后可访问
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();
        //1.配置文件上配置的也允许跳过权限验证
        for (String url : ignoreUrlsConfig.getUrls()) { //不需要保护的资源路径允许访问
            registry.antMatchers(url).permitAll();
        }
        //2.注解标记允许匿名访问的url,自定义@Anonymous注解添加到方法上可以实现跳过权限验证。
        permitAllUrl.getUrls().forEach(url -> registry.antMatchers(url).permitAll());

        http
                /**
                 * 关闭csrf
                 * CSRF是指跨站请求伪造（Cross-site request forgery）
                 * SpringSecurity防止CSRF攻击的方式就是通过csrf_token。
                 * 后端会生成一个csrf_token，前端发起请求的时候需要携带这个csrf_token,后端会有过滤器进行校验，如果没有携带或者是伪造的就不允许访问。
                 * 我们可以发现CSRF攻击依靠的是cookie中所携带的认证信息。
                 * 但是在前后端分离的项目中我们的认证信息其实是token，而token并不是存储中cookie中，并且需要前端代码去把token设置到请求头中才可以，所以CSRF攻击也就不用担心了。
                 */
                .csrf().disable()
                //不通过Session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //登录和未登录的都可以访问
                .antMatchers("/test").permitAll()
                // 静态资源，可匿名访问
                .antMatchers(HttpMethod.GET, "/", "/*.html", "/**/*.html", "/**/*.css", "/**/*.js", "/profile/**").permitAll()
                .antMatchers("/swagger-ui.html", "/swagger-resources/**", "/webjars/**", "/*/api-docs", "/druid/**").permitAll()
                // 对于登录login 注册register 验证码captchaImage 允许匿名访问
                .antMatchers("/user/login", "/register", "/captchaImage").permitAll()
                // 除上面外的所 有请求全部需要鉴权认证
                .anyRequest().authenticated();

        //把token校验过滤器添加到过滤器链中，并且配置该过滤器的位置(添加到UsernamePasswordAuthenticationFilter之前)
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);


        //配置异常处理器。
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)// 配置认证失败处理类
                .accessDeniedHandler(accessDeniedHandler);//配置授权的异常处理
        //允许跨域
        http.cors();
    }
}