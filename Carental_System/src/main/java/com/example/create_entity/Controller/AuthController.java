package com.example.create_entity.Controller;

import com.example.create_entity.Service.ServiceImpl.LoginServiceImpl;
import com.example.create_entity.dto.Request.LoginRequest;
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
