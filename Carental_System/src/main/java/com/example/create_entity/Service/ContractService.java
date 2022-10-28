package com.example.create_entity.Service;

import com.example.create_entity.dto.Request.ContractHadDriverRequest;
import com.example.create_entity.dto.Request.ContractRealPriceRequest;
import com.example.create_entity.dto.Request.ContractRequest;
import com.example.create_entity.dto.Request.ListContractDetailDriverRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;


public interface ContractService {
    @Transactional
    ResponseEntity<?> add(ContractRequest contractRequest);

    @Transactional
    ResponseEntity<?> updateDriver(ContractHadDriverRequest contractHadDriverRequest);

    ResponseEntity<?> ListContract(Integer p);

    ResponseEntity<?> FilterByName(String name, Integer HadDriver, Integer Status, Integer p);

    ResponseEntity<?> FilterByPhone(String name, Integer HadDriver, Integer Status, Integer p);

    @Transactional
    ResponseEntity<?> update(ContractRequest contractRequest);

    ResponseEntity<?> getContractById(Long id);

    ResponseEntity<?> updateRealPrice(ContractRealPriceRequest contractRealPriceRequest);

//    @Transactional
//    ResponseEntity<?> updateRentailPrice()
}
