package com.example.create_entity.Service;

import com.example.create_entity.Entity.*;

import com.example.create_entity.Repository.*;
import com.example.create_entity.dto.Request.ContractRequest;
import com.example.create_entity.dto.Response.ContractResponse;
import com.example.create_entity.dto.Response.ListContractDetailResponse;

import com.example.create_entity.Repository.AccountRepository;
import com.example.create_entity.Repository.ContractDetailRepository;
import com.example.create_entity.Repository.ContractRepository;
import com.example.create_entity.Repository.ParkingRepository;
import com.example.create_entity.dto.Response.PagingContract;
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


import java.util.*;


@Service
public class ContractServiceImpl implements ContractService {
    @Autowired
    ContractRepository br;
    @Autowired
    ContractDetailRepository bdr;
    @Autowired
    ParkingRepository pr;
    @Autowired
    AccountRepository ar;
    @Autowired
    CarRepository cr;

    @Override
    public ResponseEntity<?> add(ContractRequest contractRequest) {
        ResponseVo responseVo = null;
        if (ObjectUtils.isEmpty(contractRequest)) {
            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Booking truyền vào trống", null);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        ContractEntity exsitBooking = br.findByCustomerIDAndExpectedStartDateAndExpectedEndDate(contractRequest.getCustomerId(), contractRequest.getExpectedStartDate(), contractRequest.getExpectedEndDate());
        if (!ObjectUtils.isEmpty(exsitBooking)) {
            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Bạn đã đặt booking tương tự", null);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        if (contractRequest.getListCarPlateNumber().isEmpty()) {
            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Bạn chưa chọn xe", null);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        ContractEntity newBooking = ContractRequest.convertToBookingEntity(contractRequest);
        Optional<ParkingEntity> pickUpParking = pr.findById(contractRequest.getPickupParkingId());
        Optional<ParkingEntity> returnParking = pr.findById(contractRequest.getReturnParkingId());
        if (!pickUpParking.isPresent() || !returnParking.isPresent()) {
            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Thông tin bãi đỗ không đúng", null);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        AccountEntity customer = ar.getCustomerById(contractRequest.getCustomerId());
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
            newBooking = br.getByCustomerIdAndExpectStartDateAndExpectEndDate(contractRequest.getCustomerId(), contractRequest.getExpectedStartDate(), contractRequest.getExpectedEndDate());
            List<ContractDetailEntity> bookingDetailEntities = new ArrayList<>();
            for (String carPlateNumber : contractRequest.getListCarPlateNumber()) {
                ContractDetailEntity contractDetailEntity = new ContractDetailEntity();
                CarEntity carEntity = cr.findCarEntityByPlateNumber(carPlateNumber);
                contractDetailEntity.setBooking(newBooking);
                contractDetailEntity.setCar(carEntity);
                contractDetailEntity.setLastModifiedDate(date);
                bookingDetailEntities.add(contractDetailEntity);

            }
            bdr.saveAll(bookingDetailEntities);
            // save list Booking Detail
            bookingDetailEntities = bdr.getListBookingDetailEntitiesByBookingId(newBooking.getId());
            List<ListContractDetailResponse> listContractDetailRespons = ListContractDetailResponse.createListBookingDetailResponse(bookingDetailEntities);
            ContractResponse contractResponse = ContractEntity.convertToBookingResponse(newBooking);
            HashMap<String, Object> reponse = new HashMap<>();
            reponse.put("Booking", contractResponse);
            reponse.put("BookingDetail", listContractDetailRespons);
            responseVo = ResponseVeConvertUntil.createResponseVo(true, "Tạo Booking thành công", reponse);

            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        } catch (Exception e) {
            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Lỗi Khi Tạo Mới Booking", null);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> ListContract(Integer p) {
        if (p == null) {
            p = 0;
        } else if (p > 0) {
            p = p - 1;
        }
        Pageable pageable = PageRequest.of(p, 5);

        Page<ContractEntity> page = br.ListContract(pageable);

        List<ContractResponse> contractResponse = new ArrayList<>();

        page.forEach(BookingEntity -> {

            ContractResponse contractResponse1 = BookingEntity.convertToBookingResponse(BookingEntity);
            contractResponse.add(contractResponse1);
        });

        PagingContract pagingContract = new PagingContract();

        pagingContract.setContractResponseList(contractResponse);
        pagingContract.setTotalPage(page.getTotalPages());
        pagingContract.setNumberPage(page.getNumber() + 1);


        if (!page.isEmpty()) {
            return new ResponseEntity<>(pagingContract, HttpStatus.OK);
        } else {
            ReposMesses messes = new ReposMesses();
            messes.setMess("K có dữ liệu bảng Booking");
            return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
        }


    }

    public Integer CheckNullPaging(Integer p) {
        if (p == null) {
            p = 0;
        } else if (p > 0) {
            p = p - 1;
        }
        return p;
    }

    public ResponseEntity<?> responseResultContract(List<ContractEntity> contractEntities, List<ContractEntity> contractEntities1, Integer size, Integer p) {
        List<ContractResponse> contractResponses = new ArrayList<>();
        contractEntities.forEach(ContractEntity -> {
            ContractResponse contractResponse;
            contractResponse = ContractEntity.convertToBookingResponse(ContractEntity);
            contractResponses.add(contractResponse);
        });
        PagingContract pagingContract = new PagingContract();


        if (contractEntities1.size() % size == 0) {
            pagingContract.setTotalPage(contractEntities1.size() / size);
        } else {
            pagingContract.setTotalPage(contractEntities1.size() / size + 1);
        }
        pagingContract.setNumberPage(p + 1);
        pagingContract.setContractResponseList(contractResponses);

        if (contractEntities1.isEmpty()) {
            ReposMesses messes = new ReposMesses();
            messes.setMess("Không tìm thấy ! ");
            return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(pagingContract, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> FilterByName(String name, Integer p) {

        p = CheckNullPaging(p);
        Integer size = 5;
        Pageable pageable = PageRequest.of(p, size);
        List<ContractEntity> contractEntities = br.FilterByName(name, pageable);
        List<ContractEntity> contractEntities1 = br.FilterByName1(name);

        return responseResultContract(contractEntities, contractEntities1, size, p);

    }

    @Override
    public ResponseEntity<?> FilterByPhone(String phone, Integer p) {

        p = CheckNullPaging(p);
        Integer size = 5;
        Pageable pageable = PageRequest.of(p, size);
        List<ContractEntity> contractEntities = br.FilterByPhone(phone,pageable);
        List<ContractEntity> contractEntities1 = br.FilterByPhone1(phone);

        return responseResultContract(contractEntities, contractEntities1, size, p);
    }

    @Override
    public ResponseEntity<?> FilterByHadDriver(Integer p) {
        p = CheckNullPaging(p);
        Integer size = 5;
        Pageable pageable = PageRequest.of(p, size);
        List<ContractEntity> contractEntities = br.FilterByHadDriver(pageable);
        List<ContractEntity> contractEntities1 = br.FilterByHadDriver1();
        return responseResultContract(contractEntities, contractEntities1, size, p);
    }

    @Override
    public ResponseEntity<?> FilterByNotHadDriver(Integer p) {
        p = CheckNullPaging(p);
        Integer size = 5;
        Pageable pageable = PageRequest.of(p, size);
        List<ContractEntity> contractEntities = br.FilterByNotHadDriver(pageable);
        List<ContractEntity> contractEntities1 = br.FilterByNotHadDriver1();
        return responseResultContract(contractEntities, contractEntities1, size, p);
    }
    @Override
    public ResponseEntity<?> getContractById(Long id) {
        ResponseVo responseVo = null;
        ContractEntity contractEntity = br.FindByID(id);
        if(ObjectUtils.isEmpty(contractEntity)){
            responseVo = ResponseVeConvertUntil.createResponseVo(false,"Không tìm thấy thông tin phù hợp",null);
            return new ResponseEntity<>(responseVo,HttpStatus.OK);
        }
        ContractResponse response = ContractEntity.convertToBookingResponse(contractEntity);
        responseVo = ResponseVeConvertUntil.createResponseVo(true,"Lấy thông tin Hợp đồng thành công",response);
        return new ResponseEntity<>(responseVo,HttpStatus.OK);
    }

}
