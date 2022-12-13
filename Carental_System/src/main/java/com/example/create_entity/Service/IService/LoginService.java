package com.example.create_entity.Service.IService;

import com.example.create_entity.dto.Request.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {

    ResponseEntity<?> login(LoginRequest loginRequest);
}
