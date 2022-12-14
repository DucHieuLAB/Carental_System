package com.example.crsm_g8.Service.IService;

import com.example.crsm_g8.dto.Request.*;
import org.springframework.http.ResponseEntity;

public interface AccountService {
   ResponseEntity<?>getListStaff(Integer p);

   ResponseEntity<?> Create_Staff(StaffRequest staffRequest);

   ResponseEntity<?>FilterByName(String Name,Integer p);

   ResponseEntity<?>FilterByPhone(String Phone,Integer p);

   ResponseEntity<?>FilterByIdentity_Number(String Identity_Number,Integer p);

   ResponseEntity<?>ChangeStatusStaff(String UserName);

   ResponseEntity<?>DetailStaff(String UserName);



   ResponseEntity<?>SendOTPEmailRegister(RegisterInfoRequest REQUEST);

   ResponseEntity<?> Confirm_Register_OTPEmail(ConfirmOTPRequest confirmOTPRegisterRequest);

   ResponseEntity<?> SendOTPtoEmail(String email);

   ResponseEntity<?> UpdateCustomer(UpdateInfoCustomerRequest updateInfoCustomerRequest);

   ResponseEntity<?> UpdateStaff(UpdateInfoStaffRequest updateInfoStaffRequest);

   ResponseEntity<?> ConfirmOTPForgot(String Email,String OTP);

    ResponseEntity<?>ChangePassword(ChangePassWordRequest response);


    // Customer

   ResponseEntity<?>GetListCustomer(Integer p);
   ResponseEntity<?>DetailCustomer (String username);


    ResponseEntity<?> FilterByNameCustomer(String name, Integer p);

   ResponseEntity<?> FilterByPhoneCustomer(String phone, Integer p);

   ResponseEntity<?> FilterByIdentityCustomer(String identity_number, Integer p);

    ResponseEntity<?> change_password_2(ChangePassRequest changePassRequest);

   ResponseEntity<?>ChangeNewPass(ChangePassRequest changePassRequest);

    ResponseEntity<?> SendOTP_Login(String username);

   ResponseEntity<?>ConfirmOTP(ConfirmOTPRequest confirmOTPRequest);

    ResponseEntity<?>ChangeStatusCustomer(String username);
}
