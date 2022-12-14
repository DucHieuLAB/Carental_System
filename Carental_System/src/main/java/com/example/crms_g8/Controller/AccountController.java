package com.example.crms_g8.Controller;


import com.example.crms_g8.Service.ServiceImpl.AccountServiceIml;
import com.example.crms_g8.dto.Request.ChangePassRequest;
import com.example.crms_g8.dto.Request.ChangePassWordRequest;
import com.example.crms_g8.dto.Request.ConfirmOTPRequest;
import com.example.crms_g8.dto.Request.RegisterInfoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    AccountServiceIml accountService;

    @RequestMapping(value = "/account/Customer/Register", method = RequestMethod.POST)
    public ResponseEntity<?>SignUp(@RequestBody RegisterInfoRequest registerInfoRequest) {
        return accountService.SendOTPEmailRegister(registerInfoRequest);
    }

    @RequestMapping(value = "/account/Customer/CfOTP", method = RequestMethod.PUT)
    public ResponseEntity<?>ConfirmOTPRegister(@RequestBody ConfirmOTPRequest confirmOTPRegisterRequest){

        return accountService.Confirm_Register_OTPEmail(confirmOTPRegisterRequest);
    }

    @RequestMapping(value = "/account/forgot/SendOTP", method = RequestMethod.GET)
    public ResponseEntity<?>SendOTPReset(@RequestParam(required = false) String Email) {
      return   accountService.SendOTPtoEmail(Email);
    }

    @RequestMapping(value = "/account/forgot/CfOTP_Forgot", method = RequestMethod.GET)
    public ResponseEntity<?>VerifyOTPReset(@RequestParam(required = false) String Email,
                                   @RequestParam(required = false) String OTP) {

        return accountService.ConfirmOTPForgot(Email,OTP);
    }

    @RequestMapping(value = "/account/forgot/ChangePassWord", method = RequestMethod.PUT)
    public ResponseEntity<?>ChangePass(@RequestBody ChangePassWordRequest response){
        return accountService.ChangePassword(response);
    }

    @RequestMapping(value = "/account/ChangeNewPassWord", method = RequestMethod.PUT)
    public ResponseEntity<?> ChangeNewPassword(@RequestBody ChangePassRequest changePassRequest) {
        return accountService.ChangeNewPass(changePassRequest);
    }

//    @RequestMapping(value = "/account/ChangeNewPassWord", method = RequestMethod.PUT)
//    public ResponseEntity<?>ConfirmO(@RequestBody ChangePassRequest changePassRequest) {
//        return accountService.change_new_password(changePassRequest);
//    }

    @RequestMapping(value = "/account/login/SendOTP", method = RequestMethod.GET)
    public ResponseEntity<?>SendOTP_Login(@RequestParam(required = false) String username) {
        return   accountService.SendOTP_Login(username);
    }
    @RequestMapping(value = "/account/login/ConfirmOTP_Login", method = RequestMethod.PUT)
    public ResponseEntity<?>VerifyOTPLogin(@RequestBody ConfirmOTPRequest confirmOTPRequest) {
        return   accountService.ConfirmOTP(confirmOTPRequest);
    }

//    @RequestMapping(value = "account/Customer/Detail", method = RequestMethod.GET)
//    public ResponseEntity<?> GetDetail(@RequestParam(required = false) String username) {
//        return accountService.DetailCustomer(username);
//    }
}
