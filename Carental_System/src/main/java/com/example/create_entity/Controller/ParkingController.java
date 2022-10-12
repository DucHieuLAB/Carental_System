package com.example.create_entity.Controller;

import com.example.create_entity.Service.CarServiceImpl;
import com.example.create_entity.Service.ParkingServiceImpl;
import com.example.create_entity.dto.Request.CarRequest;
import com.example.create_entity.dto.Request.ParkingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/parking")
public class ParkingController {
    private final int defaultPage = 1;
    private final int defaultSize = 10;
    private final ParkingServiceImpl parkingService;

    @Autowired
    public ParkingController(ParkingServiceImpl parkingService) {
        this.parkingService = parkingService;
    }

    @GetMapping(value = "/ListParkings")
    public ResponseEntity<?> getList() {
        return parkingService.getAll();
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> add(@RequestBody ParkingRequest parkingRequest){
        return parkingService.add(parkingRequest);
    }
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody ParkingRequest parkingRequest) {
        return parkingService.update(parkingRequest);
    }
    @DeleteMapping("/delete/{parkingId}")
    public ResponseEntity<?> delete(@PathVariable Long parkingId) {
        return parkingService.delete(parkingId);
    }

    @GetMapping
    public ResponseEntity<?> getlistPaging(@RequestParam(required = false) Integer pageIndex,
                                     @RequestParam(required = false) Integer pageSize) {
        if (pageIndex == null) {
            pageIndex = defaultPage;
        }
        if (pageSize == null) {
            pageSize = defaultSize;
        }
        return parkingService.findAllPaging(pageIndex, pageSize);
    }
}
