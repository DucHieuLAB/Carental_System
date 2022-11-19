package com.example.create_entity.Controller;

import com.example.create_entity.Repository.*;
import com.example.create_entity.Service.DriverService;
import com.example.create_entity.dto.Request.DriverInfoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DriverController {

    @Autowired
    DriverRepository driverRepository;

    @Autowired
    LicenseRepository licenseRepository;


    @Autowired
    AccountRepository accountRepository;

    @Autowired
    private final DriverService driverService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    DistrictRepository districtRepository;


    @RequestMapping(value = "/driver/ListDriver", method = RequestMethod.GET)
    public ResponseEntity<?> ManagerDriver(@RequestParam(value = "p", required = false) Integer p) {
        return driverService.ManagerDriver(p);
    }

    @RequestMapping(value = "/driver/Search_name", method = RequestMethod.GET)
    public ResponseEntity<?> Find_By_Name(@RequestParam(required = false) String name,
                                          @RequestParam(required = false) Integer p) {
        return driverService.SearchByName(name,p);
    }

    @RequestMapping(value = "/driver/Detail", method = RequestMethod.GET)
    private ResponseEntity<?> DetailDriver(@RequestParam(required = false) String username) {
        return driverService.DriverDetail(username);
    }


    @RequestMapping(value = "/driver/Search_Phone", method = RequestMethod.GET)
    public ResponseEntity<?> Find_By_Phone(@RequestParam(required = false) String Phone,
                                           @RequestParam(required = false) Integer p) {
        return driverService.Find_By_Phone(Phone,p);
    }


    @RequestMapping(value = "/driver/Search_CMT", method = RequestMethod.GET)
    public ResponseEntity<?> Find_By_CMT(@RequestParam(required = false) String cmt,
                                         @RequestParam(required = false) Integer p) {

        return driverService.Find_By_cmt(cmt, p);
    }

    @RequestMapping(value = "/driver/List_license", method = RequestMethod.GET)
    public ResponseEntity<?> licenseTypeEntities() {
        return driverService.licenseTypeEntities();
    }


    @RequestMapping(value = "/driver/Change_Status", method = RequestMethod.GET)
    public ResponseEntity<?> Change_Status_Driver(@RequestParam(name = "username") String username) {
        return driverService.Change_Status_Driver(username);
    }


    @RequestMapping(value = "/driver/Create", method = RequestMethod.POST)
    private ResponseEntity<?> Create(@RequestBody DriverInfoRequest infoRequest) {
        return driverService.Create(infoRequest);
    }

    @RequestMapping(value = "/driver/Update", method = RequestMethod.PUT)
    private ResponseEntity<?>Update(@RequestBody DriverInfoRequest infoRequest) {
        return driverService.UpdateDriver(infoRequest);
    }


}



