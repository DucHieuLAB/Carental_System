package com.example.create_entity.Controller;

import com.example.create_entity.Service.ContractServiceImpl;
import com.example.create_entity.dto.Request.CarRequest;
import com.example.create_entity.dto.Request.ContractRequest;
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

    @RequestMapping(value = "/SearchByName", method = RequestMethod.GET)
    public ResponseEntity<?> FilterByName(@RequestParam(required = false) Integer p,String name)
    {
        return contractService.FilterByName(name,p);
    }

    @RequestMapping(value = "/SearchByPhone", method = RequestMethod.GET)
    public ResponseEntity<?> FilterByPhone(@RequestParam(required = false) Integer p,String phone)
    {
        return contractService.FilterByPhone(phone,p);
    }

    @RequestMapping(value = "/FilterByHadDriver", method = RequestMethod.GET)
    public ResponseEntity<?> FilterByHadDriver(@RequestParam(required = false) Integer p)
    {
        return contractService.FilterByHadDriver(p);
    }


    @RequestMapping(value = "/FilterByNotHadDriver", method = RequestMethod.GET)
    public ResponseEntity<?> FilterByNotHadDriver(@RequestParam(required = false) Integer p)
    {
        return contractService.FilterByNotHadDriver(p);
    }


    @RequestMapping(value = "/FilterByWaitingForProgressing", method = RequestMethod.GET)
    public ResponseEntity<?> FilterByWaitingForProgressing(@RequestParam(required = false) Integer p)
    {
        return contractService.FilterByWaitingForProgressing(p);
    }

    @RequestMapping(value = "/FilterByWaitForConfirmation", method = RequestMethod.GET)
    public ResponseEntity<?> FilterByWaitForConfirmation(@RequestParam(required = false) Integer p)
    {
        return contractService.FilterByWaitForConfirmation(p);
    }


    @RequestMapping(value = "/FilterByEffective", method = RequestMethod.GET)
    public ResponseEntity<?> FilterByEffective(@RequestParam(required = false) Integer p)
    {
        return contractService.FilterByEffective(p);
    }


    @RequestMapping(value = "/FilterByActivate", method = RequestMethod.GET)
    public ResponseEntity<?> FilterByActivate(@RequestParam(required = false) Integer p)
    {
        return contractService.FilterByActivate(p);
    }
    @RequestMapping(value = "/FilterByClose", method = RequestMethod.GET)
    public ResponseEntity<?> FilterByClose(@RequestParam(required = false) Integer p)
    {
        return contractService.FilterByClose(p);
    }

    @RequestMapping(value = "/FilterByCancel", method = RequestMethod.GET)
    public ResponseEntity<?> FilterByCancel(@RequestParam(required = false) Integer p)
    {
        return contractService.FilterByCancel(p);
    }


//    @GetMapping(value = "Detail/{id}")
//    public ResponseEntity<?> getContractDetail(@RequestParam(name = "id") Long id ){
////        return bookingService.getContractById();
//        return null;

    @GetMapping("/Detail/{id}")
    public ResponseEntity<?> getCar(@PathVariable long id){
        return contractService.getContractById(id);

    }


}
