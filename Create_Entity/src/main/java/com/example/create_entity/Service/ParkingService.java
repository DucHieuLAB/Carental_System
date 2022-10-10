package com.example.create_entity.Service;

import com.example.create_entity.Entity.ParkingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

public interface ParkingService  {
    public ResponseEntity<?> getAll();
}
