package com.example.create_entity.Service;

import com.example.create_entity.Entity.ParkingEntity;
import com.example.create_entity.Repository.ParkingRepository;
import com.example.create_entity.dto.Request.ParkingRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
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
        parkingRequest.setLocation("vywvtwvcjhsvgdwibv");
        parkingRequest.getDistrict().setCity("Thành phố Hà Nội");
        parkingRequest.getDistrict().setDistrictName("Quận Cầu Giấy");
        parkingRequest.getDistrict().setWards("Phường Trung Hòa");
        ResponseEntity<?> responseEntity = parkingService.add(parkingRequest);
        ParkingEntity parkingEntity = parkingRepository.findParkingByName("Bãi đỗ xe Trung Kính");
        Assertions.assertNotNull(parkingEntity);
    }
}