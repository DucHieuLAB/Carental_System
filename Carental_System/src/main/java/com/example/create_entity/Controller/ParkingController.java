package com.example.create_entity.Controller;

import com.example.create_entity.Service.CarServiceImpl;
import com.example.create_entity.Service.ParkingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/Car")
public class ParkingController {
    private final ParkingServiceImpl parkingService;

    @Autowired
    public ParkingController(ParkingServiceImpl parkingService) {this.parkingService = parkingService;}

    @GetMapping(value = "/Parking")
    public ResponseEntity<?> getListCapacity(){
        return parkingService.getAll();
    }

}
