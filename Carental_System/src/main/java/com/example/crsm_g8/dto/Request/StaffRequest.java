package com.example.crsm_g8.dto.Request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StaffRequest {

    private String Address;
    private Date Dob;
    private String Email;
    private String FullName;
    private int Gender;
    private String Identity_Picture_Front;
    private String Identity_Number;
    private String Identity_Picture_Back;
    private String Phone;
    private String User_Name;
    private String Img;
    private int Status;
    private String Password;
    private String District_Name;
    private String wards;
    private String City;

}
