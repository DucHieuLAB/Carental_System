package com.example.create_entity.Service;

import com.example.create_entity.dto.Request.BrandRequest;
import com.example.create_entity.dto.Request.CarRequest;
import com.example.create_entity.dto.Request.DriverByCarByContractRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface CarService {
    ResponseEntity<?> getListCapacity();

    ResponseEntity<?> add(CarRequest carRequest);

    ResponseEntity<?> findAll(int pageIndex, int pageSize,String modelName, Long parkingId, Integer capacity);

    @Transactional
    ResponseEntity<?> update(CarRequest carRequest);

    @Transactional
    ResponseEntity<?> delete(Long carId);

    ResponseEntity<?> findById(Long brandId, int pageIndex, int pageSize);

    ResponseEntity<?> findByPlateNumber(String carPlateNumber);

    ResponseEntity<?> getListDriverByCarPlateNumber(DriverByCarByContractRequest driverByCarByContractRequest);

    ResponseEntity<?> getListCarSelfDriver(Date startDate, Date endDate, Long pickupParkingId,Long returnParkingId);

    ResponseEntity<?> getListCarHadDriverContract(Date startDate, Date endDate, String cityName);

//    ResponseEntity<?> getListBestSeller();

}
