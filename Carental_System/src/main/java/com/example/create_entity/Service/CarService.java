package com.example.create_entity.Service;

import com.example.create_entity.dto.Request.BrandRequest;
import com.example.create_entity.dto.Request.CarRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CarService {
    public ResponseEntity<?> getListCapacity();

    public ResponseEntity<?> add(CarRequest carRequest);

    ResponseEntity<?> findAll(int pageIndex, int pageSize,
                              String modelName,Long parkingId,Integer capacity);
    @Transactional
    public ResponseEntity<?> update( CarRequest carRequest);

    @Transactional
    public ResponseEntity<?> delete(Long carId);

    ResponseEntity<?> findById(Long brandId, int pageIndex, int pageSize);

    ResponseEntity<?> findByPlateNumber(String carPlateNumber);

}
