package com.example.create_entity.Controller;

import com.example.create_entity.Service.AccountService;
import com.example.create_entity.dto.Request.RegisterInfoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    AccountService accountService;

    @RequestMapping(value = "/account/Register", method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody RegisterInfoRequest registerInfoRequest) {
        return  accountService.sendOTPEmail(registerInfoRequest);
    }
    }
