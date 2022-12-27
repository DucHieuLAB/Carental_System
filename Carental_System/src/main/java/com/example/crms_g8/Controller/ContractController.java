package com.example.crms_g8.Controller;

import com.example.crms_g8.Service.ServiceImpl.ContractServiceImpl;
import com.example.crms_g8.dto.Request.*;
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
    public ResponseEntity<?> ListRequestCustomer(@RequestParam(value = "p", required = false) Integer p) {
        return contractService.ManageRequest(p);
    }

    @RequestMapping(value = "/ListContract", method = RequestMethod.GET)
    public ResponseEntity<?> ListContractCustomer(@RequestParam(value = "p", required = false) Integer p) {
        return contractService.ManageContract(p);
    }

    @RequestMapping(value = "/SearchContract", method = RequestMethod.GET)
    public ResponseEntity<?> FilterListContract(@RequestParam(required = false) Integer p,
                                    @RequestParam(required = false) String name,
                                    @RequestParam(required = false) Integer HadDriver,
                                    @RequestParam(required = false) Integer Status,
                                    @RequestParam(required = false) String phone) {
        if (phone != null &&  (name == null || name=="")) {
            return contractService.SearchByPhoneContract(phone, HadDriver, Status, p);
        }
        return contractService.SearchByNameContract(name, HadDriver, Status, p);
    }

    @RequestMapping(value = "/SearchRequest", method = RequestMethod.GET)
    public ResponseEntity<?> FilterListRequest(@RequestParam(required = false) Integer p,
                                    @RequestParam(required = false) String name,
                                    @RequestParam(required = false) Integer HadDriver,
                                    @RequestParam(required = false) Integer Status,
                                    @RequestParam(required = false) String phone) {
        if (phone != null && (name == null || name=="")) {
            return contractService.SearchByPhoneRequest(phone, HadDriver, Status, p);
        }
        return contractService.SearchByNameRequest(name, HadDriver, Status, p);
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
    public ResponseEntity<?> updateDriverAndRealPrice(@RequestBody ContractDriverRealPriceRequest contractDriverRealPriceRequest) throws Exception {
        return contractService.updateDriverAndRealPrice(contractDriverRealPriceRequest);
    }

    @DeleteMapping("/cancelContractByCustomer")
    public ResponseEntity<?> cancelContract(@RequestBody CancelContractRequest cancelContractRequest) {
        cancelContractRequest.setDoCustomer(true);
        return contractService.cancelRenting(cancelContractRequest);
    }

    @DeleteMapping("/cancelContractByStaff")
    public ResponseEntity<?> cancelContractByStaff(@RequestBody CancelContractRequest cancelContractRequest) {
        cancelContractRequest.setDoCustomer(false);
        return contractService.cancelRenting(cancelContractRequest);
    }

    @PutMapping("/getCarFromParking")
    public ResponseEntity<?> getCarFromParking(@RequestBody GetCarReQuest getCarReQuest) throws Exception {
        return contractService.comfirmGetCar(getCarReQuest);
    }

    @PutMapping("/addSurchargeEntity")
    public ResponseEntity<?> addSurchargeEntity(@RequestBody SurchargeRequest surchargeRequest){
        return contractService.addSurcharge(surchargeRequest);
    }
    @GetMapping("/getSurchargeByContract/{contractId}")
    public ResponseEntity<?> getListSurchargeByContract(@PathVariable long contractId){
        return contractService.getListSurchargeByContract(contractId);
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

    @GetMapping("/listPayment/{ContractId}")
    public ResponseEntity<?> getListPaymentByContract(@PathVariable long ContractId){
        return contractService.getListPaymentByContract(ContractId);
    }

    @GetMapping("/getPaymentInf/{ContractId}")
    public ResponseEntity<?> getPaymentInf(@PathVariable long ContractId){
        return contractService.getContractPayment(ContractId);
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
