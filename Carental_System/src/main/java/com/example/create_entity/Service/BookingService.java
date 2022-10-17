package com.example.create_entity.Service;

import com.example.create_entity.dto.Request.BookingRequest;
import org.springframework.http.ResponseEntity;

public interface BookingService   {

    ResponseEntity<?> add(BookingRequest bookingRequest);

    ResponseEntity<?> ListBooking(Integer p);
}
