package com.example.create_entity.Service;

import com.example.create_entity.Entity.AccountEntity;
import com.example.create_entity.dto.Request.StaffRequest;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AccountService {
   ResponseEntity<?> getListByNameRole(Integer p);

   ResponseEntity<?> Create_Staff(StaffRequest staffRequest);

}
