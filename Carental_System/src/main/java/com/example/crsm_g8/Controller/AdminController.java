package com.example.crsm_g8.Controller;

import com.example.crsm_g8.Service.ServiceImpl.AdminServiceIml;
import com.example.crsm_g8.dto.Request.UpdateInfoAdminRequest;
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
    return   adminServiceIml.DetailAdmin(username);
    }


    @RequestMapping(value = "/admin/update", method = RequestMethod.PUT)
    public ResponseEntity<?> Update(@RequestBody UpdateInfoAdminRequest UpdateInfoAdminRequest) {
        return   adminServiceIml.Update(UpdateInfoAdminRequest);
    }
}
