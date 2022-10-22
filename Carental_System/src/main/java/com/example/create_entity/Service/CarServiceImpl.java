package com.example.create_entity.Service;

import com.example.create_entity.Entity.BrandEntity;
import com.example.create_entity.Entity.CarEntity;
import com.example.create_entity.Entity.CarImageEntity;
import com.example.create_entity.Entity.ParkingEntity;
import com.example.create_entity.Repository.BrandRepository;
import com.example.create_entity.Repository.CarRepository;
import com.example.create_entity.Repository.ParkingRepository;
import com.example.create_entity.dto.Request.CarRequest;
import com.example.create_entity.dto.Response.*;
import com.example.create_entity.untils.ResponseVeConvertUntil;
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
public class CarServiceImpl implements CarService {
    @Autowired
    CarRepository carRepository;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    ParkingRepository parkingRepository;

    @Autowired
    CarImageServiceImpl carImageService;

    @Override
    public ResponseEntity<?> getListCapacity() {
        ResponseVo responseVo = new ResponseVo();
        List<Integer> result = carRepository.getListCapacity();
        Map<String, Object> map = new HashMap<>();
        if (result.isEmpty()) {
            responseVo.setMessage("Danh sach trống");
            responseVo.setData(null);
            return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
        }
        map.put("capacity", result);
        responseVo.setMessage("");
        responseVo.setData(map);
        return new ResponseEntity<>(responseVo, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> add(CarRequest carRequest) {
        ResponseVo responseVo = new ResponseVo();
        if (ObjectUtils.isEmpty(carRequest)) {
            responseVo.setStatus(false);
            responseVo.setMessage("invialid input, car is empty");
            return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
        }
        CarEntity exsitCar = carRepository.findCarEntityByPlateNumber(carRequest.getPlateNumber());
        if (!ObjectUtils.isEmpty(exsitCar)) {
            responseVo.setStatus(false);
            responseVo.setMessage("Biển số xe đã tồn tại");
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        try {
            CarEntity newCarEntity = CarEntity.createCarEntity(carRequest);
            BrandEntity brandEntity = brandRepository.findBrandEntityById(carRequest.getBrandId());
            Optional<ParkingEntity> parkingEntity = parkingRepository.findById(carRequest.getParkingId());
            if (ObjectUtils.isEmpty(brandEntity)) {
                responseVo.setMessage("Hãng xe không hợp lệ");
                return new ResponseEntity<>(responseVo, HttpStatus.OK);
            }
            if (!parkingEntity.isPresent()) {
                responseVo.setMessage("Bãi đỗ xe không hợp lệ");
                return new ResponseEntity<>(responseVo, HttpStatus.OK);
            }
            newCarEntity.setBrand(brandEntity);
            newCarEntity.setParking(parkingEntity.get());
            carRepository.save(newCarEntity);
            carImageService.addList(carRequest.getImgs(), newCarEntity);
            CarEntity newCar = carRepository.findCarEntityByPlateNumber(newCarEntity.getPlateNumber());
            List<CarImageEntity> lsCar = carImageService.getListCarByPlateNumber(newCarEntity.getPlateNumber());
            newCar.setCarImageEntities(lsCar);
            CarResponseDetailResponse response = CarEntity.createCarResponseDetailResponse(newCar);
            responseVo.setStatus(true);
            responseVo.setMessage("Tạo mới thành công");
            responseVo.setData(response);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        } catch (Exception e) {
            responseVo.setStatus(false);
            responseVo.setMessage("Lỗi khi tạo mới Xe");
            responseVo.setData(e.getMessage());
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }

    }

    @Override
    public ResponseEntity<?> findAll(int pageIndex, int pageSize, String modelName, Long parkingId, Integer capacity) {
        Pageable pageable = PageRequest.of(pageIndex - 1, pageSize);
        Page<CarEntity> carPage = null;
        if (ObjectUtils.isEmpty(modelName)) {
            carPage = carRepository.findCarEntityByStatus(pageable);
        } else {
            carPage = carRepository.findBySearch("%" + modelName + "%", parkingId, capacity, pageable);
        }
        ResponseVo responseVo = new ResponseVo();
        Map<String, Object> responseData = new HashMap<>();
        if (carPage.isEmpty()) {
            responseData.put("cars", carPage.getContent());
            responseData.put("Total Record", 0);
            responseVo.setMessage("Không tìm thấy kết quả liên quan tới từ khóa");
            responseVo.setData(responseData);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        List<ListCarResponse> cars = ListCarResponse.createListCarPesponse(carPage.getContent());
        for (ListCarResponse c : cars) {
            List<ListCarImageResponse> carImageResponses = ListCarImageResponse.createListCarImagePesponse(carImageService.getListCarByPlateNumber(c.getPlateNumber()));
            c.setListImg(carImageResponses);
        }
        responseVo.setMessage("List Car By Search");
        responseData.put("cars", cars);
        responseData.put("modelName", modelName);
        responseData.put("parkingId", parkingId);
        responseData.put("capacity", capacity);
        responseData.put("currentPage", pageIndex);
        responseData.put("totalRecord", carPage.getTotalElements());
        responseData.put("pageSize", carPage.getSize());
        responseData.put("totalPage", carPage.getTotalPages());
        responseVo.setData(responseData);
        return new ResponseEntity<>(responseVo, HttpStatus.OK);

    }

    @Override
    public ResponseEntity<?> update(CarRequest carRequest) {
        ResponseVo responseVo = new ResponseVo();
        if (ObjectUtils.isEmpty(carRequest)) {
            responseVo.setStatus(false);
            responseVo.setMessage("Invalid input");
        }
        CarEntity exsitCar = carRepository.findCarEntityById(carRequest.getId());
        if (ObjectUtils.isEmpty(exsitCar)) {
            responseVo.setStatus(false);
            responseVo.setMessage("Mã số Xe không tồn tại");
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        try {
            if(!carRequest.getPlateNumber().equals(exsitCar.getPlateNumber())){
                CarEntity checkPlateNumber = carRepository.findCarEntityByPlateNumber(carRequest.getPlateNumber());

                if(!ObjectUtils.isEmpty(checkPlateNumber)){
                    responseVo = ResponseVeConvertUntil.createResponseVo(false,"Biển số xe đã tông tại trong CSDL",null);
                    return new ResponseEntity<>(responseVo,HttpStatus.OK);
                }
            }
            exsitCar = CarEntity.createCarEntity(carRequest);
            BrandEntity brandEntity = brandRepository.findBrandEntityById(carRequest.getBrandId());
            Optional<ParkingEntity> parkingEntity = parkingRepository.findById(carRequest.getParkingId());
            if (ObjectUtils.isEmpty(brandEntity)) {
                responseVo.setMessage("Hãng xe không hợp lệ");
                return new ResponseEntity<>(responseVo, HttpStatus.OK);
            }
            if (!parkingEntity.isPresent()) {
                responseVo.setMessage("Bãi đỗ xe không hợp lệ");
                return new ResponseEntity<>(responseVo, HttpStatus.OK);
            }
            exsitCar.setBrand(brandEntity);
            exsitCar.setParking(parkingEntity.get());
            carRepository.save(exsitCar);
            carImageService.updateList(carRequest.getImgs(),exsitCar);
            exsitCar = carRepository.findCarEntityByPlateNumber(carRequest.getPlateNumber());
            CarResponseDetailResponse response = CarEntity.createCarResponseDetailResponse(exsitCar);
            responseVo.setStatus(true);
            responseVo.setMessage("Cập nhật thông tin thành công");
            responseVo.setData(response);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        } catch (Exception e) {
            responseVo = ResponseVeConvertUntil.createResponseVo(true, "lỗi khi sửa thông tin Car", e.getMessage());
            return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> delete(Long carId) {
        ResponseVo responseVo = new ResponseVo();
        CarEntity carEntity = carRepository.findCarEntityById(carId);
        if (ObjectUtils.isEmpty(carEntity)) {
            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Không tìm thấy xe tương ứng", null);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        try {
            carEntity.setStatus(0);
            carRepository.save(carEntity);
            responseVo = ResponseVeConvertUntil.createResponseVo(true, "Xóa xe thành công", carId);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        } catch (Exception e) {
            responseVo = ResponseVeConvertUntil.createResponseVo(true, "lỗi khi câp nhật thông tin xe", e.getMessage());
            return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> findById(Long brandId, int pageIndex, int pageSize) {
        return null;
    }

    @Override
    public ResponseEntity<?> findByPlateNumber(String carPlateNumber) {
        ResponseVo responseVo = null;
        CarEntity entity = carRepository.findCarEntityByPlateNumber(carPlateNumber);
        if(ObjectUtils.isEmpty(entity)){
           responseVo = ResponseVeConvertUntil.createResponseVo(false,"Không tìm thấy dữ liệu",null);
            return new ResponseEntity<>(responseVo,HttpStatus.OK);
        }
        CarResponseDetailResponse response = CarEntity.createCarResponseDetailResponse(entity);
        responseVo = ResponseVeConvertUntil.createResponseVo(true,"Get dữ liệu thành công",response);
        return new ResponseEntity<>(responseVo,HttpStatus.OK);
    }
}
