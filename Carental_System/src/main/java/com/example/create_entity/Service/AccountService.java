package com.example.create_entity.Service;

import com.example.create_entity.Entity.AccountEntity;
import com.example.create_entity.dto.Request.ChangePassWordRequest;
import com.example.create_entity.dto.Request.RegisterInfoRequest;
import com.example.create_entity.dto.Request.StaffRequest;
import com.example.create_entity.dto.Request.UpdateInfoCustomerRequest;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface AccountService {
   ResponseEntity<?> getListStaff(Integer p);

   ResponseEntity<?> Create_Staff(StaffRequest staffRequest);

   ResponseEntity<?>FilterByName(String Name,Integer p);

   ResponseEntity<?>FilterByPhone(String Phone,Integer p);

   ResponseEntity<?>FilterByIdentity_Number(String Identity_Number,Integer p);

   ResponseEntity<?>ChangeStatusStaff(String UserName);

   ResponseEntity<?>GetDetailStaff(String UserName);

   ResponseEntity<?> sendOTPEmail_Register(RegisterInfoRequest REQUEST, HttpServletResponse response);

   ResponseEntity<?> Confirm_Register_OTPEmail(String username,String OTP,String OTP_ck,HttpServletResponse response);


   ResponseEntity<?> SendOTPtoEmail(String email,HttpServletResponse response);

//   ResponseEntity<?> UpdateCustomer(UpdateInfoCustomerRequest updateInfoCustomerRequest);

   ResponseEntity<?> ConfirmOTPForgot(String Email,String OTP,String OTP_ck,HttpServletResponse response);

    ResponseEntity<?> Change_password(ChangePassWordRequest response,String Email,HttpServletResponse httpServletResponse);

   ResponseEntity<?>DetailCustomer (String username);





}
