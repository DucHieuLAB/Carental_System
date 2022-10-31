package com.example.create_entity.Service;

import com.example.create_entity.Entity.*;
import com.example.create_entity.Repository.*;
import com.example.create_entity.dto.Request.DriverInfoRequest;
import com.example.create_entity.dto.Response.PagingDriver;
import com.example.create_entity.dto.Response.DriverInfoDetailResponse;
import com.example.create_entity.dto.Response.DriverInfoResponse;
import com.example.create_entity.dto.Response.LicenseInfoResponse;
import com.example.create_entity.dto.Response.ReposMesses;
import lombok.NoArgsConstructor;
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

@NoArgsConstructor
@Service
public class DriverService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    DriverRepository driverRepository;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    DistrictRepository districtRepository;
    @Autowired
    LicenseRepository licenseRepository;




    public List<DriverInfoResponse> responseEntity(List<DriverEntity> driverInfoResponses) {

        List<DriverInfoResponse> infoResponses = new ArrayList<>();

        driverInfoResponses.forEach(DriverEntity -> {
            DriverInfoResponse infoResponse = new DriverInfoResponse();
            infoResponse.setName_License(DriverEntity.getLicenseTypeEntity().getName_License());
            infoResponse.setStatus(DriverEntity.getAccountEntity().getStatus());
            infoResponse.setPhone(DriverEntity.getAccountEntity().getPhone());
            infoResponse.setIdentity_Number(DriverEntity.getAccountEntity().getIdentity_Number());
            infoResponse.setFullName(DriverEntity.getAccountEntity().getFullName());
            infoResponse.setUsername(DriverEntity.getAccountEntity().getUsername());
            infoResponse.setYearExperience(DriverEntity.getYear_Experience());


            infoResponses.add(infoResponse);


        });
        return infoResponses;
    }

    public ResponseEntity<?> SearchByName(String name, Integer p) {
        ReposMesses messes = new ReposMesses();

        if (p == null) {
            p = 0;
        } else if (p > 0) {
            p = p - 1;
        } else if (name == null) {
            name = "";
        }

        Pageable pageable = PageRequest.of(p, 5);
        List<DriverEntity> driverEntities = driverRepository.GetDriverBy_fullName(name.trim(), pageable);

        List<DriverEntity> driverEntities1 = driverRepository.GetDriverBy_fullName1(name.trim());

        List<DriverInfoResponse> infoResponses = this.responseEntity(driverEntities);
        PagingDriver pagingDriver = new PagingDriver();
        pagingDriver.setDriverInfoResponsesList(infoResponses);

        if (driverEntities1.size() % 5 == 0) {
            pagingDriver.setTotalPage(driverEntities1.size() / 5);
        } else {
            pagingDriver.setTotalPage(driverEntities1.size() / 5 + 1);
        }
        pagingDriver.setNumberPage(p + 1);


        if (infoResponses.isEmpty()) {
            messes.setMess("k tìm thấy tên ! ");
            return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);

        } else {
            return new ResponseEntity<>(pagingDriver, HttpStatus.OK);
        }

    }

    public ResponseEntity<?> DriverDetail(String username) {

        ReposMesses messes = new ReposMesses();
        if (username.equals(null)) {
            username = "";
        }
        DriverEntity driverEntities = driverRepository.GetByUsername(username.trim());

        if(driverEntities==null){
            messes.setMess("Error ! ");
            return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
        }else {
            DriverInfoDetailResponse driverInfoDetailResponse = new DriverInfoDetailResponse();
            driverInfoDetailResponse = driverInfoDetailResponse.driverInfoResponses(driverEntities, driverInfoDetailResponse);
            return new ResponseEntity<>(driverInfoDetailResponse, HttpStatus.OK);
        }
    }

    public ResponseEntity<?> Find_By_Phone(String Phone, Integer p) {

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
        List<DriverInfoResponse> infoResponses = this.responseEntity(driverEntities);

        PagingDriver pagingDriver1 = new PagingDriver();

        if (driverEntities1.size() % 5 == 0) {
            pagingDriver1.setTotalPage(driverEntities1.size() / 5);
        } else {
            pagingDriver1.setTotalPage(driverEntities1.size() / 5 + 1);
        }

        pagingDriver1.setNumberPage(p + 1);
        pagingDriver1.setDriverInfoResponsesList(infoResponses);


        if (infoResponses.isEmpty()) {
            messes.setMess("Số điện thoại k tồn tại trong hệ thống ! ");
            return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);

        } else {
            return new ResponseEntity<>(pagingDriver1, HttpStatus.OK);
        }
    }

    public ResponseEntity<?> Find_By_cmt(String cmt, Integer p) {

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


        List<DriverEntity> driverEntities = driverRepository.GetDriverBy_Identity(cmt.trim(), pageable);
        List<DriverEntity> driverEntities1 = driverRepository.GetDriverBy_Identity1(cmt.trim());
        List<DriverInfoResponse> infoResponses = this.responseEntity(driverEntities);

        if (driverEntities1.size() % 5 == 0) {
            pagingDriver_Search_cmt.setTotalPage(driverEntities1.size() / 5);
        } else {
            pagingDriver_Search_cmt.setTotalPage(driverEntities1.size() / 5 + 1);
        }

        pagingDriver_Search_cmt.setDriverInfoResponsesList(infoResponses);
        pagingDriver_Search_cmt.setNumberPage(p + 1);


        if (driverEntities1.isEmpty()) {
            messes.setMess("NOT FOUND ! ");
            return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(pagingDriver_Search_cmt, HttpStatus.OK);
        }
    }

    public ResponseEntity<?> licenseTypeEntities() {
        ReposMesses messes = new ReposMesses();
        List<LicenseTypeEntity> licenseTypeEntities = licenseRepository.findAll();
        List<LicenseInfoResponse> licenseInfoResponses = new ArrayList<>();
        licenseTypeEntities.forEach(LicenseTypeEntity -> {
            LicenseInfoResponse licenseTypeEntity = new LicenseInfoResponse();
            licenseTypeEntity.setID(LicenseTypeEntity.getID());
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

    public ResponseEntity<?> Change_Status_Driver(String username) {
        ReposMesses reposMesses = new ReposMesses();
        DriverInfoDetailResponse driverInfoDetailResponse = new DriverInfoDetailResponse();
        DriverEntity driverEntities = driverRepository.GetByUsername(username.trim());
        if (driverEntities != null && driverEntities.getStatus() == 1 && driverEntities.getAccountEntity().getStatus() == 2) {
            driverEntities.getAccountEntity().setStatus(0);
            driverEntities.setStatus(0);
            driverRepository.save(driverEntities);
            driverInfoDetailResponse = driverInfoDetailResponse.driverInfoResponses(driverEntities, driverInfoDetailResponse);

        } else if (driverEntities != null && driverEntities.getStatus() == 0 && driverEntities.getAccountEntity().getStatus() == 0) {
            driverEntities.getAccountEntity().setStatus(2);
            driverEntities.setStatus(1);
            driverRepository.save(driverEntities);
            driverInfoDetailResponse = driverInfoDetailResponse.driverInfoResponses(driverEntities, driverInfoDetailResponse);
        } else {
            reposMesses.setMess("Thay đổi trạng thái thất bại ! ");
            return new ResponseEntity<>(reposMesses, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(driverInfoDetailResponse, HttpStatus.OK);
    }

    public ResponseEntity<?> ManagerDriver(Integer p) {


        if (p == null) {
            p = 0;
        } else if (p > 0) {
            p = p - 1;
        }
        Pageable pageable = PageRequest.of(p, 5);

        Page<DriverEntity> page = driverRepository.findAll(pageable);


        List<DriverInfoResponse> driverInfoResponses = new ArrayList<>();
        page.forEach(DriverEntity -> {
            DriverInfoResponse infoResponse = new DriverInfoResponse();
            infoResponse.setName_License(DriverEntity.getLicenseTypeEntity().getName_License());
            infoResponse.setYearExperience(DriverEntity.getYear_Experience());
            infoResponse.setStatus(DriverEntity.getAccountEntity().getStatus());
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
            messes.setMess("K có dữ liệu ! ");
            return new ResponseEntity<>(messes, HttpStatus.OK);
        }
        return new ResponseEntity<>(driverPagingDriver, HttpStatus.OK);

    }


    @Transactional
    public ResponseEntity<?> Create(DriverInfoRequest infoRequest) {
        ReposMesses messes = null;
        try {
            messes = new ReposMesses();
            Date date = new Date(System.currentTimeMillis());
            String regexPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

            DriverEntity driver = new DriverEntity();
            AccountEntity accountEntity = new AccountEntity();
            DistrictsEntity districtsEntity = new DistrictsEntity();
            RoleEntity roleEntity = roleRepository.GetRoleDriver("Driver");


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

            accountEntity.setModifiedDate(date);
            accountEntity.setImg(infoRequest.getImg());

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(infoRequest.getPassword());
            accountEntity.setPassword(encodedPassword.trim());

            accountEntity.setPhone(infoRequest.getPhone().trim());
            accountEntity.setStatus(2);
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
            licenseTypeEntity = licenseRepository.Get_License_By_Name(infoRequest.getName_License());
//        if (licenseTypeEntity.equals(null)) {
//            messes.setMess("K tồn tại List LicenseType !");
//            return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
//        }

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

            messes.setMess("Đã Tạo Thành Công ! ");
            return new ResponseEntity<>(messes, HttpStatus.OK);
        } catch (Exception e) {
            messes.setMess(e.getMessage());
            return new ResponseEntity<>(messes, HttpStatus.OK);
        }
    }


}
