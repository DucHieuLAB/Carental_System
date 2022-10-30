package com.example.create_entity.Controller;

import com.example.create_entity.Service.AccountService;
import com.example.create_entity.Service.AccountServiceIml;
import com.example.create_entity.dto.Request.RegisterInfoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    AccountServiceIml accountService;

    @RequestMapping(value = "/account/Register", method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody RegisterInfoRequest registerInfoRequest, HttpServletResponse response) {
        return  accountService.sendOTPEmail(registerInfoRequest,response);
    }

    @RequestMapping(value = "/account/CfOTP", method = RequestMethod.POST)
    public ResponseEntity<?> CfOTP(@CookieValue(value = "username", defaultValue = "" ) String UserName,
                                   @RequestParam(required = false) String OTP,
                                   @CookieValue(value = "OTP", defaultValue = "" ) String OTP_ck){
        System.out.println(UserName);
        System.out.println(OTP_ck + "=" + OTP);
        return  accountService.ConfirmOTPEmail(UserName,OTP,OTP_ck);
    }
    }
