package com.example.create_entity.Service.IService;

import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;


public interface DashBoardService {

    ResponseEntity<?> CountRequestContract();

    ResponseEntity<?> Countnewcustomer();

    ResponseEntity<?> Countdriver();

    ResponseEntity<?> CountCloseContract();

    ResponseEntity<?> CountCar();

    ResponseEntity<?> TotalPaidHadDriver();

    ResponseEntity<?> TotalPaidSelfDriving();
}
