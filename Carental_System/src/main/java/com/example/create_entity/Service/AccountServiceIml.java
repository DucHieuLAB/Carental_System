package com.example.create_entity.Service;

import com.example.create_entity.Entity.*;
import com.example.create_entity.Repository.AccountRepository;
import com.example.create_entity.Repository.DistrictRepository;
import com.example.create_entity.Repository.RoleRepository;
import com.example.create_entity.dto.Request.StaffRequest;
import com.example.create_entity.dto.Response.PagingResponse;
import com.example.create_entity.dto.Response.ReposMesses;
import com.example.create_entity.dto.Response.StaffResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AccountServiceIml implements AccountService {
    @Autowired
    AccountRepository accountRepository;


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

        List<StaffResponse> staffResponseList = new ArrayList<>();
        Integer id = roleRepository.GetIDRoleByNameRole("Staff");

        Pageable pageable = PageRequest.of(p, 5);
        Page<AccountEntity> accountEntities = accountRepository.List_Staff(id, pageable);
        try {
            accountEntities.forEach(AccountEntity -> {
                StaffResponse staffResponse = new StaffResponse();
                staffResponse.setPhone(AccountEntity.getPhone());
                staffResponse.setStatus(AccountEntity.getStatus());
                staffResponse.setFullName(AccountEntity.getFullName());
                staffResponse.setIdentity_number(AccountEntity.getIdentity_Number());
                staffResponse.setDOB(AccountEntity.getDOB());
                staffResponse.setUserName(AccountEntity.getUsername());
                staffResponse.setEmail(AccountEntity.getEmail());
                staffResponse.setAddress(AccountEntity.getAddress());
                staffResponseList.add(staffResponse);
            });
            PagingResponse pagingResponse = new PagingResponse();
            pagingResponse.setDriverInfoResponsesList(staffResponseList);
            pagingResponse.setTotalPage(accountEntities.getTotalPages());
            pagingResponse.setNumberPage(accountEntities.getNumber() + 1);

            if (accountEntities.isEmpty()) {
                ReposMesses messes = new ReposMesses();
                messes.setMess("NOT FOUND");
                return new ResponseEntity<>(messes, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(pagingResponse, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }

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
                accountEntity.setStatus(1);


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
}
