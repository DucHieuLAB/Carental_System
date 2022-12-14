package com.example.crms_g8.dto.Response;

import com.example.crms_g8.Entity.StaffEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StaffDetailResponse {

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
    private String District_Name;
    private String wards;
    private String City;

    public StaffDetailResponse staffDetailResponse(StaffEntity staffEntity){
        StaffDetailResponse detailResponse = new StaffDetailResponse();

        detailResponse.setAddress(staffEntity.getAddress());

        detailResponse.setDob(staffEntity.getDOB());
        detailResponse.setEmail(staffEntity.getAccountEntity().getEmail());
        detailResponse.setFullName(staffEntity.getFullName());
        detailResponse.setGender(staffEntity.getGender());
        detailResponse.setIdentity_Picture_Front(staffEntity.getIdentity_Picture_Front());
        detailResponse.setIdentity_Number(staffEntity.getIdentity_Number());
        detailResponse.setIdentity_Picture_Back(staffEntity.getIdentity_Picture_Back());
        detailResponse.setPhone(staffEntity.getPhone());
        detailResponse.setUser_Name(staffEntity.getAccountEntity().getUsername());
        detailResponse.setImg(staffEntity.getImg());
        detailResponse.setStatus(staffEntity.getAccountEntity().getStatus());
//        detailResponse.setPassword(staffEntity.getAccountEntity().getPassword());
        detailResponse.setDistrict_Name(staffEntity.getDistrictsEntity().getDistrict_Name());
        detailResponse.setWards(staffEntity.getDistrictsEntity().getWards());
        detailResponse.setCity(staffEntity.getDistrictsEntity().getCity());

        return detailResponse;
    }
}
