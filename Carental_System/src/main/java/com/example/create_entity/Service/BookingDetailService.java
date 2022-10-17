package com.example.create_entity.Service;

import com.example.create_entity.dto.Request.ListBookingDetailRequest;
import org.springframework.http.ResponseEntity;

import javax.transaction.Transactional;
import java.util.List;

public interface BookingDetailService {
    @Transactional
    public ResponseEntity<?> addList(List<ListBookingDetailRequest> listBookingDetailRequests);
}
