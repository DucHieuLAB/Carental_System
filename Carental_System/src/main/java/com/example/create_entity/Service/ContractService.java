package com.example.create_entity.Service;

import com.example.create_entity.dto.FinishContractRequest;
import com.example.create_entity.dto.Request.*;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;


public interface ContractService {
    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    ResponseEntity<?> add(ContractRequest contractRequest);

    @Transactional
    ResponseEntity<?> cancelRenting(CancelContractRequest cancelContractRequestlong);

    @Transactional
    ResponseEntity<?> updateDriverAndRealPrice(ContractDriverRealPriceRequest contractDriverRealPriceRequest);

    @Transactional
    ResponseEntity<?> confirmDeposit(long id);

    @Transactional
    ResponseEntity<?> comfirmGetCar(GetCarReQuest getCarReQuest) throws Exception;

    @Transactional
    ResponseEntity<?> confirmDepositCustomer(long id);

    @Transactional
    ResponseEntity<?> addSurcharge(SurchargeRequest purchargeRequest);

    @Transactional
    ResponseEntity<?> returnCar(ReturnCarRequest returnCarRequest);

    @Transactional
    ResponseEntity<?> finishContract(long id) throws Exception;

    ResponseEntity<?> getContractById(Long id);

    ResponseEntity<?> getListContractByCustomerId(long customerId);

    ResponseEntity<?> getContractPaymentInf(long id);

    ResponseEntity<?> ListRequest_ofCustomer(Integer p);

    ResponseEntity<?> ListContract(Integer p);

    ResponseEntity<?> FilterByNameContract(String name, Integer HadDriver, Integer Status, Integer p);

    ResponseEntity<?> FilterByPhoneContract(String name, Integer HadDriver, Integer Status, Integer p);

    ResponseEntity<?> FilterByNameRequest(String name, Integer HadDriver, Integer Status, Integer p);

    ResponseEntity<?> FilterByPhoneRequest(String name, Integer HadDriver, Integer Status, Integer p);


    @Transactional
    ResponseEntity<?> addPayment(PaymentRequest paymentRequest) throws Exception;

    ResponseEntity<?> getListPaymentByCustomer(long id);
    @Transactional
    ResponseEntity<?> addPaymentByCustomer(CustomerTransactionRequest customerTransactionRequest) throws Exception;

    ResponseEntity<?> getExceptedPrice(ExceptedPriceRequest exceptedPriceRequest);

    ResponseEntity<?> getListSurchargeByContract(long contractId);
}
