package com.example.create_entity.dto.Response;

import com.example.create_entity.Entity.AccountEntity;
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
    private String Password;
    private String District_Name;
    private String wards;
    private String City;

//    public StaffDetailResponse staffDetailResponse(AccountEntity accountEntity){
//        StaffDetailResponse detailResponse = new StaffDetailResponse();
//
//        detailResponse.setAddress(accountEntity.getAddress());
//
//        detailResponse.setDob(accountEntity.getDOB());
//        detailResponse.setEmail(accountEntity.getEmail());
//        detailResponse.setFullName(accountEntity.getFullName());
//        detailResponse.setGender(accountEntity.getGender());
//        detailResponse.setIdentity_Picture_Front(accountEntity.getIdentity_Picture_Front());
//        detailResponse.setIdentity_Number(accountEntity.getIdentity_Number());
//        detailResponse.setIdentity_Picture_Back(accountEntity.getIdentity_Picture_Back());
//        detailResponse.setPhone(accountEntity.getPhone());
//        detailResponse.setUser_Name(accountEntity.getUsername());
//        detailResponse.setImg(accountEntity.getImg());
//        detailResponse.setStatus(accountEntity.getStatus());
//        detailResponse.setPassword(accountEntity.getPassword());
//        detailResponse.setDistrict_Name(accountEntity.getDistrictsEntity().getDistrict_Name());
//        detailResponse.setWards(accountEntity.getDistrictsEntity().getWards());
//        detailResponse.setCity(accountEntity.getDistrictsEntity().getCity());
//
//        return detailResponse;
//    }
}
