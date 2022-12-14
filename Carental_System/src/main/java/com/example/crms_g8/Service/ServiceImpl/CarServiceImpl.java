package com.example.crms_g8.Service.ServiceImpl;

import com.example.crms_g8.Entity.*;
import com.example.crms_g8.Repository.*;
import com.example.crms_g8.Service.IService.CarService;
import com.example.crms_g8.dto.Request.CarRequest;
import com.example.crms_g8.dto.Request.DriverByCarByContractRequest;
import com.example.crms_g8.dto.Response.*;
import com.example.crms_g8.untils.ResponseVeConvertUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;

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

    @Autowired
    LicenseRepository licenseRepository;

    @Autowired
    DriverRepository driverRepository;

    @Autowired
    ContractRepository contractRepository;

    @Autowired
    ContractDetailRepository contractDetailRepository;

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
            LicenseTypeEntity licenseTypeEntity = licenseRepository.getLicenseById(carRequest.getLicenseId());
            if (ObjectUtils.isEmpty(brandEntity)) {
                responseVo.setMessage("Hãng xe không hợp lệ");
                return new ResponseEntity<>(responseVo, HttpStatus.OK);
            }
            if (!parkingEntity.isPresent()) {
                responseVo.setMessage("Bãi đỗ xe không hợp lệ");
                return new ResponseEntity<>(responseVo, HttpStatus.OK);
            }
            if (ObjectUtils.isEmpty(licenseTypeEntity)) {
                responseVo.setMessage("Thông tin bằng lái chưa hợp lệ");
                return new ResponseEntity<>(responseVo, HttpStatus.OK);
            }
            newCarEntity.setBrand(brandEntity);
            newCarEntity.setParking(parkingEntity.get());
            newCarEntity.setLicenseTypeEntity(licenseTypeEntity);
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
        responseVo.setStatus(true);
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
            if (!carRequest.getPlateNumber().equals(exsitCar.getPlateNumber())) {
                CarEntity checkPlateNumber = carRepository.findCarEntityByPlateNumber(carRequest.getPlateNumber());
                if (!ObjectUtils.isEmpty(checkPlateNumber)) {
                    responseVo = ResponseVeConvertUntil.createResponseVo(false, "Biển số xe đã tông tại trong CSDL", null);
                    return new ResponseEntity<>(responseVo, HttpStatus.OK);
                }
            }
            exsitCar = CarEntity.createCarEntity(carRequest);
            BrandEntity brandEntity = brandRepository.findBrandEntityById(carRequest.getBrandId());
            Optional<ParkingEntity> parkingEntity = parkingRepository.findById(carRequest.getParkingId());
            LicenseTypeEntity licenseTypeEntity = licenseRepository.getLicenseById(carRequest.getLicenseId());
            if (ObjectUtils.isEmpty(brandEntity)) {
                responseVo.setMessage("Hãng xe không hợp lệ");
                return new ResponseEntity<>(responseVo, HttpStatus.OK);
            }
            if (!parkingEntity.isPresent()) {
                responseVo.setMessage("Bãi đỗ xe không hợp lệ ");
                return new ResponseEntity<>(responseVo, HttpStatus.OK);
            }
            if (ObjectUtils.isEmpty(licenseTypeEntity)) {
                responseVo.setMessage("Không tìm thấy bằng lái Id = " + carRequest.getLicenseId());
                return new ResponseEntity<>(responseVo, HttpStatus.OK);
            }
            exsitCar.setBrand(brandEntity);
            exsitCar.setParking(parkingEntity.get());
            exsitCar.setLicenseTypeEntity(licenseTypeEntity);
            carRepository.save(exsitCar);
            carImageService.updateList(carRequest.getImgs(), exsitCar);
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
        if (ObjectUtils.isEmpty(entity)) {
            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Không tìm thấy dữ liệu", null);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        CarResponseDetailResponse response = CarEntity.createCarResponseDetailResponse(entity);
        responseVo = ResponseVeConvertUntil.createResponseVo(true, "Get dữ liệu thành công", response);
        return new ResponseEntity<>(responseVo, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getListDriverByCarPlateNumber(DriverByCarByContractRequest driverByCarByContractRequest) {
        ResponseVo responseVo = null;
        // validate
        CarEntity carEntity = carRepository.findCarEntityByPlateNumber(driverByCarByContractRequest.getPlateNumber());
        if (ObjectUtils.isEmpty(carEntity)) {
            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Biển số xe không đúng Xe:" + driverByCarByContractRequest.getPlateNumber(), null);
            return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
        }
        // get list driver had license valid
        List<DriverEntity> driverEntityList = driverRepository.getDriverByPlateNumber(driverByCarByContractRequest.getPlateNumber());
        if (driverEntityList.isEmpty()) {
            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Không có tài xế nào khả dụng", null);
            return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
        }
        // if driver dont have any contract from now accepted
        List<DriverEntity> listPass = new ArrayList<>();
        for (DriverEntity entity : driverEntityList) {
            Date currendate = new Date(System.currentTimeMillis());
            boolean hadNextStep = true;
            List<ContractDetailEntity> checkHadContractInvalid = contractDetailRepository.checkHadAnyContract(entity.getId(), currendate);
            if (checkHadContractInvalid.size() <= 0) {
                listPass.add(entity);
                hadNextStep = false;
            }
            if (hadNextStep) {
                // if driver dont have any contract betwenn start date and end date invalid
                ContractEntity checkDriverHadInvalidStartAndEndDate = contractRepository.findInvalidDateBookingDriver(entity.getId(), driverByCarByContractRequest.getExpectedStartDate(), driverByCarByContractRequest.getExpectedEndDate());
                if (ObjectUtils.isEmpty(checkDriverHadInvalidStartAndEndDate)) {
                    listPass.add(entity);
                }
            }
        }
        // response
        if (listPass.size() <= 0) {
            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Danh sách tài xế xe :" + driverByCarByContractRequest.getPlateNumber() + " trống", null);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        List<ListDriverByCarAndContractResponse> listDriverByCarAndContractResponses = ListDriverByCarAndContractResponse.createResponse(listPass);
        responseVo = ResponseVeConvertUntil.createResponseVo(true, "Danh sách tài xế xe :" + driverByCarByContractRequest.getPlateNumber(), listDriverByCarAndContractResponses);
        return new ResponseEntity<>(responseVo, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getListCarSelfDriver(Date startDate, Date endDate, Long pickupParkingId, Long returnParkingId) {
        List<CarEntity> ListCars = null;
        List<CarEntity> result = new ArrayList<>();
        //Create data response Map<String,Objet>
        Map<String, Object> responseData = new HashMap<>();
        // Get list Cars
        ListCars = carRepository.findAll();
        if (ListCars.isEmpty()) {
            ResponseVo responseVo = ResponseVeConvertUntil.createResponseVo(false, "Danh sac cach xe trong", null);
            return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
        }
        List<CarEntity> passValidDate = new ArrayList<>();
        for (CarEntity carEntity : ListCars) {
            // check car had contract detail or not
            boolean hadNextStep = true;
            List<ContractDetailEntity> checkCarHadInDetailContract = contractDetailRepository.findContractDetailByCar(carEntity.getId(),new Date(System.currentTimeMillis()));
            if (checkCarHadInDetailContract.size() <= 0 ) {
                if (carEntity.getParking().getId() == pickupParkingId){
                    result.add(carEntity);
                }
                hadNextStep = false;
            }
            //Check car Valid in date
            if (hadNextStep) {
                CarEntity checkCarHadInvalidContract = carRepository.checkCarValidInTime(startDate, endDate, carEntity.getPlateNumber());
                if (ObjectUtils.isEmpty(checkCarHadInvalidContract)) {
                    passValidDate.add(carEntity);
                }
            }
        }
        // Check select Pickup Parking
        List<CarEntity> listPassDateAndPickupParking = new ArrayList<>();
        for (CarEntity carEntity : passValidDate) {
            // get Contract near excepted pick Up date
            Optional<ContractEntity> contractEntity = contractRepository.findContractByPlateNumberAndStartDate(carEntity.getPlateNumber(), startDate);
            if (contractEntity.isPresent()){
                if (contractEntity.get().getReturn_parking().getId() == pickupParkingId) {
                    listPassDateAndPickupParking.add(carEntity);
                }
            }else {
                listPassDateAndPickupParking.add(carEntity);
            }
        }
        // Check select Return Parking
        for (CarEntity carEntity : listPassDateAndPickupParking) {
            Optional<ContractEntity> contractEntity = contractRepository.findContractByPlateNumberAndEndDate(carEntity.getPlateNumber(), endDate);
            boolean hadFuterContract = true;
            if (!contractEntity.isPresent()){
                result.add(carEntity);
                hadFuterContract = false;
            }
            if (hadFuterContract){
                if (contractEntity.get().getPickup_parking().getId() == returnParkingId) {
                    result.add(carEntity);
                }
            }
        }

        if (result.size() <= 0) {
            responseData.put("cars", null);
            responseData.put("startDate", startDate);
            responseData.put("endDate", endDate);
            responseData.put("PickupParkingId", pickupParkingId);
            responseData.put("ReturnParkingId", returnParkingId);
            responseData.put("totalRecord", 0);
            ResponseVo responseVo = ResponseVeConvertUntil.createResponseVo(false, "không tìm thấy danh sách", responseData);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        List<ListCarResponse> cars = ListCarResponse.createListCarPesponse(result);
        for (ListCarResponse c : cars) {
            List<ListCarImageResponse> carImageResponses = ListCarImageResponse.createListCarImagePesponse(carImageService.getListCarByPlateNumber(c.getPlateNumber()));
            c.setListImg(carImageResponses);
        }
        responseData.put("cars", cars);
        responseData.put("startDate", startDate);
        responseData.put("endDate", endDate);
        responseData.put("PickupParkingId", pickupParkingId);
        responseData.put("ReturnParkingId", returnParkingId);
        responseData.put("totalRecord", ListCars.size());
        ResponseVo responseVo = ResponseVeConvertUntil.createResponseVo(true, "Danh sách xe", responseData);
        return new ResponseEntity<>(responseVo, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getListCarHadDriverContract(Date startDate, Date endDate, String cityName) {
        // validate
        List<CarEntity> ListCars = null;
        List<CarEntity> result = new ArrayList<>();
        //Create data response Map<String,Objet>
        Map<String, Object> responseData = new HashMap<>();
        // Get list Cars valid
        ListCars = carRepository.findAll();
        List<CarEntity> listPassValidStartDateAndEndDate  = new ArrayList<>();
        for (CarEntity carEntity : ListCars) {
            // check car had contract detail or not
            boolean hadNextStep = true;
            List<ContractDetailEntity> checkCarHadInDetailContract = contractDetailRepository.findContractDetailByCar(carEntity.getId(),new Date(System.currentTimeMillis()));
            if (checkCarHadInDetailContract.size() <= 0) {
                result.add(carEntity);
                hadNextStep = false;
            }
            if (hadNextStep){
                CarEntity checkCarHadInvalidContract = carRepository.checkCarValidInTime(startDate, endDate, carEntity.getPlateNumber());
                if (ObjectUtils.isEmpty(checkCarHadInvalidContract) && hadNextStep) {
                    listPassValidStartDateAndEndDate.add(carEntity);
                }
            }
        }
        for (CarEntity carEntity : listPassValidStartDateAndEndDate){
            Optional<ContractEntity> contractEntity = contractRepository.findContractByPlateNumberAndStartDate(carEntity.getPlateNumber(), startDate);
            if (contractEntity.isPresent()){
                if (contractEntity.get().getReturn_parking().getDistrictsEntity().getCity().equals(cityName)) {
                    result.add(carEntity);
                }
            }else {
                if (carEntity.getParking().getDistrictsEntity().getCity().equals(cityName)){
                    result.add(carEntity);
                }
            }

        }
        if (result.size() <= 0) {
            responseData.put("cars", null);
            responseData.put("startDate", startDate);
            responseData.put("endDate", endDate);
            responseData.put("city", cityName);
            responseData.put("totalRecord", 0);
            ResponseVo responseVo = ResponseVeConvertUntil.createResponseVo(false, "không tìm thấy danh sách", responseData);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        List<ListCarResponse> cars = ListCarResponse.createListCarPesponse(result);
        for (ListCarResponse c : cars) {
            List<ListCarImageResponse> carImageResponses = ListCarImageResponse.createListCarImagePesponse(carImageService.getListCarByPlateNumber(c.getPlateNumber()));
            c.setListImg(carImageResponses);
        }
        responseData.put("cars", cars);
        responseData.put("startDate", startDate);
        responseData.put("endDate", endDate);
        responseData.put("city", cityName);
        responseData.put("totalRecord", cars.size());
        ResponseVo responseVo = ResponseVeConvertUntil.createResponseVo(true, "Danh sách xe", responseData);
        return new ResponseEntity<>(responseVo, HttpStatus.OK);
    }

//    @Override
//    public ResponseEntity<?> getListBestSeller() {
//        ResponseVo responseVo = null;
//        List<CarEntity> listCar = carRepository.getListCarBestSeller();
//        return null;
//    }
}
