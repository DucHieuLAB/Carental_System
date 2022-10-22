package com.example.create_entity.Controller;

import com.example.create_entity.Service.AccountServiceIml;
import com.example.create_entity.dto.Request.DriverInfoRequest;
import com.example.create_entity.dto.Request.StaffRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ControllerStaff {

    private final AccountServiceIml accountServiceIml;
    @RequestMapping(value = "/Staff/ListStaff", method = RequestMethod.GET)
    public ResponseEntity<?> ManagerStaff(@RequestParam(value = "p", required = false) Integer p) {
        return accountServiceIml.getListByNameRole(p);
    }

    @RequestMapping(value = "/Staff/Create", method = RequestMethod.POST)
    private ResponseEntity<?> Create(@RequestBody StaffRequest infoRequest) {
        return accountServiceIml.Create_Staff(infoRequest);
    }

}
