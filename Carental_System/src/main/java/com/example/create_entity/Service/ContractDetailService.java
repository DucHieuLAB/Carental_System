package com.example.create_entity.Service;

import com.example.create_entity.dto.Request.ListBookingDetailRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface ContractDetailService {
    @Transactional
    ResponseEntity<?> addList(List<ListBookingDetailRequest> listBookingDetailRequests);

    ResponseEntity<?> ListBookingDetail(Long id);

    ResponseEntity<?>Future_Schedule(String username);

    ResponseEntity<?>Current_Schedule(String username);

//    ResponseEntity<?> SearchName_Schedule(String name,String expected_start_date1 ,String expected_start_date2 ,Integer p) ;
//
//
//    ResponseEntity<?> Search_PlateNumber_Schedule(String name, String date_start1, String date_start2, Integer p);
}
