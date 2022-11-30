package com.example.create_entity.Controller;

import com.example.create_entity.Service.AdminServiceIml;
import com.example.create_entity.dto.Request.RegisterInfoRequest;
import com.example.create_entity.dto.Request.UpdateInfoAdminRequest;
import com.example.create_entity.dto.Request.UpdateInfoCustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AdminController {

    @Autowired
    AdminServiceIml adminServiceIml;
    @RequestMapping(value = "/admin/detail", method = RequestMethod.GET)
    public ResponseEntity<?> Create(@RequestParam(name = "username") String username) {
    return   adminServiceIml.Detail(username);
    }


    @RequestMapping(value = "/admin/update", method = RequestMethod.PUT)
    public ResponseEntity<?> Update(@RequestBody UpdateInfoAdminRequest UpdateInfoAdminRequest) {
        return   adminServiceIml.Update(UpdateInfoAdminRequest);
    }
}
