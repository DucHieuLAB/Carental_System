package com.example.create_entity.Service.IService;

import com.example.create_entity.Entity.ParkingEntity;
import com.example.create_entity.dto.Request.ParkingRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

public interface ParkingService {
    ResponseEntity<?> getAll();

    @Transactional
    ResponseEntity<?> add(ParkingRequest parkingRequest);


    ResponseEntity<?> findById(long id);
}
