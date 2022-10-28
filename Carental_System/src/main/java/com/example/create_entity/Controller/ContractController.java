package com.example.create_entity.Controller;

import com.example.create_entity.Service.ContractServiceImpl;
import com.example.create_entity.dto.Request.ContractHadDriverRequest;
import com.example.create_entity.dto.Request.ContractRealPriceRequest;
import com.example.create_entity.dto.Request.ContractRequest;
import com.example.create_entity.dto.Request.ListContractDetailDriverRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/contract")
public class ContractController {

    private final ContractServiceImpl contractService;
    @Autowired
    public ContractController(ContractServiceImpl bookingService1) {
        this.contractService = bookingService1;
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody ContractRequest contractRequest)
    {
        return contractService.add(contractRequest);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody ContractRequest contractRequest) {
        return contractService.update(contractRequest);
    }


    @RequestMapping(value = "/ListContract", method = RequestMethod.GET)
    public ResponseEntity<?> List(@RequestParam(value = "p", required = false) Integer p)
    {
        return contractService.ListContract(p);
    }

    @RequestMapping(value = "/Search", method = RequestMethod.GET)
    public ResponseEntity<?> Filter(@RequestParam(required = false) Integer p,
                                          @RequestParam(required = false) String name,
                                          @RequestParam(required = false)Integer HadDriver,
                                          @RequestParam(required = false)Integer Status,
                                          @RequestParam(required = false) String phone)
    {
        if(phone!=null && name==null){
            return contractService.FilterByPhone(phone,HadDriver,Status,p);
        }
        return contractService.FilterByName(name,HadDriver,Status,p);
    }


//    @GetMapping(value = "Detail/{id}")
//    public ResponseEntity<?> getContractDetail(@RequestParam(name = "id") Long id ){
////        return bookingService.getContractById();
//        return null;

    @GetMapping("/Detail/{id}")
    public ResponseEntity<?> getContract(@PathVariable long id){
        return contractService.getContractById(id);
    }

    @PutMapping("/update/driver")
    public ResponseEntity<?> updateDriver(@RequestBody ContractHadDriverRequest contractHadDriverRequest){
        return contractService.updateDriver(contractHadDriverRequest);
    }

    @PutMapping("/update/realprice")
    public ResponseEntity<?> updateDriver(@RequestBody ContractRealPriceRequest contractRealPriceRequest){
        return contractService.updateRealPrice(contractRealPriceRequest);
    }

    @GetMapping
    public ResponseEntity<?> updateContract(ContractRequest contractRequest){
        return contractService.update(contractRequest);
    }

}
