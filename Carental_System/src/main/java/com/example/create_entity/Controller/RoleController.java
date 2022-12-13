package com.example.create_entity.Controller;


import com.example.create_entity.Service.ServiceImpl.RoleServiceIml;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RoleController {
    private final RoleServiceIml roleServiceIml;
    @RequestMapping(value = "/ListRole", method = RequestMethod.GET)
    public ResponseEntity<?> ManagerDriver(){
        return roleServiceIml.ListRole();
    }
}
