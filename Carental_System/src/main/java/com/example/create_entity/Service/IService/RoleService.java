package com.example.create_entity.Service.IService;


import com.example.create_entity.Entity.RoleEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface  RoleService {
    ResponseEntity<?> ListRole();
}