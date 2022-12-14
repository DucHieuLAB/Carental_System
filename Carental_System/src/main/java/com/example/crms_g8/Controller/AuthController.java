package com.example.crms_g8.Controller;

import com.example.crms_g8.Service.ServiceImpl.LoginServiceImpl;
import com.example.crms_g8.dto.Request.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    @Autowired
    private LoginServiceImpl loginService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        return loginService.login(loginRequest);
    }

}
