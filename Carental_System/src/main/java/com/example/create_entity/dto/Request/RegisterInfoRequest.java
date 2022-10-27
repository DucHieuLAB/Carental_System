package com.example.create_entity.dto.Request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterInfoRequest {

    private String Email;
    private String UserName;
    private String Password;
    private Date otpRequestedTime;



    private static final long OTP_VALID_DURATION = 5 * 60 * 1000;

    public boolean isOTPRequired() {

        long currentTimeInMillis = System.currentTimeMillis();
        long otpRequestedTimeInMillis = this.otpRequestedTime.getTime();

        if (otpRequestedTimeInMillis + OTP_VALID_DURATION < currentTimeInMillis) {
            // OTP expires
            return false;
        }
        return true;
    }

}
