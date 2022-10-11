package com.example.create_entity.Controller;

import com.example.create_entity.Service.CarService;
import com.example.create_entity.Service.CarServiceImpl;
import com.example.create_entity.dto.Request.BrandRequest;
import com.example.create_entity.dto.Request.CarRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "/capacity")
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


}
