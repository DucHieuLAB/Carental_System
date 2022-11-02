package com.example.create_entity.Service;

import com.example.create_entity.Entity.*;
import com.example.create_entity.Repository.AccountRepository;
import com.example.create_entity.Repository.DistrictRepository;
import com.example.create_entity.Repository.RoleRepository;
import com.example.create_entity.dto.Request.ChangePassWordRequest;
import com.example.create_entity.dto.Request.RegisterInfoRequest;
import com.example.create_entity.dto.Request.StaffRequest;
import com.example.create_entity.dto.Request.UpdateInfoCustomerRequest;
import com.example.create_entity.dto.Response.*;
import com.example.create_entity.untils.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AccountServiceIml implements AccountService {
    @Autowired
    AccountRepository accountRepository;

    StaffResponse staffResponse = new StaffResponse();
    @Autowired
    DistrictRepository districtRepository;

    @Autowired
    RoleRepository roleRepository;

    public Integer CheckNullPaging(Integer p) {
        if (p == null) {
            p = 0;
        } else if (p > 0) {
            p = p - 1;
        }
        return p;
    }

    @Override
    public ResponseEntity<?> getListByNameRole(Integer p) {

        p = CheckNullPaging(p);
        Integer id = roleRepository.GetIDRoleByNameRole("Staff");
        Pageable pageable = PageRequest.of(p, 5);
        Page<AccountEntity> accountEntities = accountRepository.List_Staff(id, pageable);
        return responseEntity(accountEntities);

    }

    @Override
    @Transactional
    public ResponseEntity<?> Create_Staff(StaffRequest infoRequest) {
        ReposMesses messes = new ReposMesses();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        try {

            Date date = new Date(System.currentTimeMillis());
            String regexPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

            AccountEntity accountEntity = new AccountEntity();
            DistrictsEntity districtsEntity = new DistrictsEntity();
            RoleEntity roleEntity = roleRepository.GetRoleDriver("Staff");


            if (!accountRepository.Check_email(infoRequest.getEmail()).isEmpty()) {
                messes.setMess("Email đã tồn tại  !");
                return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
            } else if (!accountRepository.Check_username(infoRequest.getUser_Name()).isEmpty()) {
                messes.setMess("UserName đã tồn tại !");
                return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
            } else if (!infoRequest.getEmail().matches(regexPattern)) {
                messes.setMess("Email k đúng định dạng !");
                return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
            } else if (!accountRepository.Check_Phone(infoRequest.getPhone().trim()).isEmpty()) {
                messes.setMess("Số điện thoại đã được đăng kí trong hệ thống  !");
                return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
            } else if (!accountRepository.Check_Identity(infoRequest.getIdentity_Number().trim()).isEmpty()) {
                messes.setMess("Số Chứng minh thư đã được đăng kí trong hệ thống !");
                return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
            } else {


                accountEntity.setIdentity_Picture_Back(infoRequest.getIdentity_Picture_Back().trim());
                accountEntity.setIdentity_Picture_Front(infoRequest.getIdentity_Picture_Front().trim());
                accountEntity.setAddress(infoRequest.getAddress());
                accountEntity.setFullName(infoRequest.getFullName().trim());
                accountEntity.setEmail(infoRequest.getEmail().trim());
                accountEntity.setUsername(infoRequest.getUser_Name().trim());
                accountEntity.setCreateDate(date);
                accountEntity.setImg(infoRequest.getImg());
                accountEntity.setDOB(infoRequest.getDob());
                accountEntity.setGender(infoRequest.getGender());

                accountEntity.setIdentity_Number(infoRequest.getIdentity_Number().trim());

                String encodedPassword = passwordEncoder.encode(infoRequest.getPassword());
                accountEntity.setPassword(encodedPassword.trim());

                accountEntity.setPhone(infoRequest.getPhone().trim());
                accountEntity.setModifiedDate(date);
                accountEntity.setRoleEntity(roleEntity);
                accountEntity.setStatus(2);


                String city = infoRequest.getCity().trim();
                String district_name = infoRequest.getDistrict_Name().trim();
                String wards = infoRequest.getWards().trim();

                List<DistrictsEntity> districtsEntities = districtRepository.check_district(city, wards, district_name);
                if (!districtsEntities.isEmpty()) {
                    DistrictsEntity districts = districtRepository.check_districts(city, wards, district_name);
                    accountEntity.setDistrictsEntity(districts);
                } else {
                    districtsEntity.setCity(city);
                    districtsEntity.setDistrict_Name(district_name);
                    districtsEntity.setWards(wards);
                    accountEntity.setDistrictsEntity(districtsEntity);
                    districtRepository.save(districtsEntity);
                }
                accountRepository.save(accountEntity);
                messes.setMess("Đã Tạo Thành Công ! ");
                return new ResponseEntity<>(messes, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private ResponseEntity<?> responseEntity(Page<AccountEntity> accountEntities) {

        try {
            List<StaffResponse> staffResponseList = staffResponse.staffResponseList(accountEntities);

            PagingResponse pagingResponse = new PagingResponse();
            pagingResponse.setObjects(staffResponseList);
            pagingResponse.setTotalPage(accountEntities.getTotalPages());
            pagingResponse.setNumberPage(accountEntities.getNumber() + 1);

            if (staffResponseList.isEmpty()) {
                ReposMesses messes = new ReposMesses();
                messes.setMess("NOT FOUND");
                return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(pagingResponse, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> FilterByName(String Name, Integer p) {
        p = CheckNullPaging(p);
        Integer Role_id = roleRepository.GetIDRoleByNameRole("Staff");
        Pageable pageable = PageRequest.of(p, 1);
        Page<AccountEntity> accountEntities = accountRepository.FilterByName(Name.trim(), Role_id, pageable);
        return responseEntity(accountEntities);

    }

    @Override
    public ResponseEntity<?> FilterByPhone(String Phone, Integer p) {
        p = CheckNullPaging(p);
        Integer Role_id = roleRepository.GetIDRoleByNameRole("Staff");
        Pageable pageable = PageRequest.of(p, 5);
        Page<AccountEntity> accountEntities = accountRepository.FilterByPhone(Phone.trim(), Role_id, pageable);
        return responseEntity(accountEntities);
    }

    @Override
    public ResponseEntity<?> FilterByIdentity_Number(String Identity_Number, Integer p) {
        p = CheckNullPaging(p);
        Integer Role_id = roleRepository.GetIDRoleByNameRole("Staff");
        Pageable pageable = PageRequest.of(p, 5);
        Page<AccountEntity> accountEntities = accountRepository.FilterByIdentity_Number(Identity_Number.trim(), Role_id, pageable);
        return responseEntity(accountEntities);
    }

    @Override
    public ResponseEntity<?> ChangeStatus(String UserName) {
        ReposMesses messes = new ReposMesses();
        StaffResponse staffResponse = new StaffResponse();
        try {
            AccountEntity accountEntity = accountRepository.GetAccountByName(UserName.trim());
            if (accountEntity != null && accountEntity.getStatus() == 2) {
                Date date = new Date(System.currentTimeMillis());
                accountEntity.setModifiedDate(date);
                accountEntity.setStatus(0);

                staffResponse = staffResponse.staffResponseList(accountEntity);
                accountRepository.save(accountEntity);
                return new ResponseEntity<>(staffResponse, HttpStatus.OK);
            } else if (accountEntity != null && accountEntity.getStatus() == 0) {
                Date date = new Date(System.currentTimeMillis());
                accountEntity.setModifiedDate(date);
                accountEntity.setStatus(2);
                staffResponse = staffResponse.staffResponseList(accountEntity);
                accountRepository.save(accountEntity);
                return new ResponseEntity<>(staffResponse, HttpStatus.OK);
            }

        } catch (Exception e) {
            messes.setMess(e.getMessage());
            return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    @Override
    public ResponseEntity<?> GetDetail(String UserName) {
        StaffDetailResponse detailResponse = new StaffDetailResponse();
        try {
            AccountEntity accountEntity = accountRepository.GetAccountByName(UserName.trim());
            detailResponse = detailResponse.staffDetailResponse(accountEntity);
            return new ResponseEntity<>(detailResponse, HttpStatus.OK);
        } catch (Exception e) {
            ReposMesses messes = new ReposMesses();
            messes.setMess(e.getMessage());
            return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
        }
    }

    private RegisterInfoRequest registerInfoRequest;

    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private RandomString randomString;

    @Override
    @Transactional
    public ResponseEntity<?> sendOTPEmail_Register(RegisterInfoRequest REQUEST, HttpServletResponse response) {

        String regexPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        ReposMesses messes = new ReposMesses();
        RoleEntity roleEntity = roleRepository.GetRoleDriver("Customer");
        AccountEntity accountEntity = new AccountEntity();
        try {
            if (!accountRepository.Check_email(REQUEST.getEmail().trim()).isEmpty()) {
                messes.setMess("Email đã tồn tại  !");
                return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
            } else if (!accountRepository.Check_username(REQUEST.getUserName().trim()).isEmpty() || REQUEST.getUserName() == "error") {
                messes.setMess("UserName đã tồn tại !");
                return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
            } else if (!REQUEST.getEmail().matches(regexPattern)) {
                messes.setMess("Email k đúng định dạng !");
                return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
            }
            String Code_OTP = randomString.generateRandomString();
            accountEntity.setEmail(REQUEST.getEmail());
            accountEntity.setFullName(REQUEST.getFullName());
            accountEntity.setUsername(REQUEST.getUserName());
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(REQUEST.getPassword());
            accountEntity.setPassword(encodedPassword.trim());

            accountEntity.setCreateDate(new Date());
            accountEntity.setModifiedDate(new Date());
            accountEntity.setRoleEntity(roleEntity);
            accountEntity.setStatus(1);

            Cookie cookie = new Cookie("username", REQUEST.getUserName());
            cookie.setMaxAge(5 * 60);
            cookie.setPath("http://localhost:8080/api/account/CfOTP");
            response.addCookie(cookie);

            Cookie cookieOTP = new Cookie("OTP", Code_OTP);
            cookieOTP.setMaxAge(5 * 60);
            cookieOTP.setPath("http://localhost:8080/api/account/CfOTP");
            response.addCookie(cookieOTP);

            accountRepository.save(accountEntity);
            emailSenderService.sendSimpleEmail(REQUEST.getEmail(),"Here's your One Time Password (OTP) - Expire in 5 minutes!", Code_OTP);
            messes.setMess("Đã Đăng ký thành công tài khoản " +
                    "Vui Lòng Kiểm tra mã OTP ở Email để xác thực !");
            return new ResponseEntity<>(messes, HttpStatus.OK);
        } catch (Exception e) {
            messes.setMess(e.getMessage());
            return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
        }
    }
    @Override
    public ResponseEntity<?> Confirm_Register_OTPEmail(String username, String OTP, String OTP_ck,HttpServletResponse response) {
        ReposMesses messes = new ReposMesses();
        try {
            AccountEntity accountEntity = accountRepository.Check_ConfirmOTP(username.trim());
            if (accountEntity != null && OTP.trim().equals(OTP_ck.trim())) {
                accountEntity.setStatus(2);
                accountRepository.save(accountEntity);
                messes.setMess("Xác thực tài khoản thành công !");
                RemoveCookie("username", response, "http://localhost:8080/api/account/Customer/CfOTP");
                RemoveCookie("OTP", response, "http://localhost:8080/api/account/Customer/CfOTP");
                return new ResponseEntity<>(messes, HttpStatus.OK);
            } else {
                messes.setMess("Mã OTP đã hết hiệu lực !");
                return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            messes.setMess(e.getMessage());
            return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
        }
//        return null;
    }

    @Override
    public ResponseEntity<?> SendOTPtoEmail(String email,HttpServletResponse response) {
        ReposMesses messes = new ReposMesses();
        try {
            AccountEntity accountEntity = accountRepository.GetAccountByEmail(email.trim());
            System.out.println(accountEntity);
            if (accountEntity != null) {
                String Code_OTP = randomString.generateRandomString();
                Cookie cookie = new Cookie("email",email.trim());
                cookie.setPath("http://localhost:8080/api/account/CfOTP_Forgot");
                cookie.setMaxAge(5 * 60);
                response.addCookie(cookie);

                Cookie cookieOTP = new Cookie("OTP",Code_OTP);
                cookieOTP.setMaxAge(5 * 60);
                cookieOTP.setPath("http://localhost:8080/api/account/CfOTP_Forgot");
                response.addCookie(cookieOTP);
                emailSenderService.sendSimpleEmail(email, "Here's your One Time Password (OTP)  - Expire in 5 minutes!", Code_OTP);
                messes.setMess("Vui Lòng Kiểm tra mã OTP ở Email để xác thực !");
                return new ResponseEntity<>(messes, HttpStatus.OK);
            } else {
                messes.setMess("Email không tồn tại trong hệ thống hoặc đã bị khóa !");
                return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            messes.setMess(e.getMessage());
            return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> UpdateCustomer(UpdateInfoCustomerRequest updateInfoCustomerRequest) {
        ReposMesses messes = new ReposMesses();
        try {
            AccountEntity accountEntity = accountRepository.GetAccountByName(updateInfoCustomerRequest.getUserName());
            DistrictsEntity districtsEntity = new DistrictsEntity();
            accountEntity.setImg(updateInfoCustomerRequest.getImg_avt());
            accountEntity.setDOB(updateInfoCustomerRequest.getDoD());
            accountEntity.setAddress(updateInfoCustomerRequest.getAddress());
            accountEntity.setIdentity_Picture_Front(updateInfoCustomerRequest.getIdentity_picture_front());
            accountEntity.setIdentity_Picture_Back(updateInfoCustomerRequest.getIdentity_picture_back());
            accountEntity.setIdentity_Number(updateInfoCustomerRequest.getIdentity_number());
            accountEntity.setPhone(updateInfoCustomerRequest.getPhone());
            accountEntity.setGender(updateInfoCustomerRequest.getGender());

            List<DistrictsEntity> districtsEntities = districtRepository.check_district(updateInfoCustomerRequest.getCity(),
                    updateInfoCustomerRequest.getWards(),
                    updateInfoCustomerRequest.getDistrict_Name());
            if (districtsEntities.isEmpty()) {
                districtsEntity.setCity(updateInfoCustomerRequest.getCity());
                districtsEntity.setDistrict_Name(updateInfoCustomerRequest.getDistrict_Name());
                districtsEntity.setWards(updateInfoCustomerRequest.getWards());
                accountEntity.setDistrictsEntity(districtsEntity);
                districtRepository.save(districtsEntity);
            } else {
                DistrictsEntity districts = districtRepository.check_districts(
                        updateInfoCustomerRequest.getCity(),
                        updateInfoCustomerRequest.getWards(),
                        updateInfoCustomerRequest.getDistrict_Name());
                accountEntity.setDistrictsEntity(districts);
            }
            accountRepository.save(accountEntity);
            messes.setMess("Cập nhật thành công !   ");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    @Override
    public ResponseEntity<?> ConfirmOTPForgot(String Email, String OTP, String OTP_ck, HttpServletResponse response) {
        ResponseVo responseVo = new ResponseVo();
        try {
            AccountEntity accountEntity = accountRepository.GetAccountByEmail(Email.trim());
            if (accountEntity != null && OTP.trim().equals(OTP_ck.trim())) {
                responseVo.setMessage("Xác thực Email thành công !");
                responseVo.setStatus(true);
                RemoveCookie("email", response, "http://localhost:8080/api/account/CfOTP_Forgot");
                RemoveCookie("OTP", response, "http://localhost:8080/api/account/CfOTP_Forgot");
                Cookie cookie = new Cookie("email", Email);
                cookie.setPath("http://localhost:8080/api/account/ChangePassWord");
                cookie.setMaxAge(5 * 60);
                response.addCookie(cookie);
                return new ResponseEntity<>(responseVo, HttpStatus.OK);
            } else {
                responseVo.setMessage("Mã OTP không chính xác hoặc Không còn tồn tại !");
                responseVo.setStatus(false);
                return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            responseVo.setMessage(e.getMessage());
            responseVo.setStatus(false);
            return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> Change_password(ChangePassWordRequest response, String Email, HttpServletResponse httpServletResponse) {
        ResponseVo responseVo = new ResponseVo();
        try {
            AccountEntity accountEntity = accountRepository.GetAccountByEmail(Email.trim());
            if (accountEntity != null) {
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String encodedPassword = passwordEncoder.encode(response.getPassword());
                accountEntity.setPassword(encodedPassword);
                accountRepository.save(accountEntity);
                responseVo.setMessage("Thay đổi mật khẩu thành công !");
                responseVo.setStatus(true);
                RemoveCookie("email", httpServletResponse, "http://localhost:8080/api/account/ChangePassWord");
                return new ResponseEntity<>(responseVo, HttpStatus.OK);
            } else {
                responseVo.setMessage("Thay đổi mật thất bại vui lòng thực hiện lại !");
                responseVo.setStatus(false);
                return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            responseVo.setMessage(e.getMessage());
            responseVo.setStatus(false);
            return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
        }
    }

    public void RemoveCookie(String Name,HttpServletResponse response, String URL) {
        Cookie cookie = new Cookie(Name, null);
        cookie.setMaxAge(0);
        cookie.setPath(URL);
        response.addCookie(cookie);
    }
//    CustomerInfoResponse customerInfoResponse;
//
//    @Override
//    public ResponseEntity<?> DetailCustomer(String username) {
//        ReposMesses messes = new ReposMesses();
//        try {
//            AccountEntity account = accountRepository.GetAccountByName(username);
//            CustomerInfoResponse infoResponse = customerInfoResponse.customerInfoResponse(account,customerInfoResponse);
//            return new ResponseEntity<>(infoResponse, HttpStatus.OK);
//        }catch (Exception e){
//            messes.setMess(e.getMessage());
//            return new ResponseEntity<>(messes,HttpStatus.BAD_REQUEST);
//        }
//
//    }


}
