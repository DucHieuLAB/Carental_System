package com.example.create_entity.Service;

import com.example.create_entity.Entity.*;
import com.example.create_entity.Repository.*;
import com.example.create_entity.dto.Request.BookingRequest;
import com.example.create_entity.dto.Request.ListBookingDetailRequest;
import com.example.create_entity.dto.Response.BookingResponse;
import com.example.create_entity.dto.Response.ListBookingDetailResponse;
import com.example.create_entity.dto.Response.ResponseVo;
import com.example.create_entity.untils.ResponseVeConvertUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;

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
    @Autowired
    CarRepository cr;

    @Override
    public ResponseEntity<?> add(BookingRequest bookingRequest) {
        ResponseVo responseVo = null;
        if (ObjectUtils.isEmpty(bookingRequest)) {
            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Booking truyền vào trống", null);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        BookingEntity exsitBooking = br.findByCustomerIDAndExpectedStartDateAndExpectedEndDate(bookingRequest.getCustomerId(), bookingRequest.getExpectedStartDate(), bookingRequest.getExpectedEndDate());
        if (!ObjectUtils.isEmpty(exsitBooking)) {
            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Bạn đã đặt booking tương tự", null);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        if (bookingRequest.getListCarPlateNumber().isEmpty()) {
            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Bạn chưa chọn xe", null);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        BookingEntity newBooking = BookingRequest.convertToBookingEntity(bookingRequest);
        Optional<ParkingEntity> pickUpParking = pr.findById(bookingRequest.getPickupParkingId());
        Optional<ParkingEntity> returnParking = pr.findById(bookingRequest.getReturnParkingId());
        if (!pickUpParking.isPresent() || !returnParking.isPresent()) {
            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Thông tin bãi đỗ không đúng", null);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        AccountEntity customer = ar.getCustomerById(bookingRequest.getCustomerId());
        if (ObjectUtils.isEmpty(customer)) {
            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Thông tin người dùng không chính xác", null);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        newBooking.setPickup_parking(pickUpParking.get());
        newBooking.setReturn_parking(returnParking.get());
        newBooking.setCustomer(customer);
        try {
            Date date = new Date(System.currentTimeMillis());
            newBooking.setLastModifiedDate(date);
            newBooking.setCreatedDate(date);
            br.save(newBooking);
            newBooking = br.getByCustomerIdAndExpectStartDateAndExpectEndDate(bookingRequest.getCustomerId(), bookingRequest.getExpectedStartDate(),bookingRequest.getExpectedEndDate());
            List<BookingDetailEntity> bookingDetailEntities = new ArrayList<>();
            for (String carPlateNumber: bookingRequest.getListCarPlateNumber()
                 ) {
                BookingDetailEntity bookingDetailEntity = new BookingDetailEntity();
                CarEntity carEntity = cr.findCarEntityByPlateNumber(carPlateNumber);
                bookingDetailEntity.setBooking(newBooking);
                bookingDetailEntity.setCar(carEntity);
                bookingDetailEntity.setLastModifiedDate(date);
                bookingDetailEntities.add(bookingDetailEntity);

            }
            bdr.saveAll(bookingDetailEntities);
            // save list Booking Detail
            bookingDetailEntities = bdr.getListBookingDetailEntitiesByBookingId(newBooking.getId());
            List<ListBookingDetailResponse> listBookingDetailResponses = ListBookingDetailResponse.createListBookingDetailResponse(bookingDetailEntities);
            BookingResponse bookingResponse = BookingEntity.convertToBookingRespose(newBooking);
            HashMap<String,Object> reponse = new HashMap<>();
            reponse.put("Booking",bookingResponse);
            reponse.put("BookingDetail",listBookingDetailResponses);
            responseVo = ResponseVeConvertUntil.createResponseVo(true, "Tạo Booking thành công", reponse);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        } catch (Exception e) {
            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Lỗi Khi Tạo Mới Booking", null);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
    }

}
