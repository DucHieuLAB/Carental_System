package com.example.create_entity.Controller;

import com.example.create_entity.Service.BookingService;
import com.example.create_entity.dto.Request.BookingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/booking")
public class BookingController {
    @Autowired
    BookingService bookingService;

    @PostMapping
    public ResponseEntity<?> add(@RequestBody BookingRequest bookingRequest)
    {
        return bookingService.add(bookingRequest);
    }
}
