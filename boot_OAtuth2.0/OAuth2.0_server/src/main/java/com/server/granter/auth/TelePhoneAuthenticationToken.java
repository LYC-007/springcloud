package com.server.granter.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * 手机验证码token
 */
public class TelePhoneAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private String telePhone;

    private String code;

    /**
     * @param principal 用户名
     */
    public TelePhoneAuthenticationToken(Object principal, Object credentials, String telePhone, String code) {
        super(principal, credentials);
        setAuthenticated(false);
        this.telePhone = telePhone;
        this.code = code;
    }

    public String getTelePhone() {
        return telePhone;
    }

    public String getCode() {
        return code;
    }
}