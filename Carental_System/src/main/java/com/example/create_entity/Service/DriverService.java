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


    public List<DriverInfoResponse> responseEntity(Page<DriverEntity> driverInfoResponses) {

        List<DriverInfoResponse> infoResponses = new ArrayList<>();

        driverInfoResponses.forEach(DriverEntity -> {
            DriverInfoResponse infoResponse = new DriverInfoResponse();
            infoResponse.setName_License(DriverEntity.getLicenseTypeEntity().getName_License());
            infoResponse.setStatus(DriverEntity.getAccountEntity().getStatus());
            infoResponse.setPhone(DriverEntity.getPhone());
            infoResponse.setIdentity_Number(DriverEntity.getIdentity_Number());
            infoResponse.setFullName(DriverEntity.getFullName());
            infoResponse.setUsername(DriverEntity.getAccountEntity().getUsername());
            infoResponse.setYearExperience(DriverEntity.getYear_Experience());


            infoResponses.add(infoResponse);


        });
        return infoResponses;
    }

    public ResponseEntity<?> SearchByName(String name, Integer p) {
        if (p == null) {
            p = 0;
        } else if (p > 0) {
            p = p - 1;
        }
        ReposMesses messes = new ReposMesses();
        try {
            Pageable pageable = PageRequest.of(p, 5);
            Page<DriverEntity> driverEntities = driverRepository.GetDriverBy_fullName(name, pageable);
            PagingDriver pagingDriver = new PagingDriver();
            List<DriverInfoResponse> infoResponses = this.responseEntity(driverEntities);
            pagingDriver.setDriverInfoResponsesList(infoResponses);
            pagingDriver.setTotalPage(driverEntities.getTotalPages());
            pagingDriver.setNumberPage(driverEntities.getNumber() + 1);
            if (infoResponses.isEmpty()) {
                messes.setMess("k tìm thấy tên ! ");
                return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(pagingDriver, HttpStatus.OK);
            }
        } catch (Exception e) {
            messes.setMess(e.getMessage());
            return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
        }
    }


    public ResponseEntity<?> ManagerDriver(Integer p) {
        if (p == null) {
            p = 0;
        } else if (p > 0) {
            p = p - 1;
        }
        ReposMesses messes = new ReposMesses();
        try {
            Pageable pageable = PageRequest.of(p, 5);
            Page<DriverEntity> page = driverRepository.findAll(pageable);
            List<DriverInfoResponse> driverInfoResponses = new ArrayList<>();
            page.forEach(DriverEntity -> {
                DriverInfoResponse infoResponse = new DriverInfoResponse();
                infoResponse.setName_License(DriverEntity.getLicenseTypeEntity().getName_License());
                infoResponse.setYearExperience(DriverEntity.getYear_Experience());
                infoResponse.setStatus(DriverEntity.getAccountEntity().getStatus());
                infoResponse.setPhone(DriverEntity.getPhone());
                infoResponse.setIdentity_Number(DriverEntity.getIdentity_Number());
                infoResponse.setFullName(DriverEntity.getFullName());
                infoResponse.setUsername(DriverEntity.getAccountEntity().getUsername());
                driverInfoResponses.add(infoResponse);
            });
            PagingDriver driverPagingDriver = new PagingDriver();
            driverPagingDriver.setDriverInfoResponsesList(driverInfoResponses);
            driverPagingDriver.setTotalPage(page.getTotalPages());
            driverPagingDriver.setNumberPage(page.getNumber() + 1);
            if (page.isEmpty()) {
                messes.setMess("K có dữ liệu ! ");
                return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(driverPagingDriver, HttpStatus.OK);
        } catch (Exception e) {
            messes.setMess(e.getMessage());
            return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
        }
    }


    @Transactional
    public ResponseEntity<?> Create(DriverInfoRequest infoRequest) {
        ReposMesses messes = null;
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        try {
            messes = new ReposMesses();
            Date date = new Date(System.currentTimeMillis());
            String regexPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

            DriverEntity driver = new DriverEntity();
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
            } else if (!driverRepository.Check_Phone(infoRequest.getPhone().trim()).isEmpty()) {
                messes.setMess("Số điện thoại đã được đăng kí trong hệ thống  !");
                return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
            } else if (!driverRepository.Check_Identity(infoRequest.getIdentity_Number().trim()).isEmpty()) {
                messes.setMess("Số Chứng minh thư đã được đăng kí trong hệ thống !");
                return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
            } else {
                try {
                    AccountEntity accountEntity = new AccountEntity();
                    String encodedPassword = passwordEncoder.encode(infoRequest.getPassword());
                    accountEntity.setPassword(encodedPassword.trim());
                    accountEntity.setEmail(infoRequest.getEmail().trim());
                    accountEntity.setUsername(infoRequest.getUsername().trim());
                    accountEntity.setCreateDate(date);
                    accountEntity.setModifiedDate(date);
                    accountEntity.setRoleEntity(roleEntity);
                    accountEntity.setStatus(2);
                    accountRepository.save(accountEntity);


                    driver.setFullName(infoRequest.getFullName().trim());
                    driver.setGender(infoRequest.getGender());
                    driver.setDOB(infoRequest.getDob());
                    driver.setIdentity_Number(infoRequest.getIdentity_Number().trim());
                    driver.setIdentity_Picture_Back(infoRequest.getIdentity_Picture_Back().trim());
                    driver.setIdentity_Picture_Front(infoRequest.getIdentity_Picture_Front().trim());
                    driver.setAddress(infoRequest.getAddress());
                    driver.setImg(infoRequest.getImg());
                    driver.setPhone(infoRequest.getPhone().trim());
                    driver.setModifiedDate(date);


                    String city = infoRequest.getCity().trim();
                    String district_name = infoRequest.getDistrict_Name().trim();
                    String wards = infoRequest.getWards().trim();


                    List<DistrictsEntity> districtsEntities = districtRepository.check_district(city, wards, district_name);

                    if (districtsEntities.isEmpty()) {
                        districtsEntity.setCity(city);
                        districtsEntity.setDistrict_Name(district_name);
                        districtsEntity.setWards(wards);
                        driver.setDistrictsEntity(districtsEntity);
                        districtRepository.save(districtsEntity);
                    } else {
                        DistrictsEntity districts = districtRepository.check_districts(city, wards, district_name);
                        driver.setDistrictsEntity(districts);
                    }

                    LicenseTypeEntity licenseTypeEntity;
                    licenseTypeEntity = licenseRepository.Get_License_By_Name(infoRequest.getName_License());

                    driver.setAccountEntity(accountEntity);
                    driver.setLicenseTypeEntity(licenseTypeEntity);
                    driver.setDriver_Number_License(infoRequest.getDriver_Number_License());
                    driver.setDriving_license_image_Front(infoRequest.getDriving_license_image_Front());
                    driver.setDriving_license_image_back(infoRequest.getDriving_license_image_back());
                    driver.setYear_Experience(infoRequest.getYearExperience());
                    driver.setStatus(1);
                    driver.setContractDetailEntityList(null);
                    driver.setStart_Date(date);

                    driverRepository.save(driver);
                    messes.setMess("Đã Tạo Thành Công ! ");
                    return new ResponseEntity<>(messes, HttpStatus.OK);
                } catch (Exception e) {
                    messes.setMess(e.getMessage());
                    return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception e) {
            messes.setMess(e.getMessage());
            return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> DriverDetail(String username) {
        ReposMesses messes = new ReposMesses();
//        if (username.equals(null)) {
//            username = "";
//        }
        try {
            DriverEntity driverEntities = driverRepository.GetByUsername(username.trim());
            if (driverEntities == null) {
                messes.setMess("Error ! ");
                return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
            } else {
                DriverInfoDetailResponse driverInfoDetailResponse = new DriverInfoDetailResponse();
                driverInfoDetailResponse = driverInfoDetailResponse.driverInfoResponses(driverEntities);
                return new ResponseEntity<>(driverInfoDetailResponse, HttpStatus.OK);
            }
        } catch (Exception e) {
            messes.setMess(e.getMessage());
            return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> Find_By_Phone(String Phone, Integer p) {

        if (p == null) {
            p = 0;
        } else if (p > 0) {
            p = p - 1;
        }
        ReposMesses messes = new ReposMesses();
        try {
            Pageable pageable = PageRequest.of(p, 5);
            Page<DriverEntity> driverEntities = driverRepository.GetDriverBy_Phone(Phone,pageable);
            PagingDriver pagingDriver = new PagingDriver();
            List<DriverInfoResponse> infoResponses = this.responseEntity(driverEntities);
            pagingDriver.setDriverInfoResponsesList(infoResponses);
            pagingDriver.setTotalPage(driverEntities.getTotalPages());
            pagingDriver.setNumberPage(driverEntities.getNumber() + 1);
            if (infoResponses.isEmpty()) {
                messes.setMess("k tìm thấy dữ liệu  ! ");
                return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(pagingDriver, HttpStatus.OK);
            }
        } catch (Exception e) {
            messes.setMess(e.getMessage());
            return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> Find_By_cmt(String cmt, Integer p) {

        if (p == null) {
            p = 0;
        } else if (p > 0) {
            p = p - 1;
        }
        ReposMesses messes = new ReposMesses();
        try {
            Pageable pageable = PageRequest.of(p, 5);
            Page<DriverEntity> driverEntities = driverRepository.GetDriverBy_Identity(cmt,pageable);
            PagingDriver pagingDriver = new PagingDriver();
            List<DriverInfoResponse> infoResponses = this.responseEntity(driverEntities);
            pagingDriver.setDriverInfoResponsesList(infoResponses);
            pagingDriver.setTotalPage(driverEntities.getTotalPages());
            pagingDriver.setNumberPage(driverEntities.getNumber() + 1);
            if (infoResponses.isEmpty()) {
                messes.setMess("k tìm thấy dữ liệu ! ");
                return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(pagingDriver, HttpStatus.OK);
            }
        } catch (Exception e) {
            messes.setMess(e.getMessage());
            return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> licenseTypeEntities() {
        ReposMesses messes = new ReposMesses();
        try {
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
        }catch (Exception e) {
            messes.setMess(e.getMessage());
            return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> Change_Status_Driver(String username) {
        ReposMesses reposMesses = new ReposMesses();
        try {
            DriverInfoDetailResponse driverInfoDetailResponse = new DriverInfoDetailResponse();
            DriverEntity driverEntities = driverRepository.GetByUsername(username.trim());
            if (driverEntities != null && driverEntities.getStatus() == 1 && driverEntities.getAccountEntity().getStatus() == 2) {
                driverEntities.getAccountEntity().setStatus(0);
                driverEntities.setStatus(0);
                Date date = new Date(System.currentTimeMillis());
                driverEntities.setModifiedDate(date);
                driverRepository.save(driverEntities);
                driverInfoDetailResponse = driverInfoDetailResponse.driverInfoResponses(driverEntities);

            } else if (driverEntities != null && driverEntities.getStatus() == 0 && driverEntities.getAccountEntity().getStatus() == 0) {
                driverEntities.getAccountEntity().setStatus(2);
                driverEntities.setStatus(1);
                Date date = new Date(System.currentTimeMillis());
                driverEntities.setModifiedDate(date);
                driverRepository.save(driverEntities);
                driverInfoDetailResponse = driverInfoDetailResponse.driverInfoResponses(driverEntities);
            } else {
                reposMesses.setMess("Thay đổi trạng thái thất bại ! ");
                return new ResponseEntity<>(reposMesses, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(driverInfoDetailResponse, HttpStatus.OK);
        }catch (Exception e){
            reposMesses.setMess(e.getMessage());
            return new ResponseEntity<>(reposMesses, HttpStatus.BAD_REQUEST);
        }
    }
}
