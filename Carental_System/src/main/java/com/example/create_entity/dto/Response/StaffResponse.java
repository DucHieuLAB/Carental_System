package com.example.create_entity.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StaffResponse {

    String Address;
    Date DOB;
    String Phone;
    String FullName;
    String Email;
    String Identity_number;
    String UserName;
    Integer Status;

}
