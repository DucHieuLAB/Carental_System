package com.example.crms_g8.Service.ServiceImpl;

import com.example.crms_g8.Entity.*;
import com.example.crms_g8.Repository.*;
import com.example.crms_g8.dto.Request.DriverInfoRequest;
import com.example.crms_g8.dto.Response.*;
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

    public ResponseEntity<?>FilterByName(String name, Integer p) {
        if (p == null) {
            p = 0;
        } else if (p > 0) {
            p = p - 1;
        }
        ReposMesses messes = new ReposMesses();
        try {
            Pageable pageable = PageRequest.of(p, 5);
            Page<DriverEntity> driverEntities = driverRepository.SearchByName(name, pageable);
            PagingDriver pagingDriver = new PagingDriver();
            List<DriverInfoResponse> infoResponses = this.responseEntity(driverEntities);
            pagingDriver.setDriverInfoResponsesList(infoResponses);
            pagingDriver.setTotalPage(driverEntities.getTotalPages());
            pagingDriver.setNumberPage(driverEntities.getNumber() + 1);
            if (infoResponses.isEmpty()) {
                messes.setMess("Không tìm thấy dữ liệu yêu cầu  ! ");
                return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(pagingDriver, HttpStatus.OK);
            }
        } catch (Exception e) {
            messes.setMess(e.getMessage());
            return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
        }
    }


    public ResponseEntity<?>GetListDriver(Integer p) {
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
                messes.setMess("Không có dữ liệu trong bảng driver ! ");
                return new ResponseEntity<>(messes, HttpStatus.OK);
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


            if (!accountRepository.CheckEmail(infoRequest.getEmail()).isEmpty()) {
                messes.setMess("Email đã tồn tại trong hệ thống  !");
                return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
            } else if (!accountRepository.CheckUsername(infoRequest.getUsername()).isEmpty()) {
                messes.setMess("UserName đã tồn tại trong hệ thống !");
                return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
            } else if (!infoRequest.getEmail().matches(regexPattern)) {
                messes.setMess("Email k đúng định dạng trong hệ thống !");
                return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
            } else if (!driverRepository.Check_Phone(infoRequest.getPhone().trim()).isEmpty()) {
                messes.setMess("Số điện thoại đã được đăng kí trong hệ thống  !");
                return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
            } else if (!driverRepository.Check_Identity(infoRequest.getIdentity_Number().trim()).isEmpty()) {
                messes.setMess("Số Chứng minh thư đã được đăng kí trong hệ thống !");
                return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
            } else if (driverRepository.CheckNumberLicense(infoRequest.getDriver_Number_License().trim()) != null) {
                messes.setMess("Bằng lái xe đã được đăng kí trong hệ thống !");
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
                    messes.setMess("Đã Tạo Thành Công tài khoản ! ");
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

    public ResponseEntity<?>DetailDriver(String username) {
        ReposMesses messes = new ReposMesses();
//        if (username.equals(null)) {
//            username = "";
//        }
        try {
            DriverEntity driverEntities = driverRepository.GetDriverByUsername(username.trim());
            if (driverEntities == null) {
                messes.setMess("Đã xảy ra lỗi hệ thống !");
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

    public ResponseEntity<?>FilterByPhone(String Phone, Integer p) {

        if (p == null) {
            p = 0;
        } else if (p > 0) {
            p = p - 1;
        }
        ReposMesses messes = new ReposMesses();
        try {
            Pageable pageable = PageRequest.of(p, 5);
            Page<DriverEntity> driverEntities = driverRepository.GetDriverBy_Phone(Phone, pageable);
            PagingDriver pagingDriver = new PagingDriver();
            List<DriverInfoResponse> infoResponses = this.responseEntity(driverEntities);
            pagingDriver.setDriverInfoResponsesList(infoResponses);
            pagingDriver.setTotalPage(driverEntities.getTotalPages());
            pagingDriver.setNumberPage(driverEntities.getNumber() + 1);
            if (infoResponses.isEmpty()) {
                messes.setMess("Không tìm thấy dữ liệu yêu cầu   ! ");
                return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(pagingDriver, HttpStatus.OK);
            }
        } catch (Exception e) {
            messes.setMess(e.getMessage());
            return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?>FilterByIdentity(String cmt, Integer p) {

        if (p == null) {
            p = 0;
        } else if (p > 0) {
            p = p - 1;
        }
        ReposMesses messes = new ReposMesses();
        try {
            Pageable pageable = PageRequest.of(p, 5);
            Page<DriverEntity> driverEntities = driverRepository.GetDriverBy_Identity(cmt, pageable);
            PagingDriver pagingDriver = new PagingDriver();
            List<DriverInfoResponse> infoResponses = this.responseEntity(driverEntities);
            pagingDriver.setDriverInfoResponsesList(infoResponses);
            pagingDriver.setTotalPage(driverEntities.getTotalPages());
            pagingDriver.setNumberPage(driverEntities.getNumber() + 1);
            if (infoResponses.isEmpty()) {
                messes.setMess("Không tìm thấy dữ liệu yêu cầu ! ");
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
                messes.setMess("Không có dữ liệu trong bảng license !");
                return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(licenseInfoResponses, HttpStatus.OK);
            }
        } catch (Exception e) {
            messes.setMess(e.getMessage());
            return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?>ChangeStatusDriver(String username) {
        ReposMesses reposMesses = new ReposMesses();
        try {
            DriverInfoDetailResponse driverInfoDetailResponse = new DriverInfoDetailResponse();
            DriverEntity driverEntities = driverRepository.GetDriverByUsername(username.trim());
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
                reposMesses.setMess("Thay đổi trạng thái tài khoảns thất bại ! ");
                return new ResponseEntity<>(reposMesses, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(driverInfoDetailResponse, HttpStatus.OK);
        } catch (Exception e) {
            reposMesses.setMess(e.getMessage());
            return new ResponseEntity<>(reposMesses, HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public ResponseEntity<?> UpdateDriver(DriverInfoRequest infoRequest) {
        ResponseVo messes = new ResponseVo();
        DriverInfoDetailResponse driverInfoDetailResponse = new DriverInfoDetailResponse();
        try {

            DriverEntity driverEntity = driverRepository.Check_Username(infoRequest.getUsername().trim());
            if (!driverRepository.Check_Phone_Update(infoRequest.getPhone().trim(), infoRequest.getUsername().trim(), driverEntity.getId()).isEmpty()) {
                messes.setMessage("Số điện thoại đã được đăng kí trong hệ thống  !");
                messes.setStatus(false);
                return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
            } else if (!driverRepository.Check_Identity_Update(infoRequest.getIdentity_Number().trim(), infoRequest.getUsername().trim(), driverEntity.getId()).isEmpty()) {
                messes.setMessage("Số Chứng minh thư đã được đăng kí trong hệ thống !");
                messes.setStatus(false);
                return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
            } else if (driverRepository.Check_Number_license_update(infoRequest.getDriver_Number_License().trim(), infoRequest.getUsername().trim(), driverEntity.getId()) != null) {
                messes.setMessage("Số bằng lái xe đã tồn ! ");
                messes.setStatus(false);
                return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
            } else {
                DistrictsEntity districtsEntity = new DistrictsEntity();

                driverEntity.setImg(infoRequest.getImg());
                driverEntity.setDOB(infoRequest.getDob());
                driverEntity.setAddress(infoRequest.getAddress());
                driverEntity.setIdentity_Picture_Front(infoRequest.getIdentity_Picture_Front());
                driverEntity.setIdentity_Picture_Back(infoRequest.getIdentity_Picture_Back());
                driverEntity.setIdentity_Number(infoRequest.getIdentity_Number());
                driverEntity.setPhone(infoRequest.getPhone());
                driverEntity.setFullName(infoRequest.getFullName());
                driverEntity.setGender(infoRequest.getGender());
                driverEntity.setModifiedDate(new Date(System.currentTimeMillis()));

                List<DistrictsEntity> districtsEntities = districtRepository.check_district(infoRequest.getCity(),
                        infoRequest.getWards(),
                        infoRequest.getDistrict_Name());
                if (districtsEntities.isEmpty()) {
                    districtsEntity.setCity(infoRequest.getCity());
                    districtsEntity.setDistrict_Name(infoRequest.getDistrict_Name());
                    districtsEntity.setWards(infoRequest.getWards());
                    driverEntity.setDistrictsEntity(districtsEntity);
                    districtRepository.save(districtsEntity);
                } else {
                    DistrictsEntity districts = districtRepository.check_districts(
                            infoRequest.getCity(),
                            infoRequest.getWards(),
                            infoRequest.getDistrict_Name());
                    driverEntity.setDistrictsEntity(districts);
                }

                LicenseTypeEntity licenseTypeEntity;
                licenseTypeEntity = licenseRepository.Get_License_By_Name(infoRequest.getName_License());

                driverEntity.setLicenseTypeEntity(licenseTypeEntity);
                driverEntity.setDriver_Number_License(infoRequest.getDriver_Number_License());
                driverEntity.setDriving_license_image_Front(infoRequest.getDriving_license_image_Front());
                driverEntity.setDriving_license_image_back(infoRequest.getDriving_license_image_back());
                driverEntity.setYear_Experience(infoRequest.getYearExperience());

                driverRepository.save(driverEntity);

                driverInfoDetailResponse = driverInfoDetailResponse.driverInfoResponses(driverEntity);
                messes.setStatus(true);
                messes.setMessage("Cập nhật tài khoản thành công !");
                messes.setData(driverInfoDetailResponse);
                return new ResponseEntity<>(messes, HttpStatus.OK);
            }
        } catch (Exception e) {
            messes.setStatus(false);
            messes.setMessage(e.getMessage());
            return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
        }
    }
}
