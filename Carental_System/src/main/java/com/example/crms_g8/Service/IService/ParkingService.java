package com.example.crms_g8.Service.IService;

import com.example.crms_g8.dto.Request.ParkingRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

public interface ParkingService {
    ResponseEntity<?> getAll();

    @Transactional
    ResponseEntity<?> add(ParkingRequest parkingRequest);


    ResponseEntity<?> findById(long id);
}
