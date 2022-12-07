package com.example.create_entity.Controller;

import com.example.create_entity.Service.AccountServiceIml;
import com.example.create_entity.dto.Request.ChangePassRequest;
import com.example.create_entity.dto.Request.UpdateInfoCustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> List(@RequestParam(required = false, name = "p") Integer p) {
        return accountService.ListCustomer(p);
    }

    @RequestMapping(value = "account/Customer/FilterByName", method = RequestMethod.GET)
    public ResponseEntity<?> FilterByName(@RequestParam(required = false, name = "p") Integer p,
                                          @RequestParam(required = false, name = "name") String name) {
        return accountService.FilterByNameCustomer(name, p);
    }

    @RequestMapping(value = "account/Customer/FilterByPhone", method = RequestMethod.GET)
    public ResponseEntity<?> FilterByPhone(@RequestParam(required = false, name = "p") Integer p,
                                           @RequestParam(required = false, name = "phone") String Phone) {
        return accountService.FilterByPhoneCustomer(Phone, p);
    }

    @RequestMapping(value = "account/Customer/FilterByIdentity", method = RequestMethod.GET)
    public ResponseEntity<?> FilterByIdentity(@RequestParam(required = false, name = "p") Integer p,
                                              @RequestParam(required = false, name = "identity_number") String identity_number) {
        return accountService.FilterByIdentityCustomer(identity_number, p);
    }

    @RequestMapping(value = "account/Customer/Update", method = RequestMethod.PUT)
    public ResponseEntity<?> Update(@RequestBody UpdateInfoCustomerRequest updateInfoCustomerRequest) {
        return accountService.UpdateCustomer(updateInfoCustomerRequest);
    }

    @RequestMapping(value = "account/Customer/ChangeStatus", method = RequestMethod.PUT)
    public ResponseEntity<?>ChangeStatus(@RequestParam(name = "username") String username) {
        return accountService.ChangeStatus(username);
    }



//    @RequestMapping(value = "account/Customer/Change_Password", method = RequestMethod.PUT)
//    public ResponseEntity<?> Change_Password(@RequestBody ChangePassRequest changePassRequest) {
//        return accountService.change_password_2(changePassRequest);
//    }
}
