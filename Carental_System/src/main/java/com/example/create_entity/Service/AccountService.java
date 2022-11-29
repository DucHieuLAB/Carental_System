package com.example.create_entity.Service;

import com.example.create_entity.Entity.AccountEntity;
import com.example.create_entity.dto.Request.*;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface AccountService {
   ResponseEntity<?>getListStaff(Integer p);

   ResponseEntity<?> Create_Staff(StaffRequest staffRequest);

   ResponseEntity<?>FilterByName(String Name,Integer p);

   ResponseEntity<?>FilterByPhone(String Phone,Integer p);

   ResponseEntity<?>FilterByIdentity_Number(String Identity_Number,Integer p);

   ResponseEntity<?>ChangeStatusStaff(String UserName);

   ResponseEntity<?>GetDetailStaff(String UserName);

   ResponseEntity<?> sendOTPEmail_Register(RegisterInfoRequest REQUEST);

   ResponseEntity<?> Confirm_Register_OTPEmail(ConfirmOTPRegisterRequest confirmOTPRegisterRequest);

   ResponseEntity<?> SendOTPtoEmail(String email);

   ResponseEntity<?> UpdateCustomer(UpdateInfoCustomerRequest updateInfoCustomerRequest);

   ResponseEntity<?> UpdateStaff(UpdateInfoStaffRequest updateInfoStaffRequest);

   ResponseEntity<?> ConfirmOTPForgot(String Email,String OTP);

    ResponseEntity<?> Change_password(ChangePassWordRequest response);


    // Customer

   ResponseEntity<?> ListCustomer (Integer p);
   ResponseEntity<?>DetailCustomer (String username);


    ResponseEntity<?> FilterByNameCustomer(String name, Integer p);

   ResponseEntity<?> FilterByPhoneCustomer(String phone, Integer p);

   ResponseEntity<?> FilterByIdentityCustomer(String identity_number, Integer p);

    ResponseEntity<?> change_password_2(ChangePassRequest changePassRequest);

   ResponseEntity<?> change_new_password(ChangePassRequest changePassRequest);
}
