package com.example.create_entity.untils;

import com.example.create_entity.dto.Response.ResponseVo;
import org.springframework.stereotype.Component;

@Component
public class ResponseVeConvertUntil {

    public static ResponseVo createResponseVo(boolean status , String message, Object data){
    return new ResponseVo(status,message,data);
    }

}
