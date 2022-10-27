package com.example.create_entity.Service;

import com.example.create_entity.dto.Request.RegisterInfoRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

public interface CustomerService {
    void sendOTPEmail(RegisterInfoRequest REQUEST, String OTP);

    void  clearOTP(RegisterInfoRequest REQUEST);
}
