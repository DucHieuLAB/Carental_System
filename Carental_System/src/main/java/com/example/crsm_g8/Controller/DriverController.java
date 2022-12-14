package com.example.crsm_g8.Controller;

import com.example.crsm_g8.Repository.*;
import com.example.crsm_g8.Service.ServiceImpl.DriverService;
import com.example.crsm_g8.dto.Request.DriverInfoRequest;
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
    public ResponseEntity<?>GetList(@RequestParam(value = "p", required = false) Integer p) {
        return driverService.GetListDriver(p);
    }

    @RequestMapping(value = "/driver/Search_name", method = RequestMethod.GET)
    public ResponseEntity<?>FilterByName(@RequestParam(required = false) String name,
                                          @RequestParam(required = false) Integer p) {
        return driverService.FilterByName(name,p);
    }

    @RequestMapping(value = "/driver/Detail", method = RequestMethod.GET)
    private ResponseEntity<?>GetDetail(@RequestParam(required = false) String username) {
        return driverService.DetailDriver(username);
    }


    @RequestMapping(value = "/driver/Search_Phone", method = RequestMethod.GET)
    public ResponseEntity<?>FilterByPhone(@RequestParam(required = false) String Phone,
                                           @RequestParam(required = false) Integer p) {
        return driverService.FilterByPhone(Phone,p);
    }


    @RequestMapping(value = "/driver/Search_CMT", method = RequestMethod.GET)
    public ResponseEntity<?>FilterByIdentity(@RequestParam(required = false) String cmt,
                                         @RequestParam(required = false) Integer p) {

        return driverService.FilterByIdentity(cmt, p);
    }

    @RequestMapping(value = "/driver/List_license", method = RequestMethod.GET)
    public ResponseEntity<?> licenseTypeEntities() {
        return driverService.licenseTypeEntities();
    }


    @RequestMapping(value = "/driver/Change_Status", method = RequestMethod.GET)
    public ResponseEntity<?>ChangeStatus(@RequestParam(name = "username") String username) {
        return driverService.ChangeStatusDriver(username);
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



