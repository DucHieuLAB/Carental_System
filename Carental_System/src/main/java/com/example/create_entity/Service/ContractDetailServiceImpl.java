package com.example.create_entity.Service;

import com.example.create_entity.Entity.ContractDetailEntity;

import com.example.create_entity.dto.Request.ListBookingDetailRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;



import com.example.create_entity.Repository.ContractDetailRepository;
import com.example.create_entity.dto.Response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@Service
public class ContractDetailServiceImpl implements ContractDetailService {

    @Autowired
    ContractDetailRepository contractDetailRepository;


    @Override
    public ResponseEntity<?> addList(List<ListBookingDetailRequest> listBookingDetailRequests) {
        return null;
    }

    @Override
    public ResponseEntity<?> ListBookingDetail(Long id) {
        ContractDetailResponse contractDetailResponse = new ContractDetailResponse();
        ContractDetailEntity contractDetailEntity;
             contractDetailEntity =  contractDetailRepository.BookingDetail(id);

        contractDetailResponse.setReal_Pick_Up_Date(contractDetailEntity.getReal_pick_up_date());
        contractDetailResponse.setBookingID(contractDetailEntity.getBooking().getId());
        contractDetailResponse.setBookingDetailID(contractDetailEntity.getId());
        contractDetailResponse.setReal_Return_Date(contractDetailEntity.getReal_return_date());
        contractDetailResponse.setLast_Modified_Date(contractDetailEntity.getLastModifiedDate());
        contractDetailResponse.setReal_Pick_Up_Date(contractDetailEntity.getReal_pick_up_date());
        contractDetailResponse.setCarID(contractDetailEntity.getCar().getId());


        return new ResponseEntity<>(contractDetailEntity, HttpStatus.OK);


    }

}

