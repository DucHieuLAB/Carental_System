package com.example.create_entity.Service;

import com.example.create_entity.Entity.*;
import com.example.create_entity.Repository.AccountRepository;
import com.example.create_entity.Repository.BookingDetailRepository;
import com.example.create_entity.Repository.BookingRepository;
import com.example.create_entity.Repository.ParkingRepository;
import com.example.create_entity.dto.Request.BookingRequest;
import com.example.create_entity.dto.Request.ListBookingDetailRequest;
import com.example.create_entity.dto.Response.BookingResponse;
import com.example.create_entity.dto.Response.PagingBooking;
import com.example.create_entity.dto.Response.ReposMesses;
import com.example.create_entity.dto.Response.ResponseVo;
import com.example.create_entity.untils.ResponseVeConvertUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    BookingRepository br;
    @Autowired
    BookingDetailRepository bdr;
    @Autowired
    ParkingRepository pr;
    @Autowired
    AccountRepository ar;

    @Override
    public ResponseEntity<?> add(BookingRequest bookingRequest) {
        ResponseVo responseVo = null;
        if (ObjectUtils.isEmpty(bookingRequest)) {
            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Booking truyền vào trống", null);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        BookingEntity exsitBooking = br.findByCustomerIDAndCreatedDate(bookingRequest.getCustomerId(), bookingRequest.getExpectedStartDate(), bookingRequest.getExpectedEndDate());
        if (!ObjectUtils.isEmpty(exsitBooking)) {
            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Bạn đã đặt booking tương tự", null);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        if (bookingRequest.getLstListBookingDetailRequests().isEmpty()) {
            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Bạn chưa chọn xe", null);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        List<BookingDetailEntity> bookingDetails = ListBookingDetailRequest.convertToBookingDetailEntity(bookingRequest.getLstListBookingDetailRequests());
        BookingEntity newBooking = BookingRequest.convertToBookingEntity(bookingRequest);
        Optional<ParkingEntity> pickUpParking = pr.findById(bookingRequest.getPickupParkingId());
        Optional<ParkingEntity> returnParking = pr.findById(bookingRequest.getReturnParkingId());
        if (pickUpParking.isPresent() || returnParking.isPresent()) {
            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Thông tin bãi đỗ không đúng", null);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        AccountEntity customer = ar.getReferenceById(bookingRequest.getCustomerId());
        if (ObjectUtils.isEmpty(customer)) {
            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Thông tin người dùng không chính xác", null);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        newBooking.setPickup_parking(pickUpParking.get());
        newBooking.setReturn_parking(returnParking.get());
        newBooking.setCustomer(customer);
        try {
            br.save(newBooking);
            BookingResponse bookingResponse = BookingEntity.convertToBookingResponse(newBooking);
            responseVo = ResponseVeConvertUntil.createResponseVo(true, "Tạo Booking thành công", bookingResponse);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        } catch (Exception e) {
            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Lỗi Khi Tạo Mới Booking", null);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> ListBooking(Integer p) {
        if (p == null) {
            p = 0;
        } else if (p > 0) {
            p = p - 1;
        }
        Pageable pageable = PageRequest.of(p, 5);

        Page<BookingEntity> page = br.ListBooking(pageable);

        List<BookingResponse> bookingResponse = new ArrayList<>();

        page.forEach(BookingEntity -> {

            BookingResponse bookingResponse1 = BookingEntity.convertToBookingResponse(BookingEntity);
            bookingResponse.add(bookingResponse1);
        });

        PagingBooking pagingBooking = new PagingBooking();

        pagingBooking.setBookingResponseList(bookingResponse);
        pagingBooking.setTotalPage(page.getTotalPages());
        pagingBooking.setNumberPage(page.getNumber()+1);


        if (!page.isEmpty()) {
            return new ResponseEntity<>(pagingBooking, HttpStatus.OK);
        } else {
            ReposMesses messes = new ReposMesses();
            messes.setMess("K có dữ liệu bảng Booking");
            return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
        }


    }
}
