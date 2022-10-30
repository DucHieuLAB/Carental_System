package com.example.create_entity.Service;

import com.example.create_entity.Entity.*;
import com.example.create_entity.Repository.AccountRepository;
import com.example.create_entity.Repository.DistrictRepository;
import com.example.create_entity.Repository.RoleRepository;
import com.example.create_entity.dto.Request.RegisterInfoRequest;
import com.example.create_entity.dto.Request.StaffRequest;
import com.example.create_entity.dto.Request.UpdateInfoCustomerRequest;
import com.example.create_entity.dto.Response.PagingResponse;
import com.example.create_entity.dto.Response.ReposMesses;
import com.example.create_entity.dto.Response.StaffDetailResponse;
import com.example.create_entity.dto.Response.StaffResponse;
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
                return new ResponseEntity<>(accountEntity, HttpStatus.OK);
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
            if (accountEntity != null && accountEntity.getStatus() == 1) {
                Date date = new Date(System.currentTimeMillis());
                accountEntity.setModifiedDate(date);
                accountEntity.setStatus(0);

                staffResponse = staffResponse.staffResponseList(accountEntity);
                accountRepository.save(accountEntity);
                return new ResponseEntity<>(staffResponse, HttpStatus.OK);
            } else if (accountEntity != null && accountEntity.getStatus() == 0) {
                Date date = new Date(System.currentTimeMillis());
                accountEntity.setModifiedDate(date);
                accountEntity.setStatus(1);
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
            AccountEntity accountEntity = accountRepository.GetAccountByName(UserName);
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
    public ResponseEntity<?> sendOTPEmail(RegisterInfoRequest REQUEST, HttpServletResponse response) {

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
            accountEntity.setPassword(REQUEST.getPassword());
            accountEntity.setCreateDate(new Date());
            accountEntity.setModifiedDate(new Date());
            accountEntity.setRoleEntity(roleEntity);
            accountEntity.setStatus(1);

            Cookie cookie = new Cookie("username", REQUEST.getUserName());
            cookie.setMaxAge(5 * 60);
//            cookie.setSecure(true);
//            cookie.setHttpOnly(true);
            cookie.setPath("http://localhost:8080/api/account/CfOTP");
            response.addCookie(cookie);

            Cookie cookieOTP = new Cookie("OTP", Code_OTP);
            cookie.setMaxAge(5 * 60);
            cookie.setPath("http://localhost:8080/api/account/CfOTP");
            response.addCookie(cookieOTP);

            accountRepository.save(accountEntity);
            emailSenderService.sendSimpleEmail(REQUEST.getEmail(), "Here's your One Time Password (OTP) - Expire in 5 minutes!", Code_OTP);
            messes.setMess("Vui Lòng Kiểm tra mã OTP ở Email !");
            return new ResponseEntity<>(messes, HttpStatus.OK);
        } catch (Exception e) {
            messes.setMess(e.getMessage());
            return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> ConfirmOTPEmail(String username, String OTP,String OTP_ck) {
        ReposMesses messes = new ReposMesses();
        try {
            AccountEntity accountEntity = accountRepository.Check_ConfirmOTP(username);
            System.out.println(accountEntity);
            if (!accountEntity.equals(null) && OTP.trim().equals(OTP_ck.trim())) {
                accountEntity.setStatus(2);
                accountRepository.save(accountEntity);
                messes.setMess("Xác thực tài khoản thành công !");
                return new ResponseEntity<>(messes, HttpStatus.OK);
            }else{
                messes.setMess("Mã OTP không tồn tại !");
                return new ResponseEntity<>(messes,HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            messes.setMess(e.getMessage());
            return new ResponseEntity<>(messes,HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> UpdateCustomer(String username, UpdateInfoCustomerRequest updateInfoCustomerRequest) {
        AccountEntity accountEntity = accountRepository.GetAccountByName(username);

        return null;
    }


}
