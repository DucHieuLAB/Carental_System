package com.example.create_entity.Service;

import com.example.create_entity.Entity.ParkingEntity;
import com.example.create_entity.Repository.ParkingRepository;
import com.example.create_entity.dto.Request.ParkingRequest;
import com.example.create_entity.dto.Response.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;


import java.util.List;

@Service
public class ParkingServiceImpl implements ParkingService{
    @Autowired
    ParkingRepository parkingRepository;

    @Override
    public ResponseEntity<?> getAll() {
        ResponseVo responseVo = new ResponseVo();
        try{
            List<ParkingEntity> parkingServices = parkingRepository.findAll();
            responseVo.setData(parkingServices);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }catch (Exception e){
            responseVo.setMessage("Error when connect to database");
            return new ResponseEntity<>(responseVo,HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public ResponseEntity<?> add(ParkingRequest parkingRequest) {
        ResponseVo responseVo = new ResponseVo();
        if (ObjectUtils.isEmpty(parkingRequest)) {
            responseVo.setStatus(false);
            responseVo.setMessage("invialid input, parrking is empty");
            return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(responseVo,HttpStatus.OK);
    }
}
