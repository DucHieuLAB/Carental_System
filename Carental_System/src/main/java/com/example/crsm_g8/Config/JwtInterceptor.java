package com.example.crsm_g8.Config;

import com.example.crsm_g8.untils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private JwtUtils jwtUtils;

//    private AccountMetaRequest accountMetaRequest;
//
//    public JwtInterceptor(AccountMetaRequest accountMetaRequest){
//        this.accountMetaRequest = accountMetaRequest;
//    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(request.getRequestURI());
//        if((request.getRequestURI().contains("contract/add"))){
//            String author = request.getHeader("authorization");
//            Claims claims = jwtUtils.verify(author);
//            long id = Long.parseLong(claims.getSubject());
//            accountMetaRequest.setId(id);
//            accountMetaRequest.setRoleId(Long.parseLong(claims.get("role").toString()));
//        }
        return super.preHandle(request,response,handler);
    }
}
