package com.example.create_entity.Service;

import com.example.create_entity.Entity.DriverEntity;
import com.example.create_entity.dto.Response.DriverInfoDetailResponse;
import com.example.create_entity.dto.Response.DriverInfoResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverService {

    public List<DriverInfoResponse> driverInfoResponseList(List<DriverEntity> driverEntities1, List<DriverEntity> driverEntities, String Para) {

        return null;
    }

   public DriverInfoDetailResponse driverInfoResponses(DriverEntity driverEntities, DriverInfoDetailResponse driverInfoDetailResponse) {


        driverInfoDetailResponse.setPhone(driverEntities.getAccountEntity().getPhone());
        driverInfoDetailResponse.setAddress(driverEntities.getAccountEntity().getAddress());
        driverInfoDetailResponse.setWards(driverEntities.getAccountEntity().getDistrictsEntity().getWards());
        driverInfoDetailResponse.setGender(driverEntities.getAccountEntity().getGender());
        driverInfoDetailResponse.setDriving_license_image_Front(driverEntities.getDriving_license_image_Front());
        driverInfoDetailResponse.setName_License(driverEntities.getLicenseTypeEntity().getName_License());
        driverInfoDetailResponse.setYearExperience(driverEntities.getYear_Experience());
        driverInfoDetailResponse.setCity(driverEntities.getAccountEntity().getDistrictsEntity().getCity());
        driverInfoDetailResponse.setFullName(driverEntities.getAccountEntity().getFullName());
        driverInfoDetailResponse.setDob(driverEntities.getAccountEntity().getDOB());
        driverInfoDetailResponse.setDriver_Number_License(driverEntities.getDriver_Number_License());
        driverInfoDetailResponse.setDistrict_Name(driverEntities.getAccountEntity().getDistrictsEntity().getDistrict_Name());
        driverInfoDetailResponse.setImg(driverEntities.getAccountEntity().getImg());
        driverInfoDetailResponse.setDriving_license_image_back(driverEntities.getDriving_license_image_back());
        driverInfoDetailResponse.setStatus(driverEntities.getStatus());
        driverInfoDetailResponse.setEmail(driverEntities.getAccountEntity().getEmail());
        driverInfoDetailResponse.setIdentity_Picture_Front(driverEntities.getAccountEntity().getIdentity_Picture_Front());
        driverInfoDetailResponse.setIdentity_Picture_Back(driverEntities.getAccountEntity().getIdentity_Picture_Back());
        driverInfoDetailResponse.setIdentity_Number(driverEntities.getAccountEntity().getIdentity_Number());


        return driverInfoDetailResponse;


    }

}
