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

    @RequestMapping(value = "/Search", method = RequestMethod.GET)
    public ResponseEntity<?> Filter(@RequestParam(required = false) Integer p,
                                    @RequestParam(required = false) String name,
                                    @RequestParam(required = false) Integer HadDriver,
                                    @RequestParam(required = false) Integer Status,
                                    @RequestParam(required = false) String phone) {
        if (phone != null && name == null) {
            return contractService.FilterByPhone(phone, HadDriver, Status, p);
        }
        return contractService.FilterByName(name, HadDriver, Status, p);
    }

    @GetMapping("/Detail/{id}")
    public ResponseEntity<?> getContract(@PathVariable long id) {
        return contractService.getContractById(id);
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

    @PutMapping("/confirmByStaff")
    public ResponseEntity<?> confirmContractByStaff(@RequestBody DepositRequest depositRequest) {
        return contractService.confirmDeposit(depositRequest);
    }

    @PutMapping("/getCarFromParking")
    public ResponseEntity<?> getCarFromParking(@RequestBody GetCarReQuest getCarReQuest) throws Exception {
        return contractService.comfirmGetCar(getCarReQuest);
    }

    @PutMapping("/addSurchargeEntity")
    public ResponseEntity<?> addSurchargeEntity(@RequestBody SurchargeRequest purchargeRequest){
        return contractService.addSurcharge(purchargeRequest);
    }

    @PutMapping("/addContractPaymentEntity")
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

    @PutMapping("/finishContract")
    public ResponseEntity<?> finishContract(@RequestBody FinishContractRequest finishContractRequest) throws Exception {
        return contractService.finishContract(finishContractRequest);
    }
}
