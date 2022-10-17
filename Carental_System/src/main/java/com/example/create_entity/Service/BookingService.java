package com.example.create_entity.Service;

import com.example.create_entity.dto.Request.BookingRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

public interface BookingService  {
    @Transactional
    ResponseEntity<?> add(BookingRequest bookingRequest);
}
