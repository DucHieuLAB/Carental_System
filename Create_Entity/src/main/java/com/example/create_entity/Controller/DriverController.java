package com.example.create_entity.Controller;

import com.example.create_entity.Entity.*;
import com.example.create_entity.Repository.*;
import com.example.create_entity.Service.DriverService;
import com.example.create_entity.dto.Request.DriverInfoRequest;
import com.example.create_entity.dto.Request.PagingDriver;
import com.example.create_entity.dto.Response.DriverInfoDetailResponse;
import com.example.create_entity.dto.Response.DriverInfoResponse;
import com.example.create_entity.dto.Response.LicenseInfoResponse;
import com.example.create_entity.dto.Response.ReposMesses;
import lombok.RequiredArgsConstructor;

import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DriverController {

    @Autowired
    DriverRepository driverRepository;

    @Autowired
    LicenseRepository licenseRepository;


    @Autowired
    AccountRepository accountRepository;
    @Autowired
    DriverService driverService = new DriverService();
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    DistrictRepository districtRepository;


    @RequestMapping(value = "/driver/ListDriver", method = RequestMethod.GET)
    public ResponseEntity<?> ManagerDriver(@RequestParam(value = "p", required = false) Integer p) {

        if (p == null) {
            p = 0;
        } else if (p > 0) {
            p = p - 1;
        }
        Pageable pageable = PageRequest.of(p, 5);

        Page<DriverEntity> page = driverRepository.GetDriverByStatus(pageable);


        List<DriverInfoResponse> driverInfoResponses = new ArrayList<>();
        page.forEach(DriverEntity -> {
            DriverInfoResponse infoResponse = new DriverInfoResponse();
            infoResponse.setName_License(DriverEntity.getLicenseTypeEntity().getName_License());
            infoResponse.setYearExperience(DriverEntity.getYear_Experience());
            infoResponse.setStatus(DriverEntity.getStatus());
            infoResponse.setPhone(DriverEntity.getAccountEntity().getPhone());
            infoResponse.setIdentity_Number(DriverEntity.getAccountEntity().getIdentity_Number());
            infoResponse.setFullName(DriverEntity.getAccountEntity().getFullName());
            infoResponse.setUsername(DriverEntity.getAccountEntity().getUsername());


            driverInfoResponses.add(infoResponse);


        });

        PagingDriver driverPagingDriver = new PagingDriver();
        driverPagingDriver.setDriverInfoResponsesList(driverInfoResponses);
        driverPagingDriver.setTotalPage(page.getTotalPages());
        driverPagingDriver.setNumberPage(p + 1);


        if (page.isEmpty()) {
            ReposMesses messes = new ReposMesses();
            messes.setMess("NOT DATA ! ");
        }
        return new ResponseEntity<>(driverPagingDriver, HttpStatus.OK);
    }

    @RequestMapping(value = "/driver/Search_name", method = RequestMethod.GET)
    public ResponseEntity<?> Find_By_Name(@RequestParam(required = false) String name, Integer p) {
        ReposMesses messes = new ReposMesses();

        if (p == null) {
            p = 0;
        } else if (p > 0) {
            p = p - 1;
        } else if (name == null) {
            name = "";
        }

        List<DriverEntity> driverEntities1 = driverRepository.GetDriverBy_fullName1(name.trim());
        Pageable pageable = PageRequest.of(p, 5);
        List<DriverEntity> driverEntities = driverRepository.GetDriverBy_fullName(name.trim(), pageable);


        List<DriverInfoResponse> infoResponses = new ArrayList<>();
        driverEntities.forEach(DriverEntity -> {
            DriverInfoResponse infoResponse = new DriverInfoResponse();

            infoResponse.setYearExperience(DriverEntity.getYear_Experience());
            infoResponse.setName_License(DriverEntity.getLicenseTypeEntity().getName_License());
            infoResponse.setPhone(DriverEntity.getAccountEntity().getPhone());
            infoResponse.setStatus(DriverEntity.getStatus());
            infoResponse.setIdentity_Number(DriverEntity.getAccountEntity().getIdentity_Number());
            infoResponse.setFullName(DriverEntity.getAccountEntity().getFullName());
            infoResponse.setUsername(DriverEntity.getAccountEntity().getUsername());

            infoResponses.add(infoResponse);


        });
        PagingDriver pagingDriver = new PagingDriver();
        pagingDriver.setDriverInfoResponsesList(infoResponses);
        pagingDriver.setTotalPage(driverEntities1.size() / 5 + 1);
        pagingDriver.setNumberPage(p + 1);


        if (infoResponses.isEmpty()) {
            messes.setMess("NOT FOUND ! ");
            return new ResponseEntity<>(messes, HttpStatus.OK);

        } else {
            return new ResponseEntity<>(pagingDriver, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/driver/Detail", method = RequestMethod.GET)
    private ResponseEntity<?> DetailDriver(@RequestParam(required = false) String username) {
        DriverEntity driverEntities = driverRepository.GetByUsername(username);
        DriverInfoDetailResponse driverInfoDetailResponse = new DriverInfoDetailResponse();
        driverInfoDetailResponse = driverService.driverInfoResponses(driverEntities, driverInfoDetailResponse);
        return new ResponseEntity<>(driverInfoDetailResponse, HttpStatus.OK);
    }


    @RequestMapping(value = "/driver/Search_Phone", method = RequestMethod.GET)
    public ResponseEntity<?> Find_By_Phone(@RequestParam(required = false) String Phone, Integer p) {


        if (p == null) {
            p = 0;
        } else if (p > 0) {
            p = p - 1;
        } else if (Phone == null) {
            Phone = "";
        }

        if (Phone == null) {
            Phone = "";
        }
        ReposMesses messes = new ReposMesses();
        List<DriverEntity> driverEntities1 = driverRepository.GetDriverBy_Phone1(Phone.trim());
        Pageable pageable = PageRequest.of(p, 5);
        List<DriverEntity> driverEntities = driverRepository.GetDriverBy_Phone(Phone.trim(), pageable);
        List<DriverInfoResponse> infoResponses = new ArrayList<>();

        driverEntities.forEach(DriverEntity -> {
            DriverInfoResponse infoResponse1 = new DriverInfoResponse();

            infoResponse1.setStatus(DriverEntity.getStatus());
            infoResponse1.setYearExperience(DriverEntity.getYear_Experience());
            infoResponse1.setPhone(DriverEntity.getAccountEntity().getPhone());
            infoResponse1.setIdentity_Number(DriverEntity.getAccountEntity().getIdentity_Number());
            infoResponse1.setFullName(DriverEntity.getAccountEntity().getFullName());
            infoResponse1.setName_License(DriverEntity.getLicenseTypeEntity().getName_License());
            infoResponse1.setUsername(DriverEntity.getAccountEntity().getUsername());

            infoResponses.add(infoResponse1);

        });
        PagingDriver pagingDriver1 = new PagingDriver();
        pagingDriver1.setTotalPage(driverEntities1.size() / 5 + 1);
        pagingDriver1.setNumberPage(p + 1);
        pagingDriver1.setDriverInfoResponsesList(infoResponses);


        if (infoResponses.isEmpty()) {
            messes.setMess("NOT FOUND ! ");
            return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);

        } else {
            return new ResponseEntity<>(pagingDriver1, HttpStatus.OK);
        }
    }

    public List<DriverEntity> driverEntityList() {
        return driverRepository.findAll();
    }

    @RequestMapping(value = "/driver/Search_CMT", method = RequestMethod.GET)
    public ResponseEntity<?> Find_By_CMT(@RequestParam(required = false) String cmt, Integer p) {


        if (p == null) {
            p = 0;
        } else if (p > 0) {
            p = p - 1;
        } else if (cmt == null) {
            cmt = "";
        }
        Pageable pageable = PageRequest.of(p, 5);
        PagingDriver pagingDriver_Search_cmt = new PagingDriver();
        ReposMesses messes = new ReposMesses();

        List<DriverInfoResponse> infoResponses = new ArrayList<>();
        List<DriverEntity> driverEntities = driverRepository.GetDriverBy_Identity(cmt.trim(), pageable);
        List<DriverEntity> driverEntities1 = driverRepository.GetDriverBy_Identity1(cmt.trim());

        driverEntities.forEach(DriverEntity -> {
            DriverInfoResponse infoResponse = new DriverInfoResponse();


            infoResponse.setIdentity_Number(DriverEntity.getAccountEntity().getIdentity_Number());
            infoResponse.setYearExperience(DriverEntity.getYear_Experience());
            infoResponse.setStatus(DriverEntity.getStatus());
            infoResponse.setPhone(DriverEntity.getAccountEntity().getPhone());
            infoResponse.setFullName(DriverEntity.getAccountEntity().getFullName());
            infoResponse.setName_License(DriverEntity.getLicenseTypeEntity().getName_License());
            infoResponse.setUsername(DriverEntity.getAccountEntity().getUsername());
            infoResponse.setIdentity_Number(DriverEntity.getAccountEntity().getIdentity_Number());

            infoResponses.add(infoResponse);

        });
        int more_size = 1;
        pagingDriver_Search_cmt.setTotalPage(driverEntities1.size() / 5 + more_size);
        pagingDriver_Search_cmt.setDriverInfoResponsesList(infoResponses);
        pagingDriver_Search_cmt.setNumberPage(p + 1);


        if (driverEntities1.isEmpty()) {
            messes.setMess("NOT FOUND ! ");
            return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(pagingDriver_Search_cmt, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/driver/List_license", method = RequestMethod.GET)
    public ResponseEntity<?> licenseTypeEntities() {
        ReposMesses messes = new ReposMesses();
        List<LicenseTypeEntity> licenseTypeEntities = licenseRepository.findAll();
        List<LicenseInfoResponse> licenseInfoResponses = new ArrayList<>();
        licenseTypeEntities.forEach(LicenseTypeEntity -> {
            LicenseInfoResponse licenseTypeEntity = new LicenseInfoResponse();
            licenseTypeEntity.setName_license(LicenseTypeEntity.getName_License());
            licenseTypeEntity.setDescription(LicenseTypeEntity.getDescription());
            licenseInfoResponses.add(licenseTypeEntity);

        });
        if (licenseInfoResponses.isEmpty()) {
            messes.setMess("K có dữ liệu trong bảng license !");
            return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(licenseInfoResponses, HttpStatus.OK);
        }

    }


    @RequestMapping(value = "/driver/ListDriverDemo", method = RequestMethod.GET)
    public ResponseEntity<?> GetListDriver(Long id) {
        List<DriverEntity> driverEntities = driverRepository.findAll();
        return new ResponseEntity<>(driverEntities, HttpStatus.OK);
    }


    @RequestMapping(value = "/driver/Change_Status", method = RequestMethod.GET)
    public ResponseEntity<?> Change_Status_Driver(@RequestParam(name = "username") String username) {
        ReposMesses reposMesses = new ReposMesses();
        DriverInfoDetailResponse driverInfoDetailResponse = new DriverInfoDetailResponse();
        DriverEntity driverEntities = driverRepository.GetByUsername(username.trim());
        if (driverEntities != null && driverEntities.getStatus() == 1 && driverEntities.getAccountEntity().getStatus() == 1) {
            driverEntities.getAccountEntity().setStatus(0);
            driverEntities.setStatus(0);
            driverRepository.save(driverEntities);
            driverInfoDetailResponse = driverService.driverInfoResponses(driverEntities, driverInfoDetailResponse);

        } else if (driverEntities != null && driverEntities.getStatus() == 0 && driverEntities.getAccountEntity().getStatus() == 0) {
            driverEntities.getAccountEntity().setStatus(1);
            driverEntities.setStatus(1);
            driverRepository.save(driverEntities);
            driverInfoDetailResponse = driverService.driverInfoResponses(driverEntities, driverInfoDetailResponse);
        } else {
            reposMesses.setMess("Thay đổi trạng thái thất bại ! ");
            return new ResponseEntity<>(reposMesses, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(driverInfoDetailResponse, HttpStatus.OK);
    }


    @RequestMapping(value = "/driver/Create", method = RequestMethod.POST)
    public ResponseEntity<?> Create(@RequestBody DriverInfoRequest infoRequest) {
        ReposMesses messes = new ReposMesses();
        Date date = new Date(System.currentTimeMillis());
        String regexPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

        DriverEntity driver = new DriverEntity();
        AccountEntity accountEntity = new AccountEntity();
        DistrictsEntity districtsEntity = new DistrictsEntity();
        RoleEntity roleEntity = roleRepository.GetRoleDriver();


        if (!accountRepository.Check_email(infoRequest.getEmail()).isEmpty()) {
            messes.setMess("Email đã tồn tại  !");
            return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
        } else if (!accountRepository.Check_username(infoRequest.getUsername()).isEmpty()) {
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
        } else if (!driverRepository.Check_diver_number_license(infoRequest.getDriver_Number_License().trim()).isEmpty()) {
            messes.setMess("Số Bằng lái xe đã được đăng kí trong hệ thống  !");
            return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
        }

        accountEntity.setFullName(infoRequest.getFullName().trim());
        accountEntity.setEmail(infoRequest.getEmail().trim());
        accountEntity.setUsername(infoRequest.getUsername().trim());
        accountEntity.setGender(infoRequest.getGender());
        accountEntity.setDOB(infoRequest.getDob());
        accountEntity.setIdentity_Number(infoRequest.getIdentity_Number().trim());
        accountEntity.setIdentity_Picture_Back(infoRequest.getIdentity_Picture_Back().trim());
        accountEntity.setIdentity_Picture_Front(infoRequest.getIdentity_Picture_Front().trim());
        accountEntity.setAddress(infoRequest.getAddress());
        accountEntity.setCreateDate(date);
        accountEntity.setImg(infoRequest.getImg());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(infoRequest.getPassword());
        accountEntity.setPassword(encodedPassword.trim());

        accountEntity.setPhone(infoRequest.getPhone().trim());
        accountEntity.setStatus(1);
        accountEntity.setRoleEntity(roleEntity);

        String city = infoRequest.getCity().trim();
        String district_name = infoRequest.getDistrict_Name().trim();
        String wards = infoRequest.getWards().trim();


        List<DistrictsEntity> districtsEntities = districtRepository.check_district(city, wards, district_name);


        if (districtsEntities.isEmpty()) {
            districtsEntity.setCity(city);
            districtsEntity.setDistrict_Name(district_name);
            districtsEntity.setWards(wards);
            accountEntity.setDistrictsEntity(districtsEntity);

            districtRepository.save(districtsEntity);

        } else {

            DistrictsEntity districts = districtRepository.check_districts(city, wards, district_name);
            accountEntity.setDistrictsEntity(districts);

        }

        LicenseTypeEntity licenseTypeEntity;
        licenseTypeEntity = licenseRepository.Get_License_By_Id(infoRequest.getName_License());

        driver.setAccountEntity(accountEntity);
        driver.setLicenseTypeEntity(licenseTypeEntity);
        driver.setDriver_Number_License(infoRequest.getDriver_Number_License());
        driver.setDriving_license_image_Front(infoRequest.getDriving_license_image_Front());
        driver.setDriving_license_image_back(infoRequest.getDriving_license_image_back());
        driver.setYear_Experience(infoRequest.getYearExperience());
        driver.setStatus(1);


        driver.setStart_Date(date);

        accountRepository.save(accountEntity);
        DriverEntity driverEntity = driverRepository.save(driver);

        return new ResponseEntity<>(driverEntity, HttpStatus.OK);
    }


}



