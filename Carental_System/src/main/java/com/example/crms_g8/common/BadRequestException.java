package com.example.crms_g8.common;

import java.util.List;

public class BadRequestException extends RuntimeException{
    private List<Error> errors;
}
