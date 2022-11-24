package com.example.create_entity.Config;

import com.example.create_entity.untils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(request.getRequestURI());
        if(!(request.getRequestURI().contains("login")|| request.getRequestURI().contains("Register"))){
            String author = request.getHeader("authorization");
//            jwtUtils.validateToken(author);
        }
        return super.preHandle(request,response,handler);
    }
}
