package com.example.create_entity.Service.IService;

import com.example.create_entity.Entity.ContractEntity;
import com.example.create_entity.dto.FinishContractRequest;
import com.example.create_entity.dto.Request.*;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface ContractService {
    ResponseEntity<?> getListContractByCustomerId(long customerId);

    ResponseEntity<?> getContractPaymentInf(long id);

    ResponseEntity<?> getListSurchargeByContract(long contractId);

    ResponseEntity<?> getListPaymentByStaff(long contractId);

    ResponseEntity<?> getExceptedPrice(ExceptedPriceRequest exceptedPriceRequest);

    @Transactional
    ResponseEntity<?> add(ContractRequest contractRequest) throws Error;

    ResponseEntity<?> getContractById(Long id);

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    ResponseEntity<?> cancelRenting(CancelContractRequest cancelContractRequestlong);

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    ResponseEntity<?> updateDriverAndRealPrice(ContractDriverRealPriceRequest contractDriverRealPriceRequest) throws Exception;

    ResponseEntity<?> getListPaymentByCustomer(long id);

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    ResponseEntity<?> addPaymentByCustomer(CustomerTransactionRequest customerTransactionRequest) throws Exception;

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    ResponseEntity<?> addPayment(PaymentRequest paymentRequest) throws Exception;

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    ResponseEntity<?> comfirmGetCar(GetCarReQuest getCarReQuest) throws Exception;

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    ResponseEntity<?> addSurcharge(SurchargeRequest purchargeRequest);

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    ResponseEntity<?> returnCar(ReturnCarRequest returnCarRequest);

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    ResponseEntity<?> finishContract(long id) throws Exception;

    ResponseEntity<?> ListRequest(Integer p);

    ResponseEntity<?> ListContract(Integer p);

    ResponseEntity<?> FilterByNameContract(String name, Integer HadDriver, Integer Status, Integer p);

    ResponseEntity<?> FilterByPhoneContract(String name, Integer HadDriver, Integer Status, Integer p);

    ResponseEntity<?> FilterByNameRequest(String name, Integer HadDriver, Integer Status, Integer p);

    ResponseEntity<?> FilterByPhoneRequest(String name, Integer HadDriver, Integer Status, Integer p);

    List<ContractEntity> getListInvalidContract();

    void save(ContractEntity contractEntity);
}