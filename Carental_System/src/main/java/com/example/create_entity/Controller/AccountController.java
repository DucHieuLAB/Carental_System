package com.example.create_entity.Controller;

import com.example.create_entity.Service.AccountService;
import com.example.create_entity.Service.AccountServiceIml;
import com.example.create_entity.dto.Request.ChangePassRequest;
import com.example.create_entity.dto.Request.ChangePassWordRequest;
import com.example.create_entity.dto.Request.ConfirmOTPRegisterRequest;
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

    @RequestMapping(value = "/account/Customer/Register", method = RequestMethod.POST)
    public ResponseEntity<?> Create(@RequestBody RegisterInfoRequest registerInfoRequest) {
        return accountService.sendOTPEmail_Register(registerInfoRequest);
    }

    @RequestMapping(value = "/account/Customer/CfOTP", method = RequestMethod.PUT)
    public ResponseEntity<?> CfOTP(@RequestBody ConfirmOTPRegisterRequest confirmOTPRegisterRequest){

        return accountService.Confirm_Register_OTPEmail(confirmOTPRegisterRequest);
    }






    @RequestMapping(value = "/account/forgot/SendOTP", method = RequestMethod.GET)
    public ResponseEntity<?> Forgot_Password(@RequestParam(required = false) String Email) {
      return   accountService.SendOTPtoEmail(Email);
    }

    @RequestMapping(value = "/account/forgot/CfOTP_Forgot", method = RequestMethod.GET)
    public ResponseEntity<?> Cf_OTP_Forgot(@RequestParam(required = false) String Email,
                                   @RequestParam(required = false) String OTP) {

        return accountService.ConfirmOTPForgot(Email,OTP);
    }

    @RequestMapping(value = "/account/forgot/ChangePassWord", method = RequestMethod.PUT)
    public ResponseEntity<?> Create(@RequestBody ChangePassWordRequest response){
        return accountService.Change_password(response);
    }

    @RequestMapping(value = "/account/ChangeNewPassWord", method = RequestMethod.PUT)
    public ResponseEntity<?> ChangeNewPass(@RequestBody ChangePassRequest changePassRequest) {
        return accountService.change_new_password(changePassRequest);
    }





//    @RequestMapping(value = "account/Customer/Detail", method = RequestMethod.GET)
//    public ResponseEntity<?> GetDetail(@RequestParam(required = false) String username) {
//        return accountService.DetailCustomer(username);
//    }
}
