package com.example.create_entity.Service;

import com.example.create_entity.dto.Request.RegisterInfoRequest;
import com.example.create_entity.untils.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CustomerServicesIml implements CustomerService{

private RegisterInfoRequest registerInfoRequest;


   private  EmailSenderService emailSenderService;
   private RandomString randomString;


    @Override
    public void sendOTPEmail(RegisterInfoRequest REQUEST, String OTP) {
    String Code_OTP = randomString.generateRandomString();
    emailSenderService.sendSimpleEmail(REQUEST.getEmail(),Code_OTP,"Here's your One Time Password (OTP) - Expire in 5 minutes!");

    }

    @Override
    public void clearOTP(RegisterInfoRequest REQUEST) {
       registerInfoRequest.setOtpRequestedTime(null);
    }
}
