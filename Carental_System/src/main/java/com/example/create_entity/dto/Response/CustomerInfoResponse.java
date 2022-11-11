package com.example.create_entity.dto.Response;

import com.example.create_entity.Entity.AccountEntity;
import com.example.create_entity.Entity.DriverEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerInfoResponse {
    private String FullName;

    private String Phone;

    private String UserName;

    private Date Dob;

    private String Email;

    private int Gender;

    private String Address;

    private String Identity_Picture_Front;

    private String Identity_Number;

    private String Identity_Picture_Back;

    private int Status;

    private String District_Name;

    private String wards;

    private String City;

    private String img;

    private String RoleName;
//
//
//    public CustomerInfoResponse customerInfoResponse(AccountEntity accountEntity, CustomerInfoResponse customerInfoResponse) {
//
//        customerInfoResponse.setUserName(accountEntity.getUsername());
//        customerInfoResponse.setDob(accountEntity.getDOB());
//        customerInfoResponse.setEmail(accountEntity.getEmail());
//        customerInfoResponse.setGender(accountEntity.getStatus());
//        customerInfoResponse.setAddress(accountEntity.getAddress());
//        customerInfoResponse.setIdentity_Number(accountEntity.getIdentity_Number());
//        customerInfoResponse.setIdentity_Picture_Back(accountEntity.getIdentity_Picture_Back());
//        customerInfoResponse.setIdentity_Picture_Front(accountEntity.getIdentity_Picture_Front());
//        customerInfoResponse.setStatus(accountEntity.getStatus());
//        customerInfoResponse.setDistrict_Name(accountEntity.getDistrictsEntity().getDistrict_Name());
//        customerInfoResponse.setCity(accountEntity.getDistrictsEntity().getCity());
//        customerInfoResponse.setWards(accountEntity.getDistrictsEntity().getWards());
//        customerInfoResponse.setImg(accountEntity.getImg());
//        customerInfoResponse.setRoleName(accountEntity.getRoleEntity().getRole_Title());
//
//        return customerInfoResponse;
//    }
}
