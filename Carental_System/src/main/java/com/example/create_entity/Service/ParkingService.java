package com.example.create_entity.Service;

import com.example.create_entity.Entity.ParkingEntity;
import com.example.create_entity.dto.Request.ParkingRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

public interface ParkingService {
    public ResponseEntity<?> getAll();

    @Transactional
    public ResponseEntity<?> add(ParkingRequest parkingRequest);


    ResponseEntity<?> findById(long id);
}
