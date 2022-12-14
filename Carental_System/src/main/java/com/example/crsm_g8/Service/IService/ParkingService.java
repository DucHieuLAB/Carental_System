package com.example.crsm_g8.Service.IService;

import com.example.crsm_g8.dto.Request.ParkingRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

public interface ParkingService {
    ResponseEntity<?> getAll();

    @Transactional
    ResponseEntity<?> add(ParkingRequest parkingRequest);


    ResponseEntity<?> findById(long id);
}
