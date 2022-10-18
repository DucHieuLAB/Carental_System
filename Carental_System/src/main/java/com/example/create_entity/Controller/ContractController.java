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


    @RequestMapping(value = "/ListBooking", method = RequestMethod.GET)
    public ResponseEntity<?> List(@RequestParam(value = "p", required = false) Integer p)
    {
        return bookingService.ListBooking(p);
    }

}
