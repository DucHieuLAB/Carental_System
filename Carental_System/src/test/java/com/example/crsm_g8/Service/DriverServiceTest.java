package com.example.crsm_g8.Service;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DriverServiceTest {
//
//    @Autowired
//    DriverService driverService;
//
//    @Autowired
//    DriverRepository driverRepository;
//
//    @DisplayName("Create a valid driver")
//    @Test
//    @Order(1)
//    public void testCreateValidDriver() {
//        DriverInfoRequest driverInfoRequest = new DriverInfoRequest();
//        driverInfoRequest.setFullName("Phạm Duy Khánh");
//        driverInfoRequest.setUsername("khanhpd8");
//        driverInfoRequest.setName_License("E");
//        driverInfoRequest.setPhone("0964810558");
//        driverInfoRequest.setYearExperience(5);
//        try {
//            driverInfoRequest.setDob(new SimpleDateFormat("dd/MM/yyyy").parse("15/12/2000"));
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//        driverInfoRequest.setEmail("khanhphamduy2000@gmail.com");
//        driverInfoRequest.setGender(0);
//        driverInfoRequest.setAddress("11 Thi Sách");
//        driverInfoRequest.setIdentity_Picture_Front("img.jpg");
//        driverInfoRequest.setIdentity_Number("030200002340");
//        driverInfoRequest.setIdentity_Picture_Back("img.jpg");
//        driverInfoRequest.setImg("img.jpg");
//        driverInfoRequest.setDriver_Number_License("200340056007");
//        driverInfoRequest.setDriving_license_image_Front("img.jpg");
//        driverInfoRequest.setDriving_license_image_back("img.jpg");
//        driverInfoRequest.setPassword("KhanhPD8@");
//        driverInfoRequest.setDistrict_Name("Thành phố Hải Dương");
//        driverInfoRequest.setWards("Phường Quang Trung");
//        driverInfoRequest.setCity("Thành phố Hải Dương");
//        ResponseEntity<?> responseEntity = driverService.Create(driverInfoRequest);
//        DriverEntity driverEntity = driverRepository.GetDriverByUsername("khanhpd8");
//        Assertions.assertNotNull(driverEntity);
//    }
//
//    @DisplayName("Create invalid email driver")
//    @Test
//    @Order(2)
//    public void testCreateInvalidEmailDriver() {
//        DriverInfoRequest driverInfoRequest = new DriverInfoRequest();
//        driverInfoRequest.setFullName("Nguyễn Thị Ngọc Ánh");
//        driverInfoRequest.setUsername("shannguyen");
//        driverInfoRequest.setName_License("E");
//        driverInfoRequest.setPhone("0986123456");
//        driverInfoRequest.setYearExperience(5);
//        try {
//            driverInfoRequest.setDob(new SimpleDateFormat("dd/MM/yyyy").parse("13/08/2003"));
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//        driverInfoRequest.setEmail("khanhphamduy2000@gmail.com");
//        driverInfoRequest.setGender(1);
//        driverInfoRequest.setAddress("Khu 8");
//        driverInfoRequest.setIdentity_Picture_Front("img.jpg");
//        driverInfoRequest.setIdentity_Number("020344550020");
//        driverInfoRequest.setIdentity_Picture_Back("img.jpg");
//        driverInfoRequest.setImg("img.jpg");
//        driverInfoRequest.setDriver_Number_License("908910002000");
//        driverInfoRequest.setDriving_license_image_Front("img.jpg");
//        driverInfoRequest.setDriving_license_image_back("img.jpg");
//        driverInfoRequest.setPassword("ShnaNguyen");
//        driverInfoRequest.setDistrict_Name("Huyện Đông Anh");
//        driverInfoRequest.setWards("Thị trấn Đông Anh");
//        driverInfoRequest.setCity("Thành phố Hà Nội");
//        ResponseEntity<?> responseEntity = driverService.Create(driverInfoRequest);
//        DriverEntity driverEntity = driverRepository.GetDriverByUsername("shannguyen");
//        Assertions.assertNull(driverEntity);
//    }
//
//    @DisplayName("Create invalid username driver")
//    @Test
//    @Order(3)
//    public void testCreateInvalidUsernameDriver() {
//        DriverInfoRequest driverInfoRequest = new DriverInfoRequest();
//        driverInfoRequest.setFullName("Nguyễn Thị Ngọc Ánh");
//        driverInfoRequest.setUsername("khanhpd8");
//        driverInfoRequest.setName_License("E");
//        driverInfoRequest.setPhone("0986123456");
//        driverInfoRequest.setYearExperience(5);
//        try {
//            driverInfoRequest.setDob(new SimpleDateFormat("dd/MM/yyyy").parse("13/08/2003"));
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//        driverInfoRequest.setEmail("shnanguyen@gmail.com");
//        driverInfoRequest.setGender(1);
//        driverInfoRequest.setAddress("Khu 8");
//        driverInfoRequest.setIdentity_Picture_Front("img.jpg");
//        driverInfoRequest.setIdentity_Number("020344550020");
//        driverInfoRequest.setIdentity_Picture_Back("img.jpg");
//        driverInfoRequest.setImg("img.jpg");
//        driverInfoRequest.setDriver_Number_License("908910002000");
//        driverInfoRequest.setDriving_license_image_Front("img.jpg");
//        driverInfoRequest.setDriving_license_image_back("img.jpg");
//        driverInfoRequest.setPassword("ShnaNguyen");
//        driverInfoRequest.setDistrict_Name("Huyện Đông Anh");
//        driverInfoRequest.setWards("Thị trấn Đông Anh");
//        driverInfoRequest.setCity("Thành phố Hà Nội");
//        ResponseEntity<?> responseEntity = driverService.Create(driverInfoRequest);
//        DriverEntity driverEntity = driverRepository.Check_Number_license("908910002000");
//        Assertions.assertNull(driverEntity);
//    }
//
//    @DisplayName("Create wrong email format driver")
//    @Test
//    @Order(4)
//    public void testCreateWrongEmailFormatDriver() {
//        DriverInfoRequest driverInfoRequest = new DriverInfoRequest();
//        driverInfoRequest.setFullName("Nguyễn Thị Ngọc Ánh");
//        driverInfoRequest.setUsername("shnanguyen");
//        driverInfoRequest.setName_License("E");
//        driverInfoRequest.setPhone("0986123456");
//        driverInfoRequest.setYearExperience(5);
//        try {
//            driverInfoRequest.setDob(new SimpleDateFormat("dd/MM/yyyy").parse("13/08/2003"));
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//        driverInfoRequest.setEmail("shnanguyen");
//        driverInfoRequest.setGender(1);
//        driverInfoRequest.setAddress("Khu 8");
//        driverInfoRequest.setIdentity_Picture_Front("img.jpg");
//        driverInfoRequest.setIdentity_Number("020344550020");
//        driverInfoRequest.setIdentity_Picture_Back("img.jpg");
//        driverInfoRequest.setImg("img.jpg");
//        driverInfoRequest.setDriver_Number_License("908910002000");
//        driverInfoRequest.setDriving_license_image_Front("img.jpg");
//        driverInfoRequest.setDriving_license_image_back("img.jpg");
//        driverInfoRequest.setPassword("ShnaNguyen");
//        driverInfoRequest.setDistrict_Name("Huyện Đông Anh");
//        driverInfoRequest.setWards("Thị trấn Đông Anh");
//        driverInfoRequest.setCity("Thành phố Hà Nội");
//        ResponseEntity<?> responseEntity = driverService.Create(driverInfoRequest);
//        DriverEntity driverEntity = driverRepository.GetDriverByUsername("shnanguyen");
//        Assertions.assertNull(driverEntity);
//    }
//
//    @DisplayName("Create invalid phone driver")
//    @Test
//    @Order(5)
//    public void testCreateInvalidPhoneDriver() {
//        DriverInfoRequest driverInfoRequest = new DriverInfoRequest();
//        driverInfoRequest.setFullName("Nguyễn Thị Ngọc Ánh");
//        driverInfoRequest.setUsername("shnanguyen");
//        driverInfoRequest.setName_License("E");
//        driverInfoRequest.setPhone("0964810558");
//        driverInfoRequest.setYearExperience(5);
//        try {
//            driverInfoRequest.setDob(new SimpleDateFormat("dd/MM/yyyy").parse("13/08/2003"));
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//        driverInfoRequest.setEmail("shnanguyen@gmail.com");
//        driverInfoRequest.setGender(1);
//        driverInfoRequest.setAddress("Khu 8");
//        driverInfoRequest.setIdentity_Picture_Front("img.jpg");
//        driverInfoRequest.setIdentity_Number("020344550020");
//        driverInfoRequest.setIdentity_Picture_Back("img.jpg");
//        driverInfoRequest.setImg("img.jpg");
//        driverInfoRequest.setDriver_Number_License("908910002000");
//        driverInfoRequest.setDriving_license_image_Front("img.jpg");
//        driverInfoRequest.setDriving_license_image_back("img.jpg");
//        driverInfoRequest.setPassword("ShnaNguyen");
//        driverInfoRequest.setDistrict_Name("Huyện Đông Anh");
//        driverInfoRequest.setWards("Thị trấn Đông Anh");
//        driverInfoRequest.setCity("Thành phố Hà Nội");
//        ResponseEntity<?> responseEntity = driverService.Create(driverInfoRequest);
//        DriverEntity driverEntity = driverRepository.GetDriverByUsername("shnanguyen");
//        Assertions.assertNull(driverEntity);
//    }
//
//    @DisplayName("Create invalid identity number driver")
//    @Test
//    @Order(6)
//    public void testCreateInvalidIdentityDriver() {
//        DriverInfoRequest driverInfoRequest = new DriverInfoRequest();
//        driverInfoRequest.setFullName("Nguyễn Thị Ngọc Ánh");
//        driverInfoRequest.setUsername("shnanguyen");
//        driverInfoRequest.setName_License("E");
//        driverInfoRequest.setPhone("0986123456");
//        driverInfoRequest.setYearExperience(5);
//        try {
//            driverInfoRequest.setDob(new SimpleDateFormat("dd/MM/yyyy").parse("13/08/2003"));
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//        driverInfoRequest.setEmail("shnanguyen@gmail.com");
//        driverInfoRequest.setGender(1);
//        driverInfoRequest.setAddress("Khu 8");
//        driverInfoRequest.setIdentity_Picture_Front("img.jpg");
//        driverInfoRequest.setIdentity_Number("030200002340");
//        driverInfoRequest.setIdentity_Picture_Back("img.jpg");
//        driverInfoRequest.setImg("img.jpg");
//        driverInfoRequest.setDriver_Number_License("908910002000");
//        driverInfoRequest.setDriving_license_image_Front("img.jpg");
//        driverInfoRequest.setDriving_license_image_back("img.jpg");
//        driverInfoRequest.setPassword("ShnaNguyen");
//        driverInfoRequest.setDistrict_Name("Huyện Đông Anh");
//        driverInfoRequest.setWards("Thị trấn Đông Anh");
//        driverInfoRequest.setCity("Thành phố Hà Nội");
//        ResponseEntity<?> responseEntity = driverService.Create(driverInfoRequest);
//        DriverEntity driverEntity = driverRepository.GetDriverByUsername("shnanguyen");
//        Assertions.assertNull(driverEntity);
//    }
//
//    @DisplayName("Create invalid license number driver")
//    @Test
//    @Order(7)
//    public void testCreateInvalidLicenseDriver() {
//        DriverInfoRequest driverInfoRequest = new DriverInfoRequest();
//        driverInfoRequest.setFullName("Nguyễn Thị Ngọc Ánh");
//        driverInfoRequest.setUsername("shnanguyen");
//        driverInfoRequest.setName_License("E");
//        driverInfoRequest.setPhone("0986123456");
//        driverInfoRequest.setYearExperience(5);
//        try {
//            driverInfoRequest.setDob(new SimpleDateFormat("dd/MM/yyyy").parse("13/08/2003"));
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//        driverInfoRequest.setEmail("shnanguyen@gmail.com");
//        driverInfoRequest.setGender(1);
//        driverInfoRequest.setAddress("Khu 8");
//        driverInfoRequest.setIdentity_Picture_Front("img.jpg");
//        driverInfoRequest.setIdentity_Number("020344550020");
//        driverInfoRequest.setIdentity_Picture_Back("img.jpg");
//        driverInfoRequest.setImg("img.jpg");
//        driverInfoRequest.setDriver_Number_License("200340056007");
//        driverInfoRequest.setDriving_license_image_Front("img.jpg");
//        driverInfoRequest.setDriving_license_image_back("img.jpg");
//        driverInfoRequest.setPassword("ShnaNguyen");
//        driverInfoRequest.setDistrict_Name("Huyện Đông Anh");
//        driverInfoRequest.setWards("Thị trấn Đông Anh");
//        driverInfoRequest.setCity("Thành phố Hà Nội");
//        ResponseEntity<?> responseEntity = driverService.Create(driverInfoRequest);
//        DriverEntity driverEntity = driverRepository.GetDriverByUsername("shnanguyen");
//        Assertions.assertNull(driverEntity);
//    }
//
//    @DisplayName("Update driver")
//    @Test
//    @Order(8)
//    public void testUpdateDriver() {
//        DriverInfoRequest driverInfoRequest = new DriverInfoRequest();
//        driverInfoRequest.setFullName("Nguyễn Thị Ngọc Ánh");
//        driverInfoRequest.setUsername("khanhpd8");
//        driverInfoRequest.setName_License("E");
//        driverInfoRequest.setPhone("0986123456");
//        driverInfoRequest.setYearExperience(5);
//        try {
//            driverInfoRequest.setDob(new SimpleDateFormat("dd/MM/yyyy").parse("13/08/2003"));
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//        driverInfoRequest.setEmail("shnanguyen@gmail.com");
//        driverInfoRequest.setGender(1);
//        driverInfoRequest.setAddress("Khu 8");
//        driverInfoRequest.setIdentity_Picture_Front("img.jpg");
//        driverInfoRequest.setIdentity_Number("020344550020");
//        driverInfoRequest.setIdentity_Picture_Back("img.jpg");
//        driverInfoRequest.setImg("img.jpg");
//        driverInfoRequest.setDriver_Number_License("200340056007");
//        driverInfoRequest.setDriving_license_image_Front("img.jpg");
//        driverInfoRequest.setDriving_license_image_back("img.jpg");
//        driverInfoRequest.setPassword("ShnaNguyen");
//        driverInfoRequest.setDistrict_Name("Huyện Đông Anh");
//        driverInfoRequest.setWards("Thị trấn Đông Anh");
//        driverInfoRequest.setCity("Thành phố Hà Nội");
//        ResponseEntity<?> responseEntity = driverService.UpdateDriver(driverInfoRequest);
//        DriverEntity driverEntity = driverRepository.GetDriverByUsername("khanhpd8");
//        Assertions.assertTrue(driverEntity.getPhone().equals("0986123456"));
//        Assertions.assertTrue(driverEntity.getIdentity_Number().equals("020344550020"));
//        Assertions.assertTrue(driverEntity.getDriver_Number_License().equals("200340056007"));
//    }
//
//    @DisplayName("Change driver status")
//    @Test
//    @Order(9)
//    public void testChangeDriverStatus() {
//        ResponseEntity<?> responseEntity = driverService.ChangeStatusDriver("khanhpd8");
//        DriverEntity driverEntity = driverRepository.GetDriverByUsername("khanhpd8");
//        Assertions.assertTrue(driverEntity.getStatus() == 0);
//        Assertions.assertTrue(driverEntity.getAccountEntity().getStatus() == 0);
//    }

}