package com.example.crms_g8.Service.IService;

import com.example.crms_g8.dto.Request.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {

    ResponseEntity<?> login(LoginRequest loginRequest);
}
