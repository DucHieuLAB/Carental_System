package com.example.create_entity.Controller;

import com.example.create_entity.Service.ContractServiceImpl;
import com.example.create_entity.dto.FinishContractRequest;
import com.example.create_entity.dto.Request.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/contract")
public class ContractController {

    private final ContractServiceImpl contractService;

    @Autowired
    public ContractController(ContractServiceImpl contractService1) {
        this.contractService = contractService1;
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody ContractRequest contractRequest) {
        return contractService.add(contractRequest);
    }

    @RequestMapping(value = "/ListRequest", method = RequestMethod.GET)
    public ResponseEntity<?> List(@RequestParam(value = "p", required = false) Integer p) {
        return contractService.ListRequest_ofCustomer(p);
    }

    @RequestMapping(value = "/ListContract", method = RequestMethod.GET)
    public ResponseEntity<?> List2(@RequestParam(value = "p", required = false) Integer p) {
        return contractService.ListContract(p);
    }

    @RequestMapping(value = "/SearchContract", method = RequestMethod.GET)
    public ResponseEntity<?> FilterContract(@RequestParam(required = false) Integer p,
                                    @RequestParam(required = false) String name,
                                    @RequestParam(required = false) Integer HadDriver,
                                    @RequestParam(required = false) Integer Status,
                                    @RequestParam(required = false) String phone) {
        if (phone != null && name == null) {
            return contractService.FilterByPhoneContract(phone, HadDriver, Status, p);
        }
        return contractService.FilterByNameContract(name, HadDriver, Status, p);
    }

    @RequestMapping(value = "/SearchRequest", method = RequestMethod.GET)
    public ResponseEntity<?> FilterRequest(@RequestParam(required = false) Integer p,
                                    @RequestParam(required = false) String name,
                                    @RequestParam(required = false) Integer HadDriver,
                                    @RequestParam(required = false) Integer Status,
                                    @RequestParam(required = false) String phone) {
        if (phone != null && name == null) {
            return contractService.FilterByPhoneRequest(phone, HadDriver, Status, p);
        }
        return contractService.FilterByNameRequest(name, HadDriver, Status, p);
    }

    @GetMapping("/Detail/{id}")
    public ResponseEntity<?> getContract(@PathVariable long id) {
        return contractService.getContractById(id);
    }

    @PutMapping("/rentalPrice")
    public ResponseEntity<?> getPrice(@RequestBody ExceptedPriceRequest exceptedPriceRequest){
        return contractService.getExceptedPrice(exceptedPriceRequest);
    }

    @GetMapping("/ListContract/{CustomerId}")
    public ResponseEntity<?> getListResponseEntityByCustomerId(@PathVariable long CustomerId) {
        return contractService.getListContractByCustomerId(CustomerId);
    }

    @PutMapping("/update/UpdateDriverAndRealPrice")
    public ResponseEntity<?> updateDriverAndRealPrice(@RequestBody ContractDriverRealPriceRequest contractDriverRealPriceRequest) {
        return contractService.updateDriverAndRealPrice(contractDriverRealPriceRequest);
    }

    @GetMapping("/confirmByCustomer/{id}")
    public ResponseEntity<?> comfirmContractByCustomer(@PathVariable long id){
        return contractService.confirmDepositCustomer(id);
    }

    @DeleteMapping("/cancelContractByCustomer/{id}")
    public ResponseEntity<?> cancelContract(@PathVariable long id) {
        return contractService.cancelRenting(id, 2);
    }

    @DeleteMapping("/cancelContractByStaff/{id}")
    public ResponseEntity<?> cancelContractByStaff(@PathVariable long id) {
        return contractService.cancelRenting(id, 1);
    }

    @PutMapping("/confirmByStaff/{contractId}")
    public ResponseEntity<?> confirmContractByStaff(@PathVariable long contractId) {
        return contractService.confirmDeposit(contractId);
    }

    @PutMapping("/getCarFromParking")
    public ResponseEntity<?> getCarFromParking(@RequestBody GetCarReQuest getCarReQuest) throws Exception {
        return contractService.comfirmGetCar(getCarReQuest);
    }

    @PutMapping("/addSurchargeEntity")
    public ResponseEntity<?> addSurchargeEntity(@RequestBody SurchargeRequest purchargeRequest){
        return contractService.addSurcharge(purchargeRequest);
    }

    @PutMapping("/addContractPaymentStaff")
    public ResponseEntity<?> addPaymentOnContract(@RequestBody PaymentRequest paymentRequest) throws Exception {
        return contractService.addPayment(paymentRequest);
    }

    @PutMapping("/returnCarToParking")
    public ResponseEntity<?> returnCarToParking(@RequestBody ReturnCarRequest returnCarRequest){
    return contractService.returnCar(returnCarRequest);
    }

    @GetMapping("getListPaymentByCustomer/{customerAccountId}")
    public ResponseEntity<?> getListPaymentByCustomerAccountId(@PathVariable long customerAccountId) {
        return contractService.getListPaymentByCustomer(customerAccountId);
    }

    @GetMapping("/getPaymentInf/{ContractId}")
    public ResponseEntity<?> getPaymentInf(@PathVariable long ContractId){
        return contractService.getContractPaymentInf(ContractId);
    }

    @GetMapping("/finishContract/{ContractId}")
    public ResponseEntity<?> finishContract(@PathVariable long ContractId) throws Exception {
        return contractService.finishContract(ContractId);
    }

    @PutMapping("/PaymentByCustomer")
    public ResponseEntity<?> paymentByCustomer(@RequestBody CustomerTransactionRequest customerTransactionRequest) throws Exception {
        return contractService.addPaymentByCustomer(customerTransactionRequest);
    }
}
