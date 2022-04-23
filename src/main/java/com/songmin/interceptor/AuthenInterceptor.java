package com.songmin.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws IOException {
        if (request.getRequestURL().toString().contains("/login")
            || request.getRequestURL().toString().contains("/register")) {
            return true;
        }
        if (request.getParameter("userId") != null) {
            return true;
        }
        String token = request.getHeader("tokenauthorization");
        token = token.replaceAll("Bearer", "");
        if (token == null || "".equals(token)) {
            response.sendError(500);
            response.setHeader("msg", "No token");
            return false;
        }
        Object obj = redisTemplate.opsForValue().get(token);
        if (obj == null) {
            response.sendError(500);
            response.setHeader("msg", "Token expired");
            return false;
        }
        request.setAttribute("userId", obj.toString());
        System.out.println("---- userId ----> " + obj.toString());
        return true;
    }
}
