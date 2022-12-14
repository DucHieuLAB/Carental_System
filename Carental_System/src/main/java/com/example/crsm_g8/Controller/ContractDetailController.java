package com.example.crsm_g8.Controller;

import com.example.crsm_g8.Service.IService.ContractDetailService;
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
