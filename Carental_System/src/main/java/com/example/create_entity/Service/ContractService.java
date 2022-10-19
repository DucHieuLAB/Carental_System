package com.example.create_entity.Service;

import com.example.create_entity.dto.Request.ContractRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;


public interface ContractService {
    @Transactional
    ResponseEntity<?> add(ContractRequest contractRequest);

    ResponseEntity<?> ListContract(Integer p);

    ResponseEntity<?> FilterByName(String name, Integer p);

    ResponseEntity<?> FilterByPhone(String phone, Integer p);

    ResponseEntity<?> FilterByHadDriver(Integer p);

    ResponseEntity<?> FilterByNotHadDriver(Integer p);

    ResponseEntity<?> FilterByWaitingForProgressing(Integer p);

    ResponseEntity<?> FilterByWaitForConfirmation(Integer p);

    ResponseEntity<?> FilterByEffective(Integer p);

    ResponseEntity<?> FilterByActivate(Integer p);


    ResponseEntity<?> FilterByClose(Integer p);

    ResponseEntity<?> FilterByCancel(Integer p);




}
