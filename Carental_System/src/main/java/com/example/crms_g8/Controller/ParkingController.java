package com.example.crms_g8.Controller;

import com.example.crms_g8.Service.ServiceImpl.ParkingServiceImpl;
import com.example.crms_g8.dto.Request.ParkingRequest;
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
    public ResponseEntity<?> add(@RequestBody ParkingRequest parkingRequest) {
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
    public ResponseEntity<?> GetListPaging(@RequestParam(required = false) Integer pageIndex,
                                           @RequestParam(required = false) Integer pageSize) {
        if (pageIndex == null) {
            pageIndex = defaultPage;
        }
        if (pageSize == null) {
            pageSize = defaultSize;
        }
        return parkingService.findAllPaging(pageIndex, pageSize);
    }

    @GetMapping("/Detail/{id}")
    public ResponseEntity<?>DetailParking(@PathVariable long id) {
        return parkingService.findById(id);
    }
}
