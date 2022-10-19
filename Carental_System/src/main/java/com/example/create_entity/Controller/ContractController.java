package com.example.create_entity.Controller;

import com.example.create_entity.Service.ContractServiceImpl;
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

    @GetMapping("/Detail/{id}")
    public ResponseEntity<?> getCar(@PathVariable long id){
        return contractService.getContractById(id);
    }
}
