package com.xufei.springcloud.interfaces01.service.impl;

import com.xufei.springcloud.interfaces01.domain.Response;
import com.xufei.springcloud.interfaces01.enums.ResponseCode;
import com.xufei.springcloud.interfaces01.exception.ServiceException;
import com.xufei.springcloud.interfaces01.service.TokenService;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public Response createToken() {
        //生成uuid当作token
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        //将生成的token存入redis中
        redisTemplate.opsForValue().set(token, token);
        //返回正确的结果信息
        return new Response(0, token.toString(), null);
    }

    @Override
    public Response checkToken(HttpServletRequest request) {
        //从请求头中获取token
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            //如果请求头token为空就从参数中获取
            token = request.getParameter("token");
            //如果都为空抛出参数异常的错误
            if (StringUtils.isBlank(token)) {
                throw new ServiceException(ResponseCode.ILLEGAL_ARGUMENT.getCode().toString(), ResponseCode.ILLEGAL_ARGUMENT.getMsg());
            }
        }


        //如果redis中不包含该token，说明token已经被删除了，抛出请求重复异常
        if (!redisTemplate.hasKey(token)) {
            throw new ServiceException(ResponseCode.REPETITIVE_OPERATION.getCode().toString(), ResponseCode.REPETITIVE_OPERATION.getMsg());
        }
        //删除token
        Boolean del = redisTemplate.delete(token);
        //如果删除不成功（已经被其他请求删除），抛出请求重复异常
        if (!del) {
            throw new ServiceException(ResponseCode.REPETITIVE_OPERATION.getCode().toString(), ResponseCode.REPETITIVE_OPERATION.getMsg());
        }
        return new Response(0, "校验成功", null);
    }
}
