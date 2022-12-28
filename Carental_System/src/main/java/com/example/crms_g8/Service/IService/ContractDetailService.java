package com.example.crms_g8.Service.IService;

import com.example.crms_g8.dto.Request.ListBookingDetailRequest;
import org.springframework.http.ResponseEntity;

import javax.transaction.Transactional;
import java.util.List;

public interface ContractDetailService {
    @Transactional
    ResponseEntity<?> addList(List<ListBookingDetailRequest> listBookingDetailRequests);

    ResponseEntity<?> ListContractDetail(Long id);

    ResponseEntity<?>FutureSchedule(String username);

    ResponseEntity<?>CurrentSchedule(String username);

    ResponseEntity<?>Historyschedule(String username);

//    ResponseEntity<?> SearchName_Schedule(String name,String expected_start_date1 ,String expected_start_date2 ,Integer p) ;
//
//
//    ResponseEntity<?> Search_PlateNumber_Schedule(String name, String date_start1, String date_start2, Integer p);
}
