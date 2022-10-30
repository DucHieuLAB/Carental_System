package com.example.create_entity.untils;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
public class Validate {
public boolean validateString(String value,String regex,String errolMessage){
    boolean result = false;

    return result;
}
    public static int calculateAge(
            LocalDate birthDate,
            LocalDate currentDate) {
        return Period.between(birthDate, currentDate).getYears();
    }
}
