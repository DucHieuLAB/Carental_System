package com.example.create_entity.Controller;

import com.example.create_entity.Entity.*;
import com.example.create_entity.Repository.*;
import com.example.create_entity.dto.Request.DriverInfoRequest;
import com.example.create_entity.dto.Response.ReposMesses;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    RoleRepository roleRepository;

    @Autowired
    DistrictRepository districtRepository;


    @RequestMapping(value = "/driver/ListDriver", method = RequestMethod.GET)
    public ResponseEntity<?> ManagerDriver() {
        List<DriverEntity> driverEntities = driverRepository.findAll();
        List<DriverInfoRequest> driverInfoRequests = new ArrayList<>();


        driverEntities.forEach(DriverEntity -> {
            DriverInfoRequest infoRequest = new DriverInfoRequest();

            infoRequest.setName_License(DriverEntity.getLicenseTypeEntity().getName_License());
            infoRequest.setYearExperience(DriverEntity.getYear_Experience());
            infoRequest.setStatus(DriverEntity.getStatus());
            infoRequest.setPhone(DriverEntity.getAccountEntity().getPhone());
            infoRequest.setIdentity_Number(DriverEntity.getAccountEntity().getIdentity_Number());
            infoRequest.setFullName(DriverEntity.getAccountEntity().getFullName());
            driverInfoRequests.add(infoRequest);


        });


        if (!driverInfoRequests.isEmpty()) {
            return new ResponseEntity<>(driverInfoRequests, HttpStatus.OK);
        } else {
            ReposMesses messes = new ReposMesses();
            messes.setMess("NOT DATA! ");
            return new ResponseEntity<>(messes, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/driver/Search_name", method = RequestMethod.POST)
    public ResponseEntity<?> Find_By_Name(@RequestParam String name) {
        ReposMesses messes = new ReposMesses();
        List<DriverEntity> driverEntities = driverRepository.findAll();
        List<DriverInfoRequest> infoRequest = new ArrayList<>();
        driverEntities.forEach(DriverEntity -> {
            DriverInfoRequest infoRequest1 = new DriverInfoRequest();
            if (DriverEntity.getAccountEntity().getFullName().toLowerCase().trim().contains(name.trim().toLowerCase())) {

                infoRequest1.setYearExperience(DriverEntity.getYear_Experience());
                infoRequest1.setStatus(DriverEntity.getStatus());
                infoRequest1.setPhone(DriverEntity.getAccountEntity().getPhone());
                infoRequest1.setIdentity_Number(DriverEntity.getAccountEntity().getIdentity_Number());
                infoRequest1.setFullName(DriverEntity.getAccountEntity().getFullName());
                infoRequest1.setName_License(DriverEntity.getLicenseTypeEntity().getName_License());

                infoRequest.add(infoRequest1);
            }
        });

        if (!infoRequest.isEmpty()) {
            return new ResponseEntity<>(infoRequest, HttpStatus.OK);
        } else {
            messes.setMess("NOT FOUND ! ");
            return new ResponseEntity<>(messes, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/driver/Search_Phone", method = RequestMethod.POST)
    public ResponseEntity<?> Find_By_Phone(@RequestParam String Phone) {

        List<DriverEntity> driverEntities = driverRepository.findAll();
        List<DriverInfoRequest> infoRequest = new ArrayList<>();

        driverEntities.forEach(DriverEntity -> {
            DriverInfoRequest infoRequest1 = new DriverInfoRequest();

            if (DriverEntity.getAccountEntity().getPhone().trim().contains(Phone.trim())) {

                infoRequest1.setStatus(DriverEntity.getStatus());
                infoRequest1.setYearExperience(DriverEntity.getYear_Experience());
                infoRequest1.setPhone(DriverEntity.getAccountEntity().getPhone());
                infoRequest1.setIdentity_Number(DriverEntity.getAccountEntity().getIdentity_Number());
                infoRequest1.setFullName(DriverEntity.getAccountEntity().getFullName());
                infoRequest1.setName_License(DriverEntity.getLicenseTypeEntity().getName_License());

                infoRequest.add(infoRequest1);
            }
        });

        if (!infoRequest.isEmpty()) {
            return new ResponseEntity<>(infoRequest, HttpStatus.OK);
        } else {
            ReposMesses messes = new ReposMesses();
            messes.setMess("NOT FOUND ! ");
            return new ResponseEntity<>(messes, HttpStatus.OK);
        }
    }

    public List<DriverEntity> driverEntityList() {
        return driverRepository.findAll();
    }

    @RequestMapping(value = "/driver/Search_cmt", method = RequestMethod.POST)
    public ResponseEntity<?> Find_By_CMT(@RequestParam String cmt) {
        List<DriverInfoRequest> infoRequest = new ArrayList<>();
        List<DriverEntity> driverEntities = driverEntityList();


        driverEntities.forEach(DriverEntity -> {
            DriverInfoRequest infoRequest1 = new DriverInfoRequest();

            if (DriverEntity.getAccountEntity().getIdentity_Number().trim().contains(cmt.trim())) {
                infoRequest1.setIdentity_Number(DriverEntity.getAccountEntity().getIdentity_Number());
                infoRequest1.setYearExperience(DriverEntity.getYear_Experience());
                infoRequest1.setStatus(DriverEntity.getStatus());
                infoRequest1.setPhone(DriverEntity.getAccountEntity().getPhone());
                infoRequest1.setFullName(DriverEntity.getAccountEntity().getFullName());
                infoRequest1.setName_License(DriverEntity.getLicenseTypeEntity().getName_License());

                infoRequest.add(infoRequest1);
            }
        });

        if (!infoRequest.isEmpty()) {
            return new ResponseEntity<>(infoRequest, HttpStatus.OK);
        } else {
            ReposMesses messes = new ReposMesses();
            messes.setMess("NOT FOUND ! ");
            return new ResponseEntity<>(messes, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/driver/ListDriverDemo", method = RequestMethod.GET)
    public ResponseEntity<?> GetListDriver(Long id) {
        List<DriverEntity> driverEntities = driverRepository.findAll();
        return new ResponseEntity<>(driverEntities, HttpStatus.OK);
    }

    @RequestMapping(value = "/driver/Delete_Status", method = RequestMethod.POST)
    public ResponseEntity<?> Change_Status_Driver(Long id) {
        ReposMesses reposMesses = new ReposMesses();
        DriverEntity driverEntities = driverRepository.GetDriverById(id);
        if (driverEntities != null) {
            driverEntities.getAccountEntity().setStatus(0);
            driverEntities.setStatus(0);
        } else {
            reposMesses.setMess("Delete Driver Fail ! ");
            return new ResponseEntity<>(reposMesses, HttpStatus.OK);
        }

        return new ResponseEntity<>(driverEntities, HttpStatus.OK);
    }

    public ResponseEntity<?> LoadDataDriver() {


        List<DriverInfoRequest> driverInfoRequests = new ArrayList<>();

        List<DriverEntity> driverEntities = driverRepository.findAll();

        driverEntities.forEach(DriverEntity -> {
            DriverInfoRequest infoRequest = new DriverInfoRequest();

            infoRequest.setName_License(DriverEntity.getLicenseTypeEntity().getName_License());
            infoRequest.setYearExperience(DriverEntity.getYear_Experience());
            infoRequest.setStatus(DriverEntity.getStatus());
            infoRequest.setIdentity_Number(DriverEntity.getAccountEntity().getIdentity_Number());
            infoRequest.setFullName(DriverEntity.getAccountEntity().getFullName());
            infoRequest.setPhone(DriverEntity.getAccountEntity().getPhone());

            driverInfoRequests.add(infoRequest);


        });
        return new ResponseEntity<>(driverInfoRequests, HttpStatus.OK);
    }

//    @RequestMapping(value = "/driver/Delete", method = RequestMethod.POST)
//    public ResponseEntity<?> Delete(@RequestParam(name = "id") Long id) {
//
//    }

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
            messes.setMess("Email is not valid !");
            return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
        } else if (!accountRepository.Check_username(infoRequest.getUsername()).isEmpty()) {
            messes.setMess("UserName is not valid !");
            return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
        } else if (!infoRequest.getEmail().matches(regexPattern)) {
            messes.setMess("Email wrong Format !");
            return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
        } else {

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

//    @RequestMapping(value = "/driver/aaa", method = RequestMethod.GET)
//    public List<DistrictsEntity> response() {
//        List<DistrictsEntity> districtsEntities = districtRepository.check_district("HUNGYEN", "NGHIATRU", "VANGIANG");
//        return districtsEntities;
//    }


}



