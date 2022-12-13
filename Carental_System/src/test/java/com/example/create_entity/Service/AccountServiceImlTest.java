package com.example.create_entity.Service;

import com.example.create_entity.Entity.StaffEntity;
import com.example.create_entity.Repository.StaffRepository;
import com.example.create_entity.Service.ServiceImpl.AccountServiceIml;
import com.example.create_entity.dto.Request.StaffRequest;
import com.example.create_entity.dto.Request.UpdateInfoStaffRequest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AccountServiceImlTest {

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    AccountServiceIml accountServiceIml;

    @DisplayName("Create a valid staff")
    @Test
    @Order(1)
    public void testCreateValidStaff() {
        StaffRequest staffRequest = new StaffRequest();
        staffRequest.setAddress("Tòa B11D");
        try {
            staffRequest.setDob(new SimpleDateFormat("dd/MM/yyyy").parse("15/12/2000"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        staffRequest.setEmail("khanhpdhe141226@fpt.edu.vn");
        staffRequest.setFullName("Khánh Duy Phạm");
        staffRequest.setGender(0);
        staffRequest.setIdentity_Picture_Front("img.jpg");
        staffRequest.setIdentity_Number("020300002345");
        staffRequest.setIdentity_Picture_Back("img.jpg");
        staffRequest.setPhone("0984114471");
        staffRequest.setUser_Name("khanhpd9");
        staffRequest.setImg("img.jpg");
        staffRequest.setStatus(2);
        staffRequest.setPassword("KhanhPD8@");
        staffRequest.setDistrict_Name("Quận Cầu Giấy");
        staffRequest.setWards("Phường Trung Hòa");
        staffRequest.setCity("Thành phố Hà Nội");
        ResponseEntity<?> responseEntity = accountServiceIml.Create_Staff(staffRequest);
        StaffEntity staffEntity = staffRepository.GetStaffByUserName("khanhpd9");
        Assertions.assertNotNull(staffEntity);
    }

    @DisplayName("Create invalid email staff")
    @Test
    @Order(2)
    public void testCreateInvalidEmailStaff() {
        StaffRequest staffRequest = new StaffRequest();
        staffRequest.setAddress("Hoà Lạc");
        try {
            staffRequest.setDob(new SimpleDateFormat("dd/MM/yyyy").parse("15/02/1992"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        staffRequest.setEmail("khanhpdhe141226@fpt.edu.vn");
        staffRequest.setFullName("Vũ Mạnh Cường");
        staffRequest.setGender(0);
        staffRequest.setIdentity_Picture_Front("img.jpg");
        staffRequest.setIdentity_Number("020300220304");
        staffRequest.setIdentity_Picture_Back("img.jpg");
        staffRequest.setPhone("0987210321");
        staffRequest.setUser_Name("cuongvm");
        staffRequest.setImg("img.jpg");
        staffRequest.setStatus(2);
        staffRequest.setPassword("KhanhPD8@");
        staffRequest.setDistrict_Name("Quận Cầu Giấy");
        staffRequest.setWards("Phường Trung Hòa");
        staffRequest.setCity("Thành phố Hà Nội");
        ResponseEntity<?> responseEntity = accountServiceIml.Create_Staff(staffRequest);
        StaffEntity staffEntity = staffRepository.GetStaffByUserName("cuongvm");
        Assertions.assertNull(staffEntity);
    }

    @DisplayName("Create invalid username staff")
    @Test
    @Order(3)
    public void testCreateInvalidUsernameStaff() {
        StaffRequest staffRequest = new StaffRequest();
        staffRequest.setAddress("Hoà Lạc");
        try {
            staffRequest.setDob(new SimpleDateFormat("dd/MM/yyyy").parse("15/02/1992"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        staffRequest.setEmail("cuongvm@gmail.com");
        staffRequest.setFullName("Vũ Mạnh Cường");
        staffRequest.setGender(0);
        staffRequest.setIdentity_Picture_Front("img.jpg");
        staffRequest.setIdentity_Number("020300220304");
        staffRequest.setIdentity_Picture_Back("img.jpg");
        staffRequest.setPhone("0987210321");
        staffRequest.setUser_Name("khanhpd9");
        staffRequest.setImg("img.jpg");
        staffRequest.setStatus(2);
        staffRequest.setPassword("KhanhPD8@");
        staffRequest.setDistrict_Name("Quận Cầu Giấy");
        staffRequest.setWards("Phường Trung Hòa");
        staffRequest.setCity("Thành phố Hà Nội");
        ResponseEntity<?> responseEntity = accountServiceIml.Create_Staff(staffRequest);
        StaffEntity staffEntity = staffRepository.GetStaffByUserName("khanhpd9");
        Assertions.assertTrue(!staffEntity.getPhone().equals("0987210321"));
    }

    @DisplayName("Create invalid email format staff")
    @Test
    @Order(4)
    public void testCreateInvalidEmailFormatStaff() {
        StaffRequest staffRequest = new StaffRequest();
        staffRequest.setAddress("Hoà Lạc");
        try {
            staffRequest.setDob(new SimpleDateFormat("dd/MM/yyyy").parse("15/02/1992"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        staffRequest.setEmail("cuongvm@");
        staffRequest.setFullName("Vũ Mạnh Cường");
        staffRequest.setGender(0);
        staffRequest.setIdentity_Picture_Front("img.jpg");
        staffRequest.setIdentity_Number("020300220304");
        staffRequest.setIdentity_Picture_Back("img.jpg");
        staffRequest.setPhone("0987210321");
        staffRequest.setUser_Name("cuongvm");
        staffRequest.setImg("img.jpg");
        staffRequest.setStatus(2);
        staffRequest.setPassword("KhanhPD8@");
        staffRequest.setDistrict_Name("Quận Cầu Giấy");
        staffRequest.setWards("Phường Trung Hòa");
        staffRequest.setCity("Thành phố Hà Nội");
        ResponseEntity<?> responseEntity = accountServiceIml.Create_Staff(staffRequest);
        StaffEntity staffEntity = staffRepository.GetStaffByUserName("cuongvm");
        Assertions.assertNull(staffEntity);
    }

    @DisplayName("Create invalid phone staff")
    @Test
    @Order(5)
    public void testCreateInvalidPhoneStaff() {
        StaffRequest staffRequest = new StaffRequest();
        staffRequest.setAddress("Hoà Lạc");
        try {
            staffRequest.setDob(new SimpleDateFormat("dd/MM/yyyy").parse("15/02/1992"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        staffRequest.setEmail("cuongvm@gmail.com");
        staffRequest.setFullName("Vũ Mạnh Cường");
        staffRequest.setGender(0);
        staffRequest.setIdentity_Picture_Front("img.jpg");
        staffRequest.setIdentity_Number("020300220304");
        staffRequest.setIdentity_Picture_Back("img.jpg");
        staffRequest.setPhone("0984114471");
        staffRequest.setUser_Name("cuongvm");
        staffRequest.setImg("img.jpg");
        staffRequest.setStatus(2);
        staffRequest.setPassword("KhanhPD8@");
        staffRequest.setDistrict_Name("Quận Cầu Giấy");
        staffRequest.setWards("Phường Trung Hòa");
        staffRequest.setCity("Thành phố Hà Nội");
        ResponseEntity<?> responseEntity = accountServiceIml.Create_Staff(staffRequest);
        StaffEntity staffEntity = staffRepository.GetStaffByUserName("cuongvm");
        Assertions.assertNull(staffEntity);
    }

    @DisplayName("Create invalid identity staff")
    @Test
    @Order(6)
    public void testCreateInvalidIdentityStaff() {
        StaffRequest staffRequest = new StaffRequest();
        staffRequest.setAddress("Hoà Lạc");
        try {
            staffRequest.setDob(new SimpleDateFormat("dd/MM/yyyy").parse("15/02/1992"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        staffRequest.setEmail("cuongvm@gmail.com");
        staffRequest.setFullName("Vũ Mạnh Cường");
        staffRequest.setGender(0);
        staffRequest.setIdentity_Picture_Front("img.jpg");
        staffRequest.setIdentity_Number("020300002345");
        staffRequest.setIdentity_Picture_Back("img.jpg");
        staffRequest.setPhone("0987210321");
        staffRequest.setUser_Name("cuongvm");
        staffRequest.setImg("img.jpg");
        staffRequest.setStatus(2);
        staffRequest.setPassword("KhanhPD8@");
        staffRequest.setDistrict_Name("Quận Cầu Giấy");
        staffRequest.setWards("Phường Trung Hòa");
        staffRequest.setCity("Thành phố Hà Nội");
        ResponseEntity<?> responseEntity = accountServiceIml.Create_Staff(staffRequest);
        StaffEntity staffEntity = staffRepository.GetStaffByUserName("cuongvm");
        Assertions.assertNull(staffEntity);
    }

    @DisplayName("Create a valid staff 2")
    @Test
    @Order(7)
    public void testCreateValidStaff2() {
        StaffRequest staffRequest = new StaffRequest();
        staffRequest.setAddress("Hòa Lạc");
        try {
            staffRequest.setDob(new SimpleDateFormat("dd/MM/yyyy").parse("05/02/2001"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        staffRequest.setEmail("maipq@gmail.com");
        staffRequest.setFullName("Phạm Quỳnh Mai");
        staffRequest.setGender(1);
        staffRequest.setIdentity_Picture_Front("img.jpg");
        staffRequest.setIdentity_Number("040500006789");
        staffRequest.setIdentity_Picture_Back("img.jpg");
        staffRequest.setPhone("0989123012");
        staffRequest.setUser_Name("maipq");
        staffRequest.setImg("img.jpg");
        staffRequest.setStatus(2);
        staffRequest.setPassword("KhanhPD8@");
        staffRequest.setDistrict_Name("Huyện Thạch Thất");
        staffRequest.setWards("Xã Thạch Hòa");
        staffRequest.setCity("Thành phố Hà Nội");
        ResponseEntity<?> responseEntity = accountServiceIml.Create_Staff(staffRequest);
        StaffEntity staffEntity = staffRepository.GetStaffByUserName("maipq");
        Assertions.assertNotNull(staffEntity);
    }

    @DisplayName("Update staff")
    @Test
    @Order(8)
    public void testUpdateStaff() {
        UpdateInfoStaffRequest updateInfoStaffRequest = new UpdateInfoStaffRequest();
        updateInfoStaffRequest.setUserName("khanhpd9");
        updateInfoStaffRequest.setFullName("Nguyễn Ngọc Ánh");
        updateInfoStaffRequest.setImg_avt("img.jpg");
        updateInfoStaffRequest.setGender(1);
        updateInfoStaffRequest.setIdentity_number("020300002345");
        updateInfoStaffRequest.setIdentity_picture_back("img.jpg");
        updateInfoStaffRequest.setIdentity_picture_front("img.jpg");
        updateInfoStaffRequest.setPhone("0984114471");
        updateInfoStaffRequest.setAddress("Tòa B11D");
        updateInfoStaffRequest.setDistrict_Name("Quận Cầu Giấy");
        updateInfoStaffRequest.setWards("Phường Trung Hòa");
        updateInfoStaffRequest.setCity("Thành phố Hà Nội");
        try {
            updateInfoStaffRequest.setDob(new SimpleDateFormat("dd/MM/yyyy").parse("15/02/1992"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        ResponseEntity<?> responseEntity = accountServiceIml.UpdateStaff(updateInfoStaffRequest);
        StaffEntity staffEntity = staffRepository.GetStaffByUserName("khanhpd9");
        Assertions.assertTrue(staffEntity.getFullName().equals("Nguyễn Ngọc Ánh"));
    }

    @DisplayName("Update invalid phone staff")
    @Test
    @Order(9)
    public void testUpdateInvalidPhoneStaff() {
        UpdateInfoStaffRequest updateInfoStaffRequest = new UpdateInfoStaffRequest();
        updateInfoStaffRequest.setUserName("khanhpd9");
        updateInfoStaffRequest.setFullName("Nguyễn Ngọc Ánh");
        updateInfoStaffRequest.setImg_avt("img.jpg");
        updateInfoStaffRequest.setGender(1);
        updateInfoStaffRequest.setIdentity_number("020300002345");
        updateInfoStaffRequest.setIdentity_picture_back("img.jpg");
        updateInfoStaffRequest.setIdentity_picture_front("img.jpg");
        updateInfoStaffRequest.setPhone("0989123012");
        updateInfoStaffRequest.setAddress("Tòa B11D");
        updateInfoStaffRequest.setDistrict_Name("Quận Cầu Giấy");
        updateInfoStaffRequest.setWards("Phường Trung Hòa");
        updateInfoStaffRequest.setCity("Thành phố Hà Nội");
        try {
            updateInfoStaffRequest.setDob(new SimpleDateFormat("dd/MM/yyyy").parse("15/02/1992"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        ResponseEntity<?> responseEntity = accountServiceIml.UpdateStaff(updateInfoStaffRequest);
        StaffEntity staffEntity = staffRepository.GetStaffByUserName("khanhpd9");
        Assertions.assertTrue(staffEntity.getPhone().equals("0984114471"));
    }

    @DisplayName("Update invalid identity staff")
    @Test
    @Order(10)
    public void testUpdateInvalidIdentityStaff() {
        UpdateInfoStaffRequest updateInfoStaffRequest = new UpdateInfoStaffRequest();
        updateInfoStaffRequest.setUserName("khanhpd9");
        updateInfoStaffRequest.setFullName("Nguyễn Ngọc Ánh");
        updateInfoStaffRequest.setImg_avt("img.jpg");
        updateInfoStaffRequest.setGender(1);
        updateInfoStaffRequest.setIdentity_number("040500006789");
        updateInfoStaffRequest.setIdentity_picture_back("img.jpg");
        updateInfoStaffRequest.setIdentity_picture_front("img.jpg");
        updateInfoStaffRequest.setPhone("0984114471");
        updateInfoStaffRequest.setAddress("Tòa B11D");
        updateInfoStaffRequest.setDistrict_Name("Quận Cầu Giấy");
        updateInfoStaffRequest.setWards("Phường Trung Hòa");
        updateInfoStaffRequest.setCity("Thành phố Hà Nội");
        try {
            updateInfoStaffRequest.setDob(new SimpleDateFormat("dd/MM/yyyy").parse("15/02/1992"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        ResponseEntity<?> responseEntity = accountServiceIml.UpdateStaff(updateInfoStaffRequest);
        StaffEntity staffEntity = staffRepository.GetStaffByUserName("khanhpd9");
        Assertions.assertTrue(staffEntity.getIdentity_Number().equals("020300002345"));
    }
}