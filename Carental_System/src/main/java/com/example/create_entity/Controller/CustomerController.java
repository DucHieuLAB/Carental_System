package com.example.create_entity.Controller;

import com.example.create_entity.Service.AccountServiceIml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CustomerController {

    @Autowired
    AccountServiceIml accountService;

    @RequestMapping(value = "account/Customer/Detail", method = RequestMethod.GET)
    public ResponseEntity<?> GetDetail(@RequestParam(required = false) String username) {
        return accountService.DetailCustomer(username);
    }


    @RequestMapping(value = "account/Customer/List", method = RequestMethod.GET)
    public ResponseEntity<?> List(@RequestParam(required = false ,name = "p") Integer p) {
        return accountService.ListCustomer(p);
    }

    @RequestMapping(value = "account/Customer/FilterByName", method = RequestMethod.GET)
    public ResponseEntity<?> FilterByName(@RequestParam(required = false ,name = "p") Integer p,
                                          @RequestParam(required = false ,name = "name") String name) {
        return accountService.FilterByNameCustomer(name,p);
    }

    @RequestMapping(value = "account/Customer/FilterByPhone", method = RequestMethod.GET)
    public ResponseEntity<?> FilterByPhone(@RequestParam(required = false ,name = "p") Integer p,
                                          @RequestParam(required = false ,name = "phone") String Phone) {
        return accountService.FilterByPhoneCustomer(Phone,p);
    }

    @RequestMapping(value = "account/Customer/FilterByIdentity", method = RequestMethod.GET)
    public ResponseEntity<?> FilterByIdentity(@RequestParam(required = false ,name = "p") Integer p,
                                          @RequestParam(required = false ,name = "identity_number") String identity_number) {
        return accountService.FilterByIdentityCustomer(identity_number,p);
    }
}