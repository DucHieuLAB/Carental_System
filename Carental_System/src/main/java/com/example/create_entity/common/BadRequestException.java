package com.example.create_entity.common;

import java.util.List;

public class BadRequestException extends RuntimeException{
    private List<Error> errors;
}
