package com.example.create_entity.Controller;

import com.example.create_entity.Service.AccountServiceIml;
import com.example.create_entity.dto.Request.DriverInfoRequest;
import com.example.create_entity.dto.Request.StaffRequest;
import com.example.create_entity.dto.Request.UpdateInfoStaffRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ControllerStaff {

    @Autowired
    AccountServiceIml accountServiceIml;
    @RequestMapping(value = "/Staff/ListStaff", method = RequestMethod.GET)
    public ResponseEntity<?> ManagerStaff(@RequestParam(value = "p", required = false) Integer p) {
        return accountServiceIml.getListStaff(p);
    }

    @RequestMapping(value = "/Staff/Create", method = RequestMethod.POST)
    private ResponseEntity<?> Create(@RequestBody StaffRequest infoRequest) {
        return accountServiceIml.Create_Staff(infoRequest);
    }

    @RequestMapping(value = "/Staff/FilterByName", method = RequestMethod.GET)
    private ResponseEntity<?>FilterByName(@RequestParam(required = false) String name,Integer p)  {
        return accountServiceIml.FilterByName(name,p);
    }

    @RequestMapping(value = "/Staff/FilterByPhone", method = RequestMethod.GET)
    private ResponseEntity<?>FilterByPhone(@RequestParam(required = false) String phone,Integer p)  {
        return accountServiceIml.FilterByPhone(phone,p);
    }

    @RequestMapping(value = "/Staff/FilterByIdentity_Number", method = RequestMethod.GET)
    private ResponseEntity<?>FilterByIdentity_Number(@RequestParam(required = false) String cmt,Integer p)  {
        return accountServiceIml.FilterByIdentity_Number(cmt,p);
    }

    @RequestMapping(value = "/Staff/ChangeStatus", method = RequestMethod.GET)
    private ResponseEntity<?>FilterByName(@RequestParam(required = false) String username)  {
        return accountServiceIml.ChangeStatusStaff(username);
    }

    @RequestMapping(value = "/Staff/GetDetail", method = RequestMethod.GET)
    private ResponseEntity<?>GetDetail(@RequestParam(required = false) String username)  {
        return accountServiceIml.GetDetailStaff(username);
    }

    @RequestMapping(value = "/Staff/Update", method = RequestMethod.PUT)
    private ResponseEntity<?> Update(@RequestBody UpdateInfoStaffRequest infoRequest) {
        return accountServiceIml.UpdateStaff(infoRequest);
    }


}
