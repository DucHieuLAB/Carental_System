package com.example.create_entity.Service;

import com.example.create_entity.Entity.DistrictsEntity;
import com.example.create_entity.Entity.ParkingEntity;
import com.example.create_entity.Repository.DistrictRepository;
import com.example.create_entity.Repository.ParkingRepository;
import com.example.create_entity.dto.Request.DistricRequest;
import com.example.create_entity.dto.Request.ParkingRequest;
import com.example.create_entity.dto.Response.ListParkingResponse;
import com.example.create_entity.dto.Response.ResponseVo;
import com.example.create_entity.untils.ResponseVeConvertUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;


import java.util.List;

@Service
public class ParkingServiceImpl implements ParkingService {
    @Autowired
    ParkingRepository parkingRepository;

    @Autowired
    DistrictRepository districtRepository;


    @Override
    public ResponseEntity<?> getAll() {
        ResponseVo responseVo = new ResponseVo();
        try {
            List<ParkingEntity> parkingServices = parkingRepository.findAllNoPagding();
            if (parkingServices.isEmpty()) {
                responseVo = ResponseVeConvertUntil.createResponseVo(true, "Danh sách bãi đỗ xe trông", null);
                return new ResponseEntity<>(responseVo, HttpStatus.OK);
            }
            List<ListParkingResponse> listParkingResponses = ListParkingResponse.CreateListParkinkResponse(parkingServices);
            responseVo.setData(listParkingResponses);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        } catch (Exception e) {
            responseVo.setMessage("Error when connect to database");
            return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public ResponseEntity<?> add(ParkingRequest parkingRequest) {
        ResponseVo responseVo = new ResponseVo();
        if (ObjectUtils.isEmpty(parkingRequest)) {
            responseVo.setStatus(false);
            responseVo.setMessage("invialid input, parking is empty");
            return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
        }
        if (ObjectUtils.isEmpty(parkingRequest.getDistric())) {
            responseVo.setStatus(false);
            responseVo.setMessage("Địa chỉ bãi đỗ xe không hợp lệ");
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        DistricRequest districRequest = parkingRequest.getDistric();
        DistrictsEntity existDistric = districtRepository.check_districts(districRequest.getCity(),
                districRequest.getWards(), districRequest.getDistrictName());
        if (ObjectUtils.isEmpty(existDistric)) {
            existDistric = DistrictsEntity.createDistricEntity(districRequest);
            if (existDistric == null) {
                responseVo = ResponseVeConvertUntil.createResponseVo(false, "Địa chỉ bạn nhập không đúng vui lòng nhập lại", null);
                return new ResponseEntity<>(responseVo, HttpStatus.OK);
            }
            districtRepository.save(existDistric);
        }
        existDistric = districtRepository.check_districts(districRequest.getCity(),
                districRequest.getWards(), districRequest.getDistrictName());
        DistricRequest district = parkingRequest.getDistric();
        DistrictsEntity districtsEntity = districtRepository.check_districts(district.getCity(), district.getWards(), district.getDistrictName());
        if (ObjectUtils.isEmpty(districtsEntity)) {
            districtsEntity = new DistrictsEntity();
            districtsEntity.setWards(district.getWards());
            districtsEntity.setCity(district.getCity());
            districtsEntity.setDistrict_Name(district.getDistrictName());
            districtRepository.save(districtsEntity);
            districtsEntity = districtRepository.check_districts(district.getCity(), district.getWards(), district.getDistrictName());
        }

        ParkingEntity parkingEntityExsit = parkingRepository.findParkingByAddressAndNameAndPhone(parkingRequest.getAddress(), parkingRequest.getName(), parkingRequest.getPhone());
        if (!ObjectUtils.isEmpty(parkingEntityExsit)) {
            responseVo.setStatus(false);
            responseVo.setMessage("Bãi đỗ xe đã tồn tại trong CSDL");
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        parkingEntityExsit = ParkingEntity.createParking(parkingRequest);
        parkingEntityExsit.setDistrictsEntity(districtsEntity);
        parkingRepository.save(parkingEntityExsit);
        responseVo = ResponseVeConvertUntil.createResponseVo(true, "Tạo mới thành công", parkingRequest);
        return new ResponseEntity<>(responseVo, HttpStatus.OK);
    }
}
