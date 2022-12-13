package com.example.create_entity.Controller;

import com.example.create_entity.Service.IService.ContractDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class ContractDetailController {
    @Autowired
    ContractDetailService bookingService;

    @RequestMapping(value = "/Contracts_Detail/View")
    public ResponseEntity<?> BookingDetail(@RequestParam(name = "id", required = false) Long id) {
        return bookingService.ListBookingDetail(id);
    }
}
