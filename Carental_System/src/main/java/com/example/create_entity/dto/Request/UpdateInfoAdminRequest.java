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
public class UpdateInfoAdminRequest {
    private String UserName;
    private String FullName;
    private String img_avt;
    private int Gender;
    private String Identity_number;
    private String identity_picture_back;
    private String identity_picture_front;
    private String Phone;
    private String Address;
    private String District_Name;
    private String wards;
    private String City;
    private Date Dob;
}
