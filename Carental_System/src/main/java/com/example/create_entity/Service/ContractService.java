package com.example.create_entity.Service;

import com.example.create_entity.dto.Request.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;


public interface ContractService {
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    ResponseEntity<?> add(ContractRequest contractRequest);

    ResponseEntity<?> ListContract(Integer p);

    ResponseEntity<?> ListContract2(Integer p);

    ResponseEntity<?> FilterByName(String name, Integer HadDriver, Integer Status, Integer p);

    ResponseEntity<?> FilterByPhone(String name, Integer HadDriver, Integer Status, Integer p);

    ResponseEntity<?> getContractById(Long id);

    ResponseEntity<?> getListContractByCustomerId(long customerId);

    @Transactional
    ResponseEntity<?> cancelRenting(long id, int i);

    @Transactional
    ResponseEntity<?> updateDriverAndRealPrice(ContractDriverRealPriceRequest contractDriverRealPriceRequest);

//    @Transactional
//    ResponseEntity<?> updateRentailPrice()

//    @Transactional
//    ResponseEntity<?> updateDriver(ContractHadDriverRequest contractHadDriverRequest);

//    ResponseEntity<?> updateRealPrice(ContractRealPriceRequest contractRealPriceRequest);

//    @Transactional
//    ResponseEntity<?> update(ContractRequest contractRequest);
//
}
