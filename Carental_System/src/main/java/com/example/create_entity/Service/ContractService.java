package com.example.create_entity.Service;

import com.example.create_entity.dto.Request.ContractRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;


public interface ContractService {
    @Transactional
    ResponseEntity<?> add(ContractRequest contractRequest);

    ResponseEntity<?> ListBooking(Integer p);
}
