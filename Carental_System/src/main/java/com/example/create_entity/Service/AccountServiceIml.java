package com.example.create_entity.Service;

import com.example.create_entity.Entity.*;
import com.example.create_entity.Repository.*;
import com.example.create_entity.dto.Request.*;
import com.example.create_entity.dto.Response.*;
import com.example.create_entity.untils.RandomString;
import org.apache.logging.log4j.message.Message;
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

    @Autowired
    CustomerRepository customerRepository;

    StaffResponse staffResponse = new StaffResponse();
    @Autowired
    DistrictRepository districtRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    StaffRepository staffRepository;

    public Integer CheckNullPaging(Integer p) {
        if (p == null) {
            p = 0;
        } else if (p > 0) {
            p = p - 1;
        }
        return p;
    }

    @Override
    public ResponseEntity<?> getListStaff(Integer p) {

        p = CheckNullPaging(p);
        Pageable pageable = PageRequest.of(p, 5);
        Page<StaffEntity> staffRepositories = staffRepository.List_Staff(pageable);
        return responseEntity(staffRepositories);

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
            StaffEntity staffEntity = new StaffEntity();


            if (!accountRepository.Check_email(infoRequest.getEmail()).isEmpty()) {
                messes.setMess("Email đã tồn tại  !");
                return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
            } else if (!accountRepository.Check_username(infoRequest.getUser_Name()).isEmpty()) {
                messes.setMess("UserName đã tồn tại !");
                return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
            } else if (!infoRequest.getEmail().matches(regexPattern)) {
                messes.setMess("Email k đúng định dạng !");
                return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
            } else if (!staffRepository.Check_Phone(infoRequest.getPhone().trim()).isEmpty()) {
                messes.setMess("Số điện thoại đã được đăng kí trong hệ thống  !");
                return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
            } else if (!staffRepository.Check_Identity(infoRequest.getIdentity_Number().trim()).isEmpty()) {
                messes.setMess("Số Chứng minh thư đã được đăng kí trong hệ thống !");
                return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
            } else {

                String encodedPassword = passwordEncoder.encode(infoRequest.getPassword());
                accountEntity.setPassword(encodedPassword.trim());
                accountEntity.setEmail(infoRequest.getEmail().trim());
                accountEntity.setUsername(infoRequest.getUser_Name().trim());
                accountEntity.setCreateDate(date);
                accountEntity.setModifiedDate(date);
                accountEntity.setRoleEntity(roleEntity);
                accountEntity.setStatus(2);
                accountRepository.save(accountEntity);

                staffEntity.setIdentity_Picture_Back(infoRequest.getIdentity_Picture_Back().trim());
                staffEntity.setIdentity_Picture_Front(infoRequest.getIdentity_Picture_Front().trim());
                staffEntity.setAddress(infoRequest.getAddress());
                staffEntity.setFullName(infoRequest.getFullName().trim());
                staffEntity.setImg(infoRequest.getImg());
                staffEntity.setDOB(infoRequest.getDob());
                staffEntity.setGender(infoRequest.getGender());
                staffEntity.setIdentity_Number(infoRequest.getIdentity_Number().trim());
                staffEntity.setPhone(infoRequest.getPhone().trim());
                staffEntity.setAccountEntity(accountEntity);
                staffEntity.setModifiedDate(date);
                staffEntity.setStatus(1);


                String city = infoRequest.getCity().trim();
                String district_name = infoRequest.getDistrict_Name().trim();
                String wards = infoRequest.getWards().trim();

                List<DistrictsEntity> districtsEntities = districtRepository.check_district(city, wards, district_name);
                if (!districtsEntities.isEmpty()) {
                    DistrictsEntity districts = districtRepository.check_districts(city, wards, district_name);
                    staffEntity.setDistrictsEntity(districts);
                } else {
                    districtsEntity.setCity(city);
                    districtsEntity.setDistrict_Name(district_name);
                    districtsEntity.setWards(wards);
                    staffEntity.setDistrictsEntity(districtsEntity);
                    districtRepository.save(districtsEntity);
                }

                staffRepository.save(staffEntity);
                messes.setMess("Đã Tạo Thành Công ! ");
                return new ResponseEntity<>(messes, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private ResponseEntity<?> responseEntity(Page<StaffEntity> staffEntities) {

        try {
            List<StaffResponse> staffResponseList = staffResponse.staffResponseList(staffEntities);

            PagingResponse pagingResponse = new PagingResponse();
            pagingResponse.setObjects(staffResponseList);
            pagingResponse.setTotalPage(staffEntities.getTotalPages());
            pagingResponse.setNumberPage(staffEntities.getNumber() + 1);

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
        Pageable pageable = PageRequest.of(p, 5);
        Page<StaffEntity> staffRepositories = staffRepository.FilterByName(Name, pageable);
        return responseEntity(staffRepositories);

    }

    @Override
    public ResponseEntity<?> FilterByPhone(String Phone, Integer p) {
        p = CheckNullPaging(p);
        Pageable pageable = PageRequest.of(p, 5);
        Page<StaffEntity> staffRepositories = staffRepository.FilterByPhone(Phone, pageable);
        return responseEntity(staffRepositories);
    }

    @Override
    public ResponseEntity<?> FilterByIdentity_Number(String Identity_Number, Integer p) {
        p = CheckNullPaging(p);
        Pageable pageable = PageRequest.of(p, 5);
        Page<StaffEntity> staffEntities = staffRepository.FilterByIdentity_Number(Identity_Number, pageable);
        return responseEntity(staffEntities);
    }

    @Override
    public ResponseEntity<?> ChangeStatusStaff(String UserName) {
        ReposMesses messes = new ReposMesses();
        StaffResponse staffResponse = new StaffResponse();
        try {
            StaffEntity staffEntities = staffRepository.GetStaffByUserName(UserName);
            if (staffEntities != null && (staffEntities.getAccountEntity().getStatus() == 2
                    || staffEntities.getAccountEntity().getStatus() == 1)) {
                Date date = new Date(System.currentTimeMillis());
                staffEntities.getAccountEntity().setStatus(0);
                staffEntities.setStatus(0);
                staffEntities.setModifiedDate(date);

                staffResponse = staffResponse.staffResponse(staffEntities);
                staffRepository.save(staffEntities);
                return new ResponseEntity<>(staffResponse, HttpStatus.OK);
            } else if (staffEntities != null && staffEntities.getAccountEntity().getStatus() == 0) {
                Date date = new Date(System.currentTimeMillis());
                staffEntities.getAccountEntity().setStatus(2);
                staffEntities.setStatus(1);
                staffEntities.setModifiedDate(date);
                staffResponse = staffResponse.staffResponse(staffEntities);
                staffRepository.save(staffEntities);
                return new ResponseEntity<>(staffResponse, HttpStatus.OK);
            }

        } catch (Exception e) {
            messes.setMess(e.getMessage());
            return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    @Override
    public ResponseEntity<?> GetDetailStaff(String UserName) {
        StaffDetailResponse detailResponse = new StaffDetailResponse();
        try {
            StaffEntity staffEntities = staffRepository.GetStaffByUserName(UserName.trim());
            detailResponse = detailResponse.staffDetailResponse(staffEntities);
            return new ResponseEntity<>(detailResponse, HttpStatus.OK);
        } catch (Exception e) {
            ReposMesses messes = new ReposMesses();
            messes.setMess(e.getMessage());
            return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
        }
    }

//    @Override
//    public ResponseEntity<?> sendOTPEmail_Register(RegisterInfoRequest REQUEST, HttpServletResponse response) {
//        return null;
//    }

    private RegisterInfoRequest registerInfoRequest;

    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private RandomString randomString;

    @Override
    @Transactional
    public ResponseEntity<?> sendOTPEmail_Register(RegisterInfoRequest REQUEST, HttpServletResponse response) {

        String regexPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        ResponseVo responseVo = new ResponseVo();
        CustomerEntity customerEntity = new CustomerEntity();
        RoleEntity roleEntity = roleRepository.GetRoleDriver("Customer");
        AccountEntity accountEntity = new AccountEntity();
        try {
            if (!accountRepository.Check_email(REQUEST.getEmail().trim()).isEmpty()) {
                responseVo.setMessage("Email đã tồn tại  !");
                responseVo.setStatus(false);
                return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
            } else if (!accountRepository.Check_username(REQUEST.getUserName().trim()).isEmpty()) {
                responseVo.setMessage("UserName đã tồn tại !");
                responseVo.setStatus(false);
                return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
            } else if (!REQUEST.getEmail().matches(regexPattern)) {
                responseVo.setMessage("Email k đúng định dạng !");
                responseVo.setStatus(false);
                return new ResponseEntity<>( responseVo, HttpStatus.BAD_REQUEST);
            }
            String Code_OTP = randomString.generateRandomString();
            accountEntity.setEmail(REQUEST.getEmail());
            accountEntity.setUsername(REQUEST.getUserName());
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(REQUEST.getPassword());
            accountEntity.setPassword(encodedPassword.trim());

            accountEntity.setCreateDate(new Date());
            accountEntity.setModifiedDate(new Date());
            accountEntity.setRoleEntity(roleEntity);
            accountEntity.setStatus(1);

            customerEntity.setFullName(REQUEST.getFullName());
            customerEntity.setAccountEntity(accountEntity);
            customerEntity.setModifiedDate(new Date(System.currentTimeMillis()));
            customerEntity.setStatus(1);


            Cookie cookie = new Cookie("username", REQUEST.getUserName());
            cookie.setMaxAge(5 * 60);
            cookie.setPath("http://localhost:8080/api/account/Customer/CfOTP");
            response.addCookie(cookie);

            Cookie cookieOTP = new Cookie("OTP", Code_OTP);
            cookieOTP.setMaxAge(5 * 60);
            cookieOTP.setPath("http://localhost:8080/api/account/Customer/CfOTP");
            response.addCookie(cookieOTP);

            accountRepository.save(accountEntity);
            customerRepository.save(customerEntity);


            emailSenderService.sendSimpleEmail(REQUEST.getEmail(), "Register Account - Here's your One Time Password (OTP) - Expire in 5 minutes!", Code_OTP);
            responseVo.setMessage("Vui Lòng Kiểm tra mã OTP ở Email để xác thực !");
            responseVo.setStatus(true);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        } catch (Exception e) {
            responseVo.setMessage(e.getMessage());
            responseVo.setStatus(false);
            return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> Confirm_Register_OTPEmail(String username, String OTP, String OTP_ck, HttpServletResponse response) {
        ResponseVo responseVo = new ResponseVo();
        try {
            AccountEntity accountEntity = accountRepository.Check_ConfirmOTP(username.trim());
            if (accountEntity != null && OTP.trim().equals(OTP_ck.trim())) {
                accountEntity.setStatus(2);
                accountEntity.setModifiedDate(new Date(System.currentTimeMillis()));
                accountRepository.save(accountEntity);
                responseVo.setStatus(true);
                responseVo.setMessage("Xác thực tài khoản thành công !");
                RemoveCookie("username", response, "http://localhost:8080/api/account/Customer/CfOTP");
                RemoveCookie("OTP", response, "http://localhost:8080/api/account/Customer/CfOTP");
                return new ResponseEntity<>(responseVo, HttpStatus.OK);
            } else {
                responseVo.setStatus(false);
                responseVo.setMessage("Mã OTP đã hết hiệu lực !");
                return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            responseVo.setMessage(e.getMessage());
            responseVo.setStatus(false);
            return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
        }
//        return null;
    }


    @Override
    public ResponseEntity<?> SendOTPtoEmail(String email, HttpServletResponse response) {
        ResponseVo responseVo = new ResponseVo();
        try {
            AccountEntity accountEntity = accountRepository.GetAccountByEmail(email.trim());
            System.out.println(accountEntity);
            if (accountEntity != null) {
                String Code_OTP = randomString.generateRandomString();
                Cookie cookie = new Cookie("email", email.trim());
                cookie.setPath("http://localhost:8080/api/account/CfOTP_Forgot");
                cookie.setMaxAge(5 * 60);
                response.addCookie(cookie);

                Cookie cookieOTP = new Cookie("OTP", Code_OTP);
                cookieOTP.setMaxAge(5 * 60);
                cookieOTP.setPath("http://localhost:8080/api/account/CfOTP_Forgot");
                response.addCookie(cookieOTP);
                emailSenderService.sendSimpleEmail(email, "Forgot Password - Here's your One Time Password (OTP)  - Expire in 5 minutes!", Code_OTP);
                responseVo.setMessage("Vui Lòng Kiểm tra mã OTP ở Email để xác thực !");
                responseVo.setStatus(true);
                return new ResponseEntity<>(responseVo, HttpStatus.OK);
            } else {
                responseVo.setStatus(false);
                responseVo.setMessage("Email không tồn tại trong hệ thống hoặc đã bị khóa !");
                return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            responseVo.setStatus(false);
            responseVo.setMessage(e.getMessage());
            return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
        }
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
                accountEntity.setModifiedDate(new Date(System.currentTimeMillis()));
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


    public void RemoveCookie(String Name, HttpServletResponse response, String URL) {
        Cookie cookie = new Cookie(Name, null);
        cookie.setMaxAge(0);
        cookie.setPath(URL);
        response.addCookie(cookie);
    }

    @Override
    @Transactional
    public ResponseEntity<?> UpdateCustomer(UpdateInfoCustomerRequest updateInfoCustomerRequest) {
        ReposMesses messes = new ReposMesses();
        try {
            if (customerRepository.Check_Phone(updateInfoCustomerRequest.getPhone().trim()) !=null) {
                messes.setMess("Số điện thoại đã tồn tại trong hệ thống !");
                return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
            } else if (customerRepository.Check_Identity(updateInfoCustomerRequest.getIdentity_number().trim()) != null) {
                messes.setMess("Số chứng minh thư đã tồn tại trong hệ thống !");
                return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
            }else {
                CustomerEntity customer = customerRepository.GetCustomerByName(updateInfoCustomerRequest.getUserName());
                DistrictsEntity districtsEntity = new DistrictsEntity();
                customer.setImg(updateInfoCustomerRequest.getImg_avt());
                customer.setDOB(updateInfoCustomerRequest.getDob());
                customer.setAddress(updateInfoCustomerRequest.getAddress());
                customer.setIdentity_Picture_Front(updateInfoCustomerRequest.getIdentity_picture_front());
                customer.setIdentity_Picture_Back(updateInfoCustomerRequest.getIdentity_picture_back());
                customer.setIdentity_Number(updateInfoCustomerRequest.getIdentity_number());
                customer.setPhone(updateInfoCustomerRequest.getPhone());
                customer.setFullName(updateInfoCustomerRequest.getFullName());
                customer.setStatus(updateInfoCustomerRequest.getStatus());
                customer.setGender(updateInfoCustomerRequest.getGender());
                customer.setModifiedDate(new Date(System.currentTimeMillis()));

                List<DistrictsEntity> districtsEntities = districtRepository.check_district(updateInfoCustomerRequest.getCity(),
                        updateInfoCustomerRequest.getWards(),
                        updateInfoCustomerRequest.getDistrict_Name());
                if (districtsEntities.isEmpty()) {
                    districtsEntity.setCity(updateInfoCustomerRequest.getCity());
                    districtsEntity.setDistrict_Name(updateInfoCustomerRequest.getDistrict_Name());
                    districtsEntity.setWards(updateInfoCustomerRequest.getWards());
                    customer.setDistrictsEntity(districtsEntity);
                    districtRepository.save(districtsEntity);
                } else {
                    DistrictsEntity districts = districtRepository.check_districts(
                            updateInfoCustomerRequest.getCity(),
                            updateInfoCustomerRequest.getWards(),
                            updateInfoCustomerRequest.getDistrict_Name());
                    customer.setDistrictsEntity(districts);
                }
                customerRepository.save(customer);
                messes.setMess("Cập nhật thành công !");
                return new ResponseEntity<>(messes, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @Override
    @Transactional
    public ResponseEntity<?> UpdateStaff(UpdateInfoStaffRequest updateInfoStaffRequest) {
        ReposMesses messes = new ReposMesses();
        try {
            if (!staffRepository.Check_Phone(updateInfoStaffRequest.getPhone().trim()).isEmpty()) {
                messes.setMess("Số điện thoại đã tồn tại trong hệ thống !");
                return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
            } else if (!staffRepository.Check_Identity(updateInfoStaffRequest.getIdentity_number().trim()).isEmpty()) {
                messes.setMess("Số chứng minh thư đã tồn tại trong hệ thống !");
                return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
            }else {
                StaffEntity staffEntity =staffRepository.GetStaffByUserName(updateInfoStaffRequest.getUserName());
                DistrictsEntity districtsEntity = new DistrictsEntity();
                staffEntity.setImg(updateInfoStaffRequest.getImg_avt());
                staffEntity.setDOB(updateInfoStaffRequest.getDob());
                staffEntity.setAddress(updateInfoStaffRequest.getAddress());
                staffEntity.setIdentity_Picture_Front(updateInfoStaffRequest.getIdentity_picture_front());
                staffEntity.setIdentity_Picture_Back(updateInfoStaffRequest.getIdentity_picture_back());
                staffEntity.setIdentity_Number(updateInfoStaffRequest.getIdentity_number());
                staffEntity.setPhone(updateInfoStaffRequest.getPhone());
                staffEntity.setFullName(updateInfoStaffRequest.getFullName());
                staffEntity.setGender(updateInfoStaffRequest.getGender());
                staffEntity.setModifiedDate(new Date(System.currentTimeMillis()));

                List<DistrictsEntity> districtsEntities = districtRepository.check_district(updateInfoStaffRequest.getCity(),
                        updateInfoStaffRequest.getWards(),
                        updateInfoStaffRequest.getDistrict_Name());
                if (districtsEntities.isEmpty()) {
                    districtsEntity.setCity(updateInfoStaffRequest.getCity());
                    districtsEntity.setDistrict_Name(updateInfoStaffRequest.getDistrict_Name());
                    districtsEntity.setWards(updateInfoStaffRequest.getWards());
                    staffEntity.setDistrictsEntity(districtsEntity);
                    districtRepository.save(districtsEntity);
                } else {
                    DistrictsEntity districts = districtRepository.check_districts(
                            updateInfoStaffRequest.getCity(),
                            updateInfoStaffRequest.getWards(),
                            updateInfoStaffRequest.getDistrict_Name());
                    staffEntity.setDistrictsEntity(districts);
                }
              staffRepository.save(staffEntity);
                messes.setMess("Cập nhật thành công !");
                return new ResponseEntity<>(messes, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> ListCustomer(Integer p) {
        ReposMesses messes = new ReposMesses();
        p = this.CheckNullPaging(p);
        try {
            Pageable pageable = PageRequest.of(p, 5);
            Page<CustomerEntity> page = customerRepository.GetListCustomer(pageable);
            ListCustomerResponse listCustomerResponse = new ListCustomerResponse();
            List<ListCustomerResponse> listCustomerResponses = listCustomerResponse.listCustomerResponses(page);

            PagingResponse pagingResponse = new PagingResponse();
            pagingResponse.setObjects(listCustomerResponses);
            pagingResponse.setTotalPage(page.getTotalPages());
            pagingResponse.setNumberPage(page.getNumber() + 1);

            return new ResponseEntity<>(pagingResponse, HttpStatus.OK);
        } catch (Exception e) {
            messes.setMess(e.getMessage());
            return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> DetailCustomer(String username) {

        ReposMesses messes = new ReposMesses();
        try {
            CustomerEntity customer = customerRepository.GetCustomerByName(username);
            CustomerInfoResponse infoResponse = new CustomerInfoResponse();
            infoResponse = infoResponse.customerInfoResponse(customer);
            return new ResponseEntity<>(infoResponse, HttpStatus.OK);
        } catch (Exception e) {
            messes.setMess(e.getMessage());
            return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> FilterByNameCustomer(String name, Integer p) {
        ReposMesses messes = new ReposMesses();
        p = this.CheckNullPaging(p);
        try {
            Pageable pageable = PageRequest.of(p, 5);
            Page<CustomerEntity> page = customerRepository.FilterByName(name, pageable);
            ListCustomerResponse listCustomerResponse = new ListCustomerResponse();
            List<ListCustomerResponse> listCustomerResponses = listCustomerResponse.listCustomerResponses(page);

            if (listCustomerResponses.isEmpty()) {
                messes.setMess("K tim thay !");
                return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
            } else {
                PagingResponse pagingResponse = new PagingResponse();
                pagingResponse.setObjects(listCustomerResponses);
                pagingResponse.setTotalPage(page.getTotalPages());
                pagingResponse.setNumberPage(page.getNumber() + 1);

                return new ResponseEntity<>(pagingResponse, HttpStatus.OK);
            }
        } catch (Exception e) {
            messes.setMess(e.getMessage());
            return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> FilterByPhoneCustomer(String phone, Integer p) {
        ReposMesses messes = new ReposMesses();
        p = this.CheckNullPaging(p);
        try {
            Pageable pageable = PageRequest.of(p, 5);
            Page<CustomerEntity> page = customerRepository.FilterByPhone(phone, pageable);
            ListCustomerResponse listCustomerResponse = new ListCustomerResponse();
            List<ListCustomerResponse> listCustomerResponses = listCustomerResponse.listCustomerResponses(page);

            if (listCustomerResponses.isEmpty()) {
                messes.setMess("K tim thay !");
                return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
            } else {
                PagingResponse pagingResponse = new PagingResponse();
                pagingResponse.setObjects(listCustomerResponses);
                pagingResponse.setTotalPage(page.getTotalPages());
                pagingResponse.setNumberPage(page.getNumber() + 1);

                return new ResponseEntity<>(pagingResponse, HttpStatus.OK);
            }
        } catch (Exception e) {
            messes.setMess(e.getMessage());
            return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> FilterByIdentityCustomer(String identity_number, Integer p) {
        ReposMesses messes = new ReposMesses();
        p = this.CheckNullPaging(p);
        try {
            Pageable pageable = PageRequest.of(p, 5);
            Page<CustomerEntity> page = customerRepository.FilterByIdentity(identity_number, pageable);
            ListCustomerResponse listCustomerResponse = new ListCustomerResponse();
            List<ListCustomerResponse> listCustomerResponses = listCustomerResponse.listCustomerResponses(page);

            if (listCustomerResponses.isEmpty()) {
                messes.setMess("K tim thay !");
                return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
            } else {
                PagingResponse pagingResponse = new PagingResponse();
                pagingResponse.setObjects(listCustomerResponses);
                pagingResponse.setTotalPage(page.getTotalPages());
                pagingResponse.setNumberPage(page.getNumber() + 1);

                return new ResponseEntity<>(pagingResponse, HttpStatus.OK);
            }
        } catch (Exception e) {
            messes.setMess(e.getMessage());
            return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
        }
    }
}
