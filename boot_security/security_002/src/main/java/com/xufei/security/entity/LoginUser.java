package com.xufei.security.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class LoginUser implements UserDetails {

    private User user;

    //存放当前登录用户的权限信息，一个用户可以有多个权限
    private List<String> permissions;

    public LoginUser(User user, List<String> permissions) {
        this.user = user;
        this.permissions = permissions;
    }

    //权限集合
    @JSONField(serialize = false)
    private  List<SimpleGrantedAuthority>  authorities;

    //获取权限信息

    /**
     * SpringSecurity会调用这个方法获取权限信息;
     *
     * 在SpringSecurity中，会使用默认的FilterSecurityInterceptor来进行权限校验。在FilterSecurityInterceptor中会从SecurityContextHolder获取其中的Authentication，
     * 然后获取其中的权限信息。当前用户是否拥有访问当前资源所需的权限。
     *
     * 所以我们在项目中只需要把当前登录用户的权限信息也存入Authentication。然后设置我们的资源所需要的权限即可。
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if (authorities!=null){
            return authorities;
        }
        //把permissions中String类型的权限信息封装成SimpleGrantedAuthority
        //第一种方式
        //List<GrantedAuthority> newList = new ArrayList<>();
//        for (String permission : permissions) {
//            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permission);
//            newList.add(authority);
//        }

        //方式二
        authorities = permissions.stream().distinct().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
