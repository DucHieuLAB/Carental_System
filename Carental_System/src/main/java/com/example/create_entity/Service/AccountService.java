package com.example.create_entity.Service;

import com.example.create_entity.Entity.AccountEntity;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AccountService {
   ResponseEntity<?> getListByNameRole(Integer p);

}
