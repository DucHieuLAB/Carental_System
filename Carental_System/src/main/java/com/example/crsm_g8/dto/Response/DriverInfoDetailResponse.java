package com.example.crsm_g8.dto.Response;

import com.example.crsm_g8.Entity.DriverEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DriverInfoDetailResponse {

    private String FullName;

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

    private int Status;

    private String driving_license_image_Front;

    private String driving_license_image_back;

    private String Driver_Number_License;

    private String District_Name;

    private String wards;

    private String City;

    private String img;

    public DriverInfoDetailResponse driverInfoResponses(DriverEntity driverEntities) {
        DriverInfoDetailResponse driverInfoDetailResponse = new DriverInfoDetailResponse();

        driverInfoDetailResponse.setPhone(driverEntities.getPhone());
        driverInfoDetailResponse.setAddress(driverEntities.getAddress());
        driverInfoDetailResponse.setWards(driverEntities.getDistrictsEntity().getWards());
        driverInfoDetailResponse.setGender(driverEntities.getGender());
        driverInfoDetailResponse.setDriving_license_image_Front(driverEntities.getDriving_license_image_Front());
        driverInfoDetailResponse.setName_License(driverEntities.getLicenseTypeEntity().getName_License());
        driverInfoDetailResponse.setYearExperience(driverEntities.getYear_Experience());
        driverInfoDetailResponse.setCity(driverEntities.getDistrictsEntity().getCity());
        driverInfoDetailResponse.setFullName(driverEntities.getFullName());
        driverInfoDetailResponse.setDob(driverEntities.getDOB());
        driverInfoDetailResponse.setDriver_Number_License(driverEntities.getDriver_Number_License());
        driverInfoDetailResponse.setDistrict_Name(driverEntities.getDistrictsEntity().getDistrict_Name());
        driverInfoDetailResponse.setImg(driverEntities.getImg());
        driverInfoDetailResponse.setDriving_license_image_back(driverEntities.getDriving_license_image_back());
        driverInfoDetailResponse.setStatus(driverEntities.getAccountEntity().getStatus());
        driverInfoDetailResponse.setEmail(driverEntities.getAccountEntity().getEmail());
        driverInfoDetailResponse.setIdentity_Picture_Front(driverEntities.getIdentity_Picture_Front());
        driverInfoDetailResponse.setIdentity_Picture_Back(driverEntities.getIdentity_Picture_Back());
        driverInfoDetailResponse.setIdentity_Number(driverEntities.getIdentity_Number());


        return driverInfoDetailResponse;


    }

}
