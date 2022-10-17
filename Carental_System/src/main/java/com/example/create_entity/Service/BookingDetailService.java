package com.example.create_entity.Service;

import com.example.create_entity.Entity.BookingDetailEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

public interface  BookingDetailService {

    ResponseEntity<?>  ListBookingDetail(Long id);
}
