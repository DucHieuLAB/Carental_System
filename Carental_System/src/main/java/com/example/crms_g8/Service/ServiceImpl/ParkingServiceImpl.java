package com.example.crms_g8.Service.ServiceImpl;

import com.example.crms_g8.Entity.DistrictsEntity;
import com.example.crms_g8.Entity.ParkingEntity;
import com.example.crms_g8.Repository.DistrictRepository;
import com.example.crms_g8.Repository.ParkingRepository;
import com.example.crms_g8.Service.IService.ParkingService;
import com.example.crms_g8.dto.Request.DistricRequest;
import com.example.crms_g8.dto.Request.ParkingRequest;
import com.example.crms_g8.dto.Response.ListParkingResponse;
import com.example.crms_g8.dto.Response.ParkingResponse;
import com.example.crms_g8.dto.Response.ResponseVo;
import com.example.crms_g8.untils.ResponseVeConvertUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
            responseVo.setStatus(true);
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
        if (ObjectUtils.isEmpty(parkingRequest.getDistrict())) {
            responseVo.setStatus(false);
            responseVo.setMessage("Địa chỉ bãi đỗ xe không hợp lệ");
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        DistricRequest districRequest = parkingRequest.getDistrict();
        DistrictsEntity existDistric = districtRepository.check_districts(districRequest.getCity(),
                districRequest.getWards(),
                districRequest.getDistrictName());
        if (ObjectUtils.isEmpty(existDistric)) {
            existDistric = DistrictsEntity.createDistricEntity(districRequest);
            districtRepository.save(existDistric);
        }
        DistrictsEntity parkingDistric = districtRepository.check_districts(districRequest.getCity(),
                districRequest.getWards(),
                districRequest.getDistrictName());
        List<ParkingEntity> parkingEntityExsits = parkingRepository.findParkingByAddressOrNameOrPhone(parkingRequest.getAddress(), parkingRequest.getName(), parkingRequest.getPhone());
        if (!ObjectUtils.isEmpty(parkingEntityExsits) || !parkingEntityExsits.isEmpty()) {
            responseVo.setStatus(false);
            responseVo.setMessage("Thông tin bãi đỗ (địa chỉ,Tên,Số điện thoại) đã tồn tại trong CSDL");
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        ParkingEntity newParkingEntity ;
        newParkingEntity = ParkingEntity.createParking(parkingRequest);
        newParkingEntity.setDistrictsEntity(parkingDistric);
        parkingRepository.save(newParkingEntity);
        ParkingResponse parkingResponse = ParkingResponse.createParkingResponse(newParkingEntity);
        responseVo = ResponseVeConvertUntil.createResponseVo(true, "Tạo mới thành công", parkingResponse);
        return new ResponseEntity<>(responseVo, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> findById(long id) {
        ResponseVo responseVo = null;
        Optional<ParkingEntity> parkingEntity = parkingRepository.findById(id);
        if (!parkingEntity.isPresent()){
            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Không tìm thấy bãi đỗ", null);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        ParkingResponse response = ParkingResponse.createParkingResponse(parkingEntity.get());
        responseVo = ResponseVeConvertUntil.createResponseVo(true, "Tạo mới thành công", response);
        return new ResponseEntity<>(responseVo, HttpStatus.OK);
    }

    public ResponseEntity<?> update(ParkingRequest parkingRequest) {
        ResponseVo responseVo = null;
        if (ObjectUtils.isEmpty(parkingRequest)) {
            responseVo.setStatus(false);
            responseVo.setMessage("Invalid input");
        }
        Optional<ParkingEntity> exsitParking = parkingRepository.findByIdAndStatus(parkingRequest.getId());
        if (!exsitParking.isPresent()) {
            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Bãi Đỗ không tồn tại", null);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        ParkingEntity parkingEntity = exsitParking.get();
        ParkingEntity checkName = parkingRepository.findParkingByName(parkingRequest.getName());
        if(!ObjectUtils.isEmpty(checkName)){
            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Tên Bãi đỗ đã được lấy", null);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        try {
            DistricRequest districRequest = parkingRequest.getDistrict();
            DistrictsEntity existDistric = districtRepository.check_districts(districRequest.getCity(),
                    districRequest.getWards(),
                    districRequest.getDistrictName());
            if (ObjectUtils.isEmpty(existDistric)) {
                if (districRequest == null) {
                    responseVo = ResponseVeConvertUntil.createResponseVo(false, "Địa chỉ bạn nhập không đúng vui lòng nhập lại", null);
                    return new ResponseEntity<>(responseVo, HttpStatus.OK);
                }
                existDistric = DistrictsEntity.createDistricEntity(districRequest);
                districtRepository.save(existDistric);
            }
            DistrictsEntity parkingDistric = districtRepository.check_districts(districRequest.getCity(),
                    districRequest.getWards(),
                    districRequest.getDistrictName());
            parkingEntity = ParkingEntity.createParking(parkingRequest);
            parkingEntity.setDistrictsEntity(parkingDistric);
            parkingRepository.save(parkingEntity);
            ParkingResponse parkingResponse = ParkingResponse.createParkingResponse(parkingEntity);
            responseVo = ResponseVeConvertUntil.createResponseVo(true, "Cập Nhật Thành Công",parkingResponse );
            return new ResponseEntity<>(responseVo, HttpStatus.OK);


        } catch (Exception e) {
            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Lỗi khi cố gắng cập nhật bãi đỗ xe", null);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
    }

    public ResponseEntity<?> delete(Long parkingId) {
        ResponseVo responseVo = null;
        Optional<ParkingEntity> exsitParking = parkingRepository.findByIdAndStatus(parkingId);
        if (!exsitParking.isPresent()){
            responseVo = ResponseVeConvertUntil.createResponseVo(false,"Không tìm thấy bãi đỗ xe",null);
            return new ResponseEntity<>(responseVo,HttpStatus.OK);
        }
        try {
            exsitParking.get().setStatus(0);
            parkingRepository.save(exsitParking.get());
            responseVo = ResponseVeConvertUntil.createResponseVo(true,"Đã xóa thành công",null);
            return new ResponseEntity<>(responseVo,HttpStatus.OK);
        }catch (Exception e){
            responseVo = ResponseVeConvertUntil.createResponseVo(true,"Lỗi khi cố gắng xóa bãi đỗ xe",null);
            return new ResponseEntity<>(responseVo,HttpStatus.BAD_REQUEST);
        }


    }

    public ResponseEntity<?> findAllPaging(Integer pageIndex, Integer pageSize) {
        ResponseVo responseVo = null;
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
        Page<ParkingEntity> parkingPage = parkingRepository.findAllPaging(pageable);
        Map<String,Object> responseData = new HashMap<>();
        if(parkingPage.isEmpty()){
            responseData.put("Parkings",parkingPage.getContent());
            responseData.put("Total Record", 0);
            responseVo = ResponseVeConvertUntil.createResponseVo(false,
                    "Không tìm thấy kết quả liên quan tới từ khóa",
                    responseData);
            return new ResponseEntity<>(responseVo,HttpStatus.OK);
        }
        List<ListParkingResponse> listParkingResponses = ListParkingResponse.CreateListParkinkResponse(parkingPage.getContent());
        responseData.put("Parkings",listParkingResponses);
        responseData.put("currentPage",pageIndex);
        responseData.put("totalRecord",parkingPage.getTotalElements());
        responseData.put("pageSize", parkingPage.getSize());
        responseData.put("totalPage", parkingPage.getTotalPages());
        responseVo = ResponseVeConvertUntil.createResponseVo(true,"Danh Sách Bãi Đỗ Xe",responseData);
        return new ResponseEntity<>(responseVo,HttpStatus.OK);
    }


}
