package com.luozhijian.campusmarket.interceptor;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            writeUnauthorized(response);
            return false;
        }
        try{
            String tokenValue=token.substring(7);
            DecodedJWT decoded=JWT.require(Algorithm.HMAC256(secret)).build().verify(tokenValue);
            Long userId = decoded.getClaim("userId").asLong();
            request.setAttribute("userId", userId);
            return true;
        }catch (JWTVerificationException e){
            writeUnauthorized(response);
            return false;
        }
    }
    private void writeUnauthorized(HttpServletResponse response)throws IOException {
        response.setStatus(401);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write("{\"code\":4001,\"message\":\"未登录或登录已过期\",\"data\":null}");
    }
}