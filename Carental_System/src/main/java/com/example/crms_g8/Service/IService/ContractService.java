package com.example.crms_g8.Service.IService;

import com.example.crms_g8.Entity.ContractEntity;
import com.example.crms_g8.dto.Request.*;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface ContractService {
    ResponseEntity<?> getListContractByCustomerId(long customerId);

    ResponseEntity<?> getContractPayment(long id);

    ResponseEntity<?> getListSurchargeByContract(long contractId);

    ResponseEntity<?> getListPaymentByContract(long contractId);

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

    ResponseEntity<?>ManageContract(Integer p);

    ResponseEntity<?>ManageRequest(Integer p);

    ResponseEntity<?>SearchByNameContract(String name, Integer HadDriver, Integer Status, Integer p);

    ResponseEntity<?>SearchByPhoneContract(String name, Integer HadDriver, Integer Status, Integer p);

    ResponseEntity<?>SearchByNameRequest(String name, Integer HadDriver, Integer Status, Integer p);

    ResponseEntity<?>SearchByPhoneRequest(String name, Integer HadDriver, Integer Status, Integer p);

    List<ContractEntity> getListInvalidContract();

    void save(ContractEntity contractEntity);

    ResponseEntity<?> getListWarningContract(Integer p);

    ResponseEntity<?> ListContractChangeParking(Integer p);
}
