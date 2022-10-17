package com.example.create_entity.Service;

import com.example.create_entity.Entity.BookingDetailEntity;

import com.example.create_entity.dto.Request.ListBookingDetailRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;



import com.example.create_entity.Repository.BookingDetailRepository;
import com.example.create_entity.dto.Response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BookingDetailServiceImpl implements BookingDetailService {

    @Autowired
    BookingDetailRepository bookingDetailRepository;


    @Override
    public ResponseEntity<?> addList(List<ListBookingDetailRequest> listBookingDetailRequests) {
        return null;
    }

    @Override
    public ResponseEntity<?> ListBookingDetail(Long id) {
        BookingDetailResponse bookingDetailResponse = new BookingDetailResponse();
        BookingDetailEntity bookingDetailEntity ;
             bookingDetailEntity=  bookingDetailRepository.BookingDetail(id);

        bookingDetailResponse.setReal_Pick_Up_Date(bookingDetailEntity.getReal_pick_up_date());
        bookingDetailResponse.setBookingID(bookingDetailEntity.getBooking().getId());
        bookingDetailResponse.setBookingDetailID(bookingDetailEntity.getId());
        bookingDetailResponse.setReal_Return_Date(bookingDetailEntity.getReal_return_date());
        bookingDetailResponse.setLast_Modified_Date(bookingDetailEntity.getLastModifiedDate());
        bookingDetailResponse.setReal_Pick_Up_Date(bookingDetailEntity.getReal_pick_up_date());
        bookingDetailResponse.setCarID(bookingDetailEntity.getCar().getId());


        return new ResponseEntity<>(bookingDetailEntity, HttpStatus.OK);


    }

}

