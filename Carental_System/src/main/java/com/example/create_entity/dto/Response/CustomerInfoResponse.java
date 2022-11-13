package com.example.create_entity.dto.Response;

import com.example.create_entity.Entity.AccountEntity;
import com.example.create_entity.Entity.CustomerEntity;
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


    public CustomerInfoResponse customerInfoResponse(CustomerEntity customer) {
        CustomerInfoResponse customerInfoResponse = new CustomerInfoResponse();
        customerInfoResponse.setUserName(customer.getAccountEntity().getUsername());
        customerInfoResponse.setDob(customer.getDOB());
        customerInfoResponse.setEmail(customer.getAccountEntity().getEmail());
        customerInfoResponse.setGender(customer.getAccountEntity().getStatus());
        customerInfoResponse.setAddress(customer.getAddress());
        customerInfoResponse.setIdentity_Number(customer.getIdentity_Number());
        customerInfoResponse.setIdentity_Picture_Back(customer.getIdentity_Picture_Back());
        customerInfoResponse.setIdentity_Picture_Front(customer.getIdentity_Picture_Front());
        customerInfoResponse.setStatus(customer.getStatus());
        if (customer.getDistrictsEntity() == null) {
            customerInfoResponse.setDistrict_Name("");
            customerInfoResponse.setCity("");
            customerInfoResponse.setWards("");
        } else {
            customerInfoResponse.setDistrict_Name(customer.getDistrictsEntity().getDistrict_Name());
            customerInfoResponse.setCity(customer.getDistrictsEntity().getCity());
            customerInfoResponse.setWards(customer.getDistrictsEntity().getWards());
        }
        customerInfoResponse.setImg(customer.getImg());
        customerInfoResponse.setRoleName(customer.getAccountEntity().getRoleEntity().getRole_Title());

        return customerInfoResponse;
    }
}
