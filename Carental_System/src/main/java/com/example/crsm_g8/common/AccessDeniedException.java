package com.example.crsm_g8.common;

public class AccessDeniedException extends RuntimeException{
    AccessDeniedException(String message){
        super(message);
    }
}
