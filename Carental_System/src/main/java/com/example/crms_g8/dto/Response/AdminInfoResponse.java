package com.example.crms_g8.dto.Response;

import com.example.crms_g8.Entity.AdminEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminInfoResponse {
    private String FullName;

    private String Phone;

    private String User_Name;

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

    public AdminInfoResponse adminInfoResponse(AdminEntity adminEntity) {



        this.setFullName(adminEntity.getFullName());
        this.setUser_Name(adminEntity.getAccountEntity().getUsername());
        this.setDob(adminEntity.getDOB());
        this.setEmail(adminEntity.getAccountEntity().getEmail());
        this.setGender(adminEntity.getGender());
        this.setAddress(adminEntity.getAddress());
        this.setIdentity_Number(adminEntity.getIdentity_Number());
        this.setIdentity_Picture_Back(adminEntity.getIdentity_Picture_Back());
        this.setIdentity_Picture_Front(adminEntity.getIdentity_Picture_Front());
        this.setStatus(adminEntity.getStatus());
        this.setPhone(adminEntity.getPhone());
        if (adminEntity.getDistrictsEntity() == null) {
            this.setDistrict_Name("");
            this.setCity("");
            this.setWards("");
        } else {
            this.setDistrict_Name(adminEntity.getDistrictsEntity().getDistrict_Name());
            this.setCity(adminEntity.getDistrictsEntity().getCity());
            this.setWards(adminEntity.getDistrictsEntity().getWards());
        }
       this.setImg(adminEntity.getImg());
        this.setRoleName(adminEntity.getAccountEntity().getRoleEntity().getRole_Title());

        return this;
    }
}


