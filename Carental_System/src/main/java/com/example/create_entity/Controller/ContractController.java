package com.example.create_entity.Controller;

import com.example.create_entity.Service.ContractServiceImpl;
import com.example.create_entity.dto.Request.ContractRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/booking")
public class ContractController {

    private final ContractServiceImpl bookingService;
    @Autowired
    public ContractController(ContractServiceImpl bookingService1) {
        this.bookingService = bookingService1;
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody ContractRequest contractRequest)
    {
        return bookingService.add(contractRequest);
    }


    @RequestMapping(value = "/ListContract", method = RequestMethod.GET)
    public ResponseEntity<?> List(@RequestParam(value = "p", required = false) Integer p)
    {
        return bookingService.ListContract(p);
    }

    @RequestMapping(value = "/SearchByName", method = RequestMethod.GET)
    public ResponseEntity<?> FilterByName(@RequestParam(required = false) Integer p,String name)
    {
        return bookingService.FilterByName(name,p);
    }

    @RequestMapping(value = "/SearchByPhone", method = RequestMethod.GET)
    public ResponseEntity<?> FilterByPhone(@RequestParam(required = false) Integer p,String phone)
    {
        return bookingService.FilterByPhone(phone,p);
    }

    @RequestMapping(value = "/FilterByHadDriver", method = RequestMethod.GET)
    public ResponseEntity<?> FilterByHadDriver(@RequestParam(required = false) Integer p)
    {
        return bookingService.FilterByHadDriver(p);
    }


    @RequestMapping(value = "/FilterByNotHadDriver", method = RequestMethod.GET)
    public ResponseEntity<?> FilterByNotHadDriver(@RequestParam(required = false) Integer p)
    {
        return bookingService.FilterByNotHadDriver(p);
    }

    @RequestMapping(value = "/FilterByWaitingForProgressing", method = RequestMethod.GET)
    public ResponseEntity<?> FilterByWaitingForProgressing(@RequestParam(required = false) Integer p)
    {
        return bookingService.FilterByWaitingForProgressing(p);
    }

    @RequestMapping(value = "/FilterByWaitForConfirmation", method = RequestMethod.GET)
    public ResponseEntity<?> FilterByWaitForConfirmation(@RequestParam(required = false) Integer p)
    {
        return bookingService.FilterByWaitForConfirmation(p);
    }


    @RequestMapping(value = "/FilterByEffective", method = RequestMethod.GET)
    public ResponseEntity<?> FilterByEffective(@RequestParam(required = false) Integer p)
    {
        return bookingService.FilterByEffective(p);
    }


    @RequestMapping(value = "/FilterByActivate", method = RequestMethod.GET)
    public ResponseEntity<?> FilterByActivate(@RequestParam(required = false) Integer p)
    {
        return bookingService.FilterByActivate(p);
    }
    @RequestMapping(value = "/FilterByClose", method = RequestMethod.GET)
    public ResponseEntity<?> FilterByClose(@RequestParam(required = false) Integer p)
    {
        return bookingService.FilterByClose(p);
    }

    @RequestMapping(value = "/FilterByCancel", method = RequestMethod.GET)
    public ResponseEntity<?> FilterByCancel(@RequestParam(required = false) Integer p)
    {
        return bookingService.FilterByCancel(p);
    }

    ////////////////////////////////////////////////
    @GetMapping(value = "Detail/{id}")
    public ResponseEntity<?> getContractDetail(@RequestParam(name = "id") Long id ){
//        return bookingService.getContractById();
        return null;
    }
}
