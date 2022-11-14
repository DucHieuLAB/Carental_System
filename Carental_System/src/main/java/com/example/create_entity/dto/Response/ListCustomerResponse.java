package com.example.create_entity.dto.Response;

import com.example.create_entity.Entity.CustomerEntity;
import com.example.create_entity.Entity.StaffEntity;
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
public class ListCustomerResponse {

    String Address;
    Date DOB;
    String Phone;
    String FullName;
    String Email;
    String Identity_number;
    String UserName;
    Integer Status;

    public List<ListCustomerResponse> listCustomerResponses (Page<CustomerEntity> customerEntities){
        List<ListCustomerResponse> listCustomerResponses = new ArrayList<>();

        customerEntities.forEach(CustomerEntity -> {
           ListCustomerResponse staffResponse = new ListCustomerResponse();
            staffResponse.setPhone(CustomerEntity.getPhone());
            staffResponse.setStatus(CustomerEntity.getAccountEntity().getStatus());
            staffResponse.setFullName(CustomerEntity.getFullName());
            staffResponse.setIdentity_number(CustomerEntity.getIdentity_Number());
            staffResponse.setDOB(CustomerEntity.getDOB());
            staffResponse.setUserName(CustomerEntity.getAccountEntity().getUsername());
            staffResponse.setEmail(CustomerEntity.getAccountEntity().getEmail());
            staffResponse.setAddress(CustomerEntity.getAddress());
            listCustomerResponses.add(staffResponse);
        });
        return listCustomerResponses;
    }
}
