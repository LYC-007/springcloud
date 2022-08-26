package com.xufei.springcloud.login.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.xufei.springcloud.login.domain.UserVo;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;


public class Auth0JwtUtils01 {
    private static final String APP_SECRET = "!Q@W#E$R^Y&U";
    //token签发者
    private static final String TOKEN_USER = "HZSSSDSTYGZPT";
    //token过期时间
    public static final Long EXPIRE_DATE = 1000 * 60L;

    /**
     * 生成token
     */
    public static String Token(UserVo userVo) {
        return JWT.create()
                .withIssuer(TOKEN_USER)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(new Date().getTime() + EXPIRE_DATE))
                .withClaim("username", userVo.getUsername())
                .withClaim("userId", userVo.getId())
                .sign(Algorithm.HMAC256(APP_SECRET));
    }

    /**
     * 生成token
     */
    public static String createToken(Map<String, String> map) {
        //创建过期时间
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 7);  //7天过期
        //创建builder对象
        JWTCreator.Builder builder = JWT.create();
        //遍历map
        map.forEach(builder::withClaim);
        return builder.withExpiresAt(instance.getTime()).sign(Algorithm.HMAC256(APP_SECRET));
    }
    /**
     * 验证token
     * 验证过程中如果有异常，则抛出；
     * 如果没有,则返回 DecodedJWT 对象来获取用户信息;
     */
    public static void verifyToken(HttpServletRequest request) {
        Algorithm algorithm = Algorithm.HMAC256(APP_SECRET);
        try {
            if(request.getHeader("token")!=null){
                JWT.require(algorithm).build().verify(request.getHeader("token"));
            }
        } catch (SignatureVerificationException e) { //验证的签名不一致
            throw new SignatureVerificationException(algorithm);
        } catch (TokenExpiredException e) {
            throw new TokenExpiredException("token is already expired");
        } catch (AlgorithmMismatchException e) {
            throw new AlgorithmMismatchException("签名算法不匹配");
        } catch (InvalidClaimException e) {
            throw new InvalidClaimException("校验的claims内容不匹配");
        } catch (RuntimeException e) {
            throw new RuntimeException("登录信息错误，请重新登录！");
        }
    }
    /**
     * 验证token
     * 验证过程中如果有异常，则抛出；
     * 如果没有,则返回 DecodedJWT 对象来获取用户信息;
     */
    public static DecodedJWT verify(String token) {
        return JWT.require(Algorithm.HMAC256(APP_SECRET)).build().verify(token);
    }
    /**
     * 判断token是否存在与有效
     */
    public static boolean checkToken(HttpServletRequest request) {
        try {
            String token = request.getHeader("token");
            if (StringUtils.isEmpty(token)) {
                return false;
            }
            JWT.require(Algorithm.HMAC256(APP_SECRET)).build().verify(token);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    /**
     * 根据token字符串获取会员id
     */
    public static String getMemberIdByJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(APP_SECRET)).build().verify(jwtToken);

        Map<String, Claim> claims = verify.getClaims();
        for (Map.Entry<String,Claim> entry: claims.entrySet()){
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }

        return verify.getClaim("userId").asString();
    }
}

