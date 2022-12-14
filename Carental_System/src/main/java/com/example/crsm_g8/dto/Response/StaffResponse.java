package com.example.crsm_g8.dto.Response;

import com.example.crsm_g8.Entity.StaffEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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



    public List<StaffResponse> staffResponseList ( Page<StaffEntity> staffEntities){
        List<StaffResponse> staffResponseList = new ArrayList<>();

        staffEntities.forEach(StaffEntity -> {
            StaffResponse staffResponse = new StaffResponse();
            staffResponse.setPhone(StaffEntity.getPhone());
            staffResponse.setStatus(StaffEntity.getAccountEntity().getStatus());
            staffResponse.setFullName(StaffEntity.getFullName());
            staffResponse.setIdentity_number(StaffEntity.getIdentity_Number());
            staffResponse.setDOB(StaffEntity.getDOB());
            staffResponse.setUserName(StaffEntity.getAccountEntity().getUsername());
            staffResponse.setEmail(StaffEntity.getAccountEntity().getEmail());
            staffResponse.setAddress(StaffEntity.getAddress());
            staffResponseList.add(staffResponse);
        });

        return staffResponseList;
    }


    public StaffResponse staffResponse( StaffEntity staffEntities){

        StaffResponse staffResponse = new StaffResponse();

        staffResponse.setPhone(staffEntities.getPhone());
        staffResponse.setStatus(staffEntities.getAccountEntity().getStatus());
        staffResponse.setFullName(staffEntities.getFullName());
        staffResponse.setIdentity_number(staffEntities.getIdentity_Number());
        staffResponse.setDOB(staffEntities.getDOB());
        staffResponse.setUserName(staffEntities.getAccountEntity().getUsername());
        staffResponse.setEmail(staffEntities.getAccountEntity().getEmail());
        staffResponse.setAddress(staffEntities.getAddress());

        return staffResponse;
    }
}
