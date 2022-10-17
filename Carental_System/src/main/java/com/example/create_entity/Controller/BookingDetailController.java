package com.example.create_entity.Controller;

import com.example.create_entity.Service.BookingDetailService;
import com.example.create_entity.Service.BookingDetailServiceImpl;
import com.example.create_entity.Service.BookingService;
import com.example.create_entity.dto.Response.BookingDetailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class BookingDetailController {


    @Autowired
    BookingDetailService bookingService;


    @RequestMapping(value = "/BookingDetail/View")
    public ResponseEntity<?> BookingDetail(@RequestParam(name = "id", required = false) Long id) {
        return bookingService.ListBookingDetail(id);
    }
}
