package com.example.create_entity.Service;

import com.example.create_entity.Entity.ContractDetailEntity;

import com.example.create_entity.dto.Request.ListBookingDetailRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        contractDetailEntity = contractDetailRepository.BookingDetail(id);

        contractDetailResponse.setReal_Pick_Up_Date(contractDetailEntity.getReal_pick_up_date());
        contractDetailResponse.setBookingID(contractDetailEntity.getBooking().getId());
        contractDetailResponse.setBookingDetailID(contractDetailEntity.getId());
        contractDetailResponse.setReal_Return_Date(contractDetailEntity.getReal_return_date());
        contractDetailResponse.setLast_Modified_Date(contractDetailEntity.getLastModifiedDate());
        contractDetailResponse.setReal_Return_Date(contractDetailEntity.getReal_return_date());
        contractDetailResponse.setCarID(contractDetailEntity.getCar().getId());

        return new ResponseEntity<>(contractDetailEntity,HttpStatus.OK);


    }

    public ResponseEntity<?> responseEntity(Integer p, List<ContractDetailEntity> contractDetailEntities, List<ContractDetailEntity> Total_Page, Integer size) {
        ResponseVo responseVo = new ResponseVo();
        if (!contractDetailEntities.isEmpty()) {
            List<ScheduleResponse> scheduleResponses = new ArrayList<>();
            contractDetailEntities.forEach(ContractDetailEntity -> {
                ScheduleResponse response = new ScheduleResponse();
                response = response.scheduleResponse(ContractDetailEntity);
                scheduleResponses.add(response);
            });

            PagingResponse pagingResponse = new PagingResponse();
            if (Total_Page.size() % size == 0) {
                pagingResponse.setTotalPage(Total_Page.size() / size);
            } else {
                pagingResponse.setTotalPage(Total_Page.size() / size + 1);
            }
            pagingResponse.setNumberPage(p + 1);
            pagingResponse.setObjects(scheduleResponses);
            return new ResponseEntity<>(pagingResponse, HttpStatus.OK);
        } else {
            responseVo.setStatus(false);
            responseVo.setMessage("Không có dữ liệu của trong hệ thống  !");
            return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> responseEntity(Integer p, Page<ContractDetailEntity> contractDetailEntities) {
        PagingResponse pagingResponse = new PagingResponse();
        ResponseVo responseVo = new ResponseVo();
        if (!contractDetailEntities.isEmpty()) {
            List<ScheduleResponse> scheduleResponses = new ArrayList<>();
            contractDetailEntities.forEach(ContractDetailEntity -> {
                ScheduleResponse response = new ScheduleResponse();
                response = response.scheduleResponse(ContractDetailEntity);
                scheduleResponses.add(response);
            });
            pagingResponse.setObjects(scheduleResponses);
            pagingResponse.setTotalPage(contractDetailEntities.getTotalPages());
            pagingResponse.setNumberPage(contractDetailEntities.getNumber() + 1);
            return new ResponseEntity<>(pagingResponse, HttpStatus.OK);
        } else {
            responseVo.setMessage("Không tìm thấy lịch trình !");
            responseVo.setStatus(false);
            return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> Schedule(Integer p) {
        if (p == null) {
            p = 0;
        } else if (p > 0) {
            p = p - 1;
        }
        int size = 5;
        try {
            Pageable pageRequest = PageRequest.of(p, size);
            List<ContractDetailEntity> contractDetailEntities = contractDetailRepository.Schedule(pageRequest);
            List<ContractDetailEntity> Total_Page = contractDetailRepository.Schedule_count();
            return responseEntity(p, contractDetailEntities, Total_Page, size);
        } catch (Exception e) {
            ResponseVo responseVo = new ResponseVo();
            responseVo.setStatus(false);
            responseVo.setMessage(e.getMessage());
            return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> SearchName_Schedule(String name, String expected_start_date1, String expected_start_date2, Integer p) {
        if (p == null) {
            p = 0;
        } else if (p > 0) {
            p = p - 1;
        }
        ResponseVo responseVo = new ResponseVo();
        int size = 5 ;
        if (expected_start_date1.isEmpty() || expected_start_date2.isEmpty()) {
            try {
                Pageable pageRequest = PageRequest.of(p, size);
                Page<ContractDetailEntity> contractDetailEntities = contractDetailRepository.SearchName_schedule("%" + name + "%", pageRequest);
                return responseEntity(p, contractDetailEntities);
            } catch (Exception e) {
                responseVo.setMessage(e.getMessage());
                responseVo.setStatus(false);
                return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
            }
        } else {
            try {
                Pageable pageRequest = PageRequest.of(p, size);
                List<ContractDetailEntity> contractDetailEntities = contractDetailRepository.SearchName_schedule_Date(name,expected_start_date1, expected_start_date2, pageRequest);
                List<ContractDetailEntity> contractDetailEntities1 = contractDetailRepository.SearchName_schedule_Date1(name,expected_start_date1, expected_start_date2);
                return responseEntity(p,contractDetailEntities,contractDetailEntities1,size);
            } catch (Exception e) {
                responseVo.setMessage(e.getMessage());
                responseVo.setStatus(false);
                return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
            }

        }
    }

    @Override
    public ResponseEntity<?> Search_PlateNumber_Schedule(String name, String date_start1, String date_start2, Integer p) {
        if (p == null) {
            p = 0;
        } else if (p > 0) {
            p = p - 1;
        }
        ResponseVo responseVo = new ResponseVo();
        int size = 5 ;
        if (date_start1.isEmpty() || date_start2.isEmpty()) {
            try {
                Pageable pageRequest = PageRequest.of(p, size);
                Page<ContractDetailEntity> contractDetailEntities = contractDetailRepository.SearchPlateNumber_schedule("%" + name + "%", pageRequest);
                return responseEntity(p, contractDetailEntities);
            } catch (Exception e) {
                responseVo.setMessage(e.getMessage());
                responseVo.setStatus(false);
                return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
            }
        } else {
            try {
                Pageable pageRequest = PageRequest.of(p, size);
                List<ContractDetailEntity> contractDetailEntities = contractDetailRepository.SearchPlateNumber_schedule_Date(name,date_start1,date_start2,pageRequest);
                List<ContractDetailEntity> contractDetailEntities1 = contractDetailRepository.SearchPlateNumber_schedule_Date1(name,date_start1,date_start2);
                return responseEntity(p,contractDetailEntities,contractDetailEntities1,size);
            } catch (Exception e) {
                responseVo.setMessage(e.getMessage());
                responseVo.setStatus(false);
                return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
            }

        }
    }

}