package com.example.create_entity.Service;

import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;


public interface DashBoardService {

    ResponseEntity<?> CountRequestContract();

    ResponseEntity<?> Count_new_customer();

    ResponseEntity<?> Count_driver();

    ResponseEntity<?> Count_CloseContract();

    ResponseEntity<?> Count_Car();

    ResponseEntity<?> Total_Paid_HadDriver();

    ResponseEntity<?> Total_Paid_NoDriver();
}
