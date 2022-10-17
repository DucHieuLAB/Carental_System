package com.example.create_entity.Service;

import com.example.create_entity.Entity.BookingDetailEntity;
import com.example.create_entity.dto.Request.ListBookingDetailRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookingDetailServiceImpl implements BookingDetailService{

    @Override
    public ResponseEntity<?> addList(List<ListBookingDetailRequest> listBookingDetailRequests) {
        return null;
    }

}
