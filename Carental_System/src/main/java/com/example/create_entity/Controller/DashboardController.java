package com.example.create_entity.Controller;

import com.example.create_entity.Service.DashBoardService;
import com.example.create_entity.dto.Request.RegisterInfoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DashboardController {

    @Autowired
    DashBoardService dashBoardService;

    @RequestMapping(value = "/DashBoard/CountRequestContract", method = RequestMethod.GET)
    public ResponseEntity<?> Count_RequestContract() {
        return dashBoardService.CountRequestContract();
    }


    @RequestMapping(value = "/DashBoard/Count_new_Customer", method = RequestMethod.GET)
    public ResponseEntity<?>Count_new_customer() {
        return dashBoardService.Count_new_customer();
    }


    @RequestMapping(value = "/DashBoard/Count_Driver", method = RequestMethod.GET)
    public ResponseEntity<?>Count_Driver() {
        return dashBoardService.Count_driver();
    }

    @RequestMapping(value = "/DashBoard/Count_CloseContract", method = RequestMethod.GET)
    public ResponseEntity<?> Count_CloseContract() {
        return dashBoardService.Count_CloseContract();
    }

    @RequestMapping(value = "/DashBoard/Count_Car", method = RequestMethod.GET)
    public ResponseEntity<?> Count_Car() {
        return dashBoardService.Count_Car();
    }


    @RequestMapping(value = "/DashBoard/Total_Paid_HadDriver", method = RequestMethod.GET)
    public ResponseEntity<?> Total_Paid_HadDriver() {
        return dashBoardService.Total_Paid_HadDriver();
    }
    @RequestMapping(value = "/DashBoard/Total_Paid_NoDriver", method = RequestMethod.GET)
    public ResponseEntity<?> Total_Paid_NoDriver() {
        return dashBoardService.Total_Paid_NoDriver();
    }
}
