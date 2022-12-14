package com.example.crms_g8.Service.IService;

import org.springframework.http.ResponseEntity;


public interface DashBoardService {

    ResponseEntity<?> CountRequestContract();

    ResponseEntity<?> Countnewcustomer();

    ResponseEntity<?> Countdriver();

    ResponseEntity<?> CountCloseContract();

    ResponseEntity<?> CountCar();

    ResponseEntity<?> TotalPaidHadDriver();

    ResponseEntity<?> TotalPaidSelfDriving();
}
