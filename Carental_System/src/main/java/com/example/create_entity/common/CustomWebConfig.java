package com.example.create_entity.common;


import com.example.create_entity.Config.JwtInterceptor;
import com.example.create_entity.dto.Request.AccountMetaRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class CustomWebConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private JwtInterceptor jwtInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor);
    }

//    @Bean
//    @RequestScope
//    public AccountMetaRequest getAccountMetaRequest(){
//        return new AccountMetaRequest();
//    }
//    @Bean
//    public JwtInterceptor jwtInterceptor(){
//       return new JwtInterceptor(getAccountMetaRequest());
//    }

}
