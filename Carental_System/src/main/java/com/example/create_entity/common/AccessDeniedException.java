package com.example.create_entity.common;

public class AccessDeniedException extends RuntimeException{
    AccessDeniedException(String message){
        super(message);
    }
}
