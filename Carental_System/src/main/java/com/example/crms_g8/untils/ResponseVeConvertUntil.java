package com.example.crms_g8.untils;

import com.example.crms_g8.dto.Response.ResponseVo;
import org.springframework.stereotype.Component;

@Component
public class ResponseVeConvertUntil {

    public static ResponseVo createResponseVo(boolean status , String message, Object data){
    return new ResponseVo(status,message,data);
    }

}
