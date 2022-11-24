package com.example.create_entity.common;

import com.example.create_entity.dto.Response.ResponseVo;
import com.example.create_entity.untils.ResponseVeConvertUntil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



@ControllerAdvice
public class GlobalExceptionHandel {
    @ExceptionHandler
    public ResponseEntity handelException(Exception e){
        // Invalid JWT token = 1 , Expired JWT token = 2, Unsupported JWT token =3 , JWT claims string is empty. = 4
        if(e.getMessage()=="Expired JWT token"){
            Error error = new Error();
            error.setId(1);
            ResponseVo responseVo = ResponseVeConvertUntil.createResponseVo(false,"Opps something went wrong",e.getMessage());
        }
        ResponseVo responseVo = ResponseVeConvertUntil.createResponseVo(false,"Opps something went wrong",e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(responseVo);
    }
    @ExceptionHandler
    public ResponseEntity handelAccessDeniedException(AccessDeniedException e){
        ResponseVo responseVo = ResponseVeConvertUntil.createResponseVo(false,"Phiên đăng nhập đã hết hạn",e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(responseVo);
    }
}
