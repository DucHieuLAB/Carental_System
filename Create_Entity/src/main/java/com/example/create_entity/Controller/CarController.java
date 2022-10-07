package com.example.create_entity.Controller;

import com.example.create_entity.Service.CarService;
import com.example.create_entity.Service.CarServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/Car")
public class CarController {

   private final CarServiceImpl carService;

   @Autowired
   public CarController(CarServiceImpl carService) {this.carService = carService;}

    @GetMapping(value = "/Capacity")
    public ResponseEntity<?> getListCapacity(){
       return carService.getListCapacity();
    }








}
