package com.example.create_entity.Service;

import com.example.create_entity.Entity.AccountEntity;
import com.example.create_entity.Entity.BookingDetailEntity;
import com.example.create_entity.Entity.BookingEntity;
import com.example.create_entity.Entity.ParkingEntity;
import com.example.create_entity.Repository.AccountRepository;
import com.example.create_entity.Repository.BookingDetailRepository;
import com.example.create_entity.Repository.BookingRepository;
import com.example.create_entity.Repository.ParkingRepository;
import com.example.create_entity.dto.Request.BookingRequest;
import com.example.create_entity.dto.Request.ListBookingDetailRequest;
import com.example.create_entity.dto.Response.BookingResponse;
import com.example.create_entity.dto.Response.ResponseVo;
import com.example.create_entity.untils.ResponseVeConvertUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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
            BookingResponse bookingResponse = BookingEntity.convertToBookingRespose(newBooking);
            responseVo = ResponseVeConvertUntil.createResponseVo(true, "Tạo Booking thành công", bookingResponse);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        } catch (Exception e) {
            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Lỗi Khi Tạo Mới Booking", null);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
    }
}
