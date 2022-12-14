package com.example.crms_g8.common;

public class AccessDeniedException extends RuntimeException{
    AccessDeniedException(String message){
        super(message);
    }
}
