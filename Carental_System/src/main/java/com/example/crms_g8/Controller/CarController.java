package com.example.crms_g8.Controller;

import com.example.crms_g8.Service.ServiceImpl.CarServiceImpl;
import com.example.crms_g8.dto.Request.CarRequest;
import com.example.crms_g8.dto.Request.DriverByCarByContractRequest;
import com.example.crms_g8.untils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/v1/car")
public class CarController {
    private final int defaultPage = 1;
    private final int defaultSize = 10;
    private final CarServiceImpl carService;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    public CarController(CarServiceImpl carService) {
        this.carService = carService;
    }

    @GetMapping("/capacity")
    public ResponseEntity<?> getListCapacity() {
        return carService.getListCapacity();
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody CarRequest carRequest) {
        return carService.add(carRequest);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody CarRequest carRequest) {
        return carService.update(carRequest);
    }

    @DeleteMapping("/delete/{carId}")
    public ResponseEntity<?> delete(@PathVariable Long carId) {
        return carService.delete(carId);
    }

    @GetMapping
    public ResponseEntity<?> listCar(@RequestParam(required = false) Integer pageIndex,
                                     @RequestParam(required = false) Integer pageSize,
                                     @RequestParam(required = false) String modelName,
                                     @RequestParam(required = false) Integer capacity,
                                     @RequestParam(required = false) Long parkingId) {
        if (pageIndex == null) {
            pageIndex = defaultPage;
        }
        if (pageSize == null) {
            pageSize = defaultSize;
        }
        return carService.findAll(pageIndex, pageSize, modelName, parkingId, capacity);
    }

    @GetMapping("/Detail/{CarPlateNumber}")
    public ResponseEntity<?> getCar(@PathVariable String CarPlateNumber) {
        return carService.findByPlateNumber(CarPlateNumber);
    }

    @GetMapping("/listDriver/")
    public ResponseEntity<?> getListDriver(@RequestParam String plateNumber,
                                           @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date expectedStartDate,
                                           @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date expectedEndDate) {
        DriverByCarByContractRequest driverByCarByContractRequest = new DriverByCarByContractRequest(plateNumber,expectedStartDate,expectedEndDate);
        return carService.getListDriverByCarPlateNumber(driverByCarByContractRequest);
    }

    @GetMapping("/SearchCarNoDriver")
    public ResponseEntity<?> searchCarForContract(@RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                                  @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
                                                  @RequestParam(required = true) Long PickupParkingId,
                                                  @RequestParam(required = true) Long ReturnParkingId) throws Exception {
        return carService.getListCarSelfDriver(startDate, endDate, PickupParkingId,ReturnParkingId);
    }

    @GetMapping("/SearchCarHadDriver")
    public ResponseEntity<?> searchCarForDadDriverContract(
                                                           @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                                           @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
                                                           @RequestParam(required = false) String CityName) {
        return carService.getListCarHadDriverContract(startDate,endDate,CityName);

    }

//
//    @GetMapping("/TopSeller")
//    public ResponseEntity<?> getListBestSeller() {
//        return carService.getListBestSeller();
//    }
}
