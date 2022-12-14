package com.example.crms_g8.Controller;

import com.example.crms_g8.Service.IService.DashBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DashboardController {

    @Autowired
    DashBoardService dashBoardService;

    @RequestMapping(value = "/DashBoard/CountRequestContract", method = RequestMethod.GET)
    public ResponseEntity<?> CountRequestContract() {
        return dashBoardService.CountRequestContract();
    }


    @RequestMapping(value = "/DashBoard/Count_new_Customer", method = RequestMethod.GET)
    public ResponseEntity<?>CountNewCustomer() {
        return dashBoardService.Countnewcustomer();
    }


    @RequestMapping(value = "/DashBoard/Count_Driver", method = RequestMethod.GET)
    public ResponseEntity<?>CountDriver() {
        return dashBoardService.Countdriver();
    }

    @RequestMapping(value = "/DashBoard/Count_CloseContract", method = RequestMethod.GET)
    public ResponseEntity<?> CountCloseContract() {
        return dashBoardService.CountCloseContract();
    }

    @RequestMapping(value = "/DashBoard/Count_Car", method = RequestMethod.GET)
    public ResponseEntity<?> CountCar() {
        return dashBoardService.CountCar();
    }


    @RequestMapping(value = "/DashBoard/Total_Paid_HadDriver", method = RequestMethod.GET)
    public ResponseEntity<?> TotalPaidHadDriver() {
        return dashBoardService.TotalPaidHadDriver();
    }
    @RequestMapping(value = "/DashBoard/Total_Paid_NoDriver", method = RequestMethod.GET)
    public ResponseEntity<?> TotalPaidSelfDriving() {
        return dashBoardService.TotalPaidSelfDriving();
    }
}
