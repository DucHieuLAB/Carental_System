package com.example.create_entity.dto.Response;

import com.example.create_entity.Entity.AccountEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.security.PrivateKey;
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

    public List<StaffResponse> staffResponseList ( Page<AccountEntity> accountEntities){
        List<StaffResponse> staffResponseList = new ArrayList<>();

        accountEntities.forEach(AccountEntity -> {
            StaffResponse staffResponse = new StaffResponse();
            staffResponse.setPhone(AccountEntity.getPhone());
            staffResponse.setStatus(AccountEntity.getStatus());
            staffResponse.setFullName(AccountEntity.getFullName());
            staffResponse.setIdentity_number(AccountEntity.getIdentity_Number());
            staffResponse.setDOB(AccountEntity.getDOB());
            staffResponse.setUserName(AccountEntity.getUsername());
            staffResponse.setEmail(AccountEntity.getEmail());
            staffResponse.setAddress(AccountEntity.getAddress());
            staffResponseList.add(staffResponse);
        });

        return staffResponseList;
    }


    public StaffResponse staffResponseList ( AccountEntity accountEntity){
            StaffResponse staffResponse = new StaffResponse();
            staffResponse.setStatus(accountEntity.getStatus());
            staffResponse.setPhone(accountEntity.getPhone());
            staffResponse.setFullName(accountEntity.getFullName());
            staffResponse.setIdentity_number(accountEntity.getIdentity_Number());
            staffResponse.setDOB(accountEntity.getDOB());
            staffResponse.setUserName(accountEntity.getUsername());
            staffResponse.setEmail(accountEntity.getEmail());
            staffResponse.setAddress(accountEntity.getAddress());


        return staffResponse;
    }
}
