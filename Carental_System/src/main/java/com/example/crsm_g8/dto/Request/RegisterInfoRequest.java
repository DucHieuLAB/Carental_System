package com.example.crsm_g8.dto.Request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterInfoRequest {

    private String FullName;
    private String Email;
    private String UserName;
    private String Password;


}
