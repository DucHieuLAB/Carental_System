package com.example.crsm_g8.common;


import com.example.crsm_g8.Config.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
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
