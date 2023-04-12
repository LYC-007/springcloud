package com.xufei.springcloud.interfaces01.service;

import com.xufei.springcloud.interfaces01.domain.Response;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface TokenService {
    Response createToken();

    Response checkToken(HttpServletRequest request);
}
