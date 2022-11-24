package com.example.create_entity.Controller;

import com.example.create_entity.Service.ContractServiceImpl;
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

    @RequestMapping(value = "/ListContract", method = RequestMethod.GET)
    public ResponseEntity<?> List(@RequestParam(value = "p", required = false) Integer p) {
        return contractService.ListContract(p);
    }

    @RequestMapping(value = "/ListContract_2", method = RequestMethod.GET)
    public ResponseEntity<?> List2(@RequestParam(value = "p", required = false) Integer p) {
        return contractService.ListContract2(p);
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

    @DeleteMapping("/cancelContractByCustomer/{id}")
    public ResponseEntity<?> cancelContract(@PathVariable long id) {
        return contractService.cancelRenting(id, 2);
    }

    @PutMapping("/confirmByStaff")
    public ResponseEntity<?> confirmContractByStaff(@RequestBody DepositRequest depositRequest) {
        return contractService.confirmDeposit(depositRequest);
    }

    //
//    @PutMapping("/update/driver")
//    public ResponseEntity<?> updateDriver(@RequestBody ContractHadDriverRequest contractHadDriverRequest){
//        return contractService.updateDriver(contractHadDriverRequest);
//    }
//
//    @PutMapping("/update/realprice")
//    public ResponseEntity<?> updateDriver(@RequestBody ContractRealPriceRequest contractRealPriceRequest){
//        return contractService.updateRealPrice(contractRealPriceRequest);
//    }
//
//    @PutMapping
//    public ResponseEntity<?> updateContract(@RequestBody ContractRequest contractRequest){
//        return contractService.update(contractRequest);
//    }

//    @PutMapping("/update/startContract")
//    public ResponseEntity<?> updateContractStatus(@RequestBody StartContractRequest startContractRequest){
//        return contractService.startRenting(startContractRequest);
//    }
//
//    @PutMapping("/confirmByCustomer/{id}")
//    public ResponseEntity<?> confirmContractByCustomer(@PathVariable long id){
//        return contractService.confirmContract(id,1);
//    }
//
//
//    @PutMapping("/startRenting/{id}")
//    public ResponseEntity<?> startRenting(@PathVariable long id){
//        return contractService.startRenting(id);
//    }
//    @PutMapping("/closeContract")
//    public ResponseEntity<?> closeContract(@RequestBody StartContractRequest startContractRequest){
//        return contractService.close();
//    }
//
//    @DeleteMapping("/cancelContractByStaff/{id}")
//    public ResponseEntity<?> cancelContract(@PathVariable long id){
//        return contractService.cancelRenting(id,1);
//    }

}
