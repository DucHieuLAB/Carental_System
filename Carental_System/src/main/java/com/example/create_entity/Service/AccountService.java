package com.example.create_entity.Service;

import com.example.create_entity.Entity.AccountEntity;
import com.example.create_entity.dto.Request.RegisterInfoRequest;
import com.example.create_entity.dto.Request.StaffRequest;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AccountService {
   ResponseEntity<?> getListByNameRole(Integer p);

   ResponseEntity<?> Create_Staff(StaffRequest staffRequest);

   ResponseEntity<?>FilterByName(String Name,Integer p);

   ResponseEntity<?>FilterByPhone(String Phone,Integer p);

   ResponseEntity<?>FilterByIdentity_Number(String Identity_Number,Integer p);

   ResponseEntity<?>ChangeStatus(String UserName);

   ResponseEntity<?>GetDetail(String UserName);

   ResponseEntity<?> sendOTPEmail(RegisterInfoRequest REQUEST);

   void  clearOTP(RegisterInfoRequest REQUEST);



}
