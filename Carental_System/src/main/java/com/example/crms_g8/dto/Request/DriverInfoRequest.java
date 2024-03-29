package com.example.crms_g8.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DriverInfoRequest {


    private String FullName;
    private String username;
    private String name_License;

    private String Phone;

    private int yearExperience;
    private Date Dob;

    private String Email;

    private int Gender;

    private String Address;


    private String Identity_Picture_Front;
    private String Identity_Number;

    private String Identity_Picture_Back;

    private String img;

    private String driving_license_image_Front;


    private String driving_license_image_back;

    private String Driver_Number_License;

    private String Password;



    private String District_Name;

    private String wards;

    private String City;




}
