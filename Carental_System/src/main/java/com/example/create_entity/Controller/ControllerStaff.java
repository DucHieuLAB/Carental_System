package com.example.create_entity.Controller;

import com.example.create_entity.Service.AccountServiceIml;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ControllerStaff {

    private final AccountServiceIml accountServiceIml;
    @RequestMapping(value = "/Staff/ListStaff", method = RequestMethod.GET)
    public ResponseEntity<?> ManagerStaff(@RequestParam(value = "p", required = false) Integer p) {
        return accountServiceIml.getListByNameRole(p);
    }
}
