package com.example.create_entity.Controller;

import com.example.create_entity.Service.CarService;
import com.example.create_entity.Service.CarServiceImpl;
import com.example.create_entity.dto.Request.BrandRequest;
import com.example.create_entity.dto.Request.CarRequest;
import com.example.create_entity.dto.Request.DriverByCarByContractRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/v1/car")
public class CarController {
    private final int defaultPage = 1;
    private final int defaultSize = 10;
    private final CarServiceImpl carService;

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
                                                  @RequestParam(required = true) Long ReturnParkingId) {

        return carService.getListCarSelfDriver(startDate, endDate, PickupParkingId,ReturnParkingId);
    }


    @GetMapping("/SearchCarHadDriver")
    public ResponseEntity<?> searchCarForDadDriverContract(@RequestParam(required = false) Integer pageIndex,
                                                           @RequestParam(required = false) Integer pageSize,
                                                           @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                                           @RequestParam(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
                                                           @RequestParam(required = false) Long parkingId,
                                                           @RequestParam(required = false) String CityName) {
        if (pageIndex == null) {
            pageIndex = defaultPage;
        }
        if (pageSize == null) {
            pageSize = defaultSize;
        }
        return carService.getListCarHadDriverContract(pageIndex, pageSize,startDate,endDate,parkingId,CityName);

    }

//
//    @GetMapping("/TopSeller")
//    public ResponseEntity<?> getListBestSeller() {
//        return carService.getListBestSeller();
//    }
}
