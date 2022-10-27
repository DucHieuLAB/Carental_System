package com.example.create_entity.dto.Request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
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
