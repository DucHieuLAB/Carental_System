package com.example.create_entity.Service;

import com.example.create_entity.Entity.ParkingEntity;
import com.example.create_entity.Repository.ParkingRepository;
import com.example.create_entity.Service.ServiceImpl.ParkingServiceImpl;
import com.example.create_entity.dto.Request.DistricRequest;
import com.example.create_entity.dto.Request.ParkingRequest;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ParkingServiceImplTest {

    @Autowired
    ParkingServiceImpl parkingService;

    @Autowired
    ParkingRepository parkingRepository;

    @DisplayName("Create new parking")
    @Test
    @Order(1)
    public void testCreateParking() {
        ParkingRequest parkingRequest = new ParkingRequest();
        parkingRequest.setName("Bãi đỗ xe Trung Kính");
        parkingRequest.setAddress("Trung Kính");
        parkingRequest.setPhone("0912345678");
        parkingRequest.setLocation("Trung Hòa Cầu Giấy Hà Nội");
        DistricRequest districRequest = new DistricRequest();
        districRequest.setCity("Thành phố Hà Nội");
        districRequest.setDistrictName("Quận Cầu Giấy");
        districRequest.setWards("Phường Trung Hòa");
        parkingRequest.setDistrict(districRequest);
        ResponseEntity<?> responseEntity = parkingService.add(parkingRequest);
        ParkingEntity parkingEntity = parkingRepository.findParkingByName("Bãi đỗ xe Trung Kính");
        Assertions.assertNotNull(parkingEntity);
    }

    @DisplayName("Test get Parking Detail")
    @Test
    @Order(2)
    public void getParingById(){

    }

    @DisplayName("Test update Parking")
    @Test
    @Order(3)
    public void updateParking(){

    }

    @DisplayName("Test delete Parking")
    @Test
    @Order(4)
    public void deleteParkingTest(){

    }

}