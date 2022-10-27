package com.example.create_entity.Service;

import com.example.create_entity.Entity.*;

import com.example.create_entity.Repository.*;
import com.example.create_entity.dto.Request.ContractHadDriverRequest;
import com.example.create_entity.dto.Request.ContractRequest;
import com.example.create_entity.dto.Request.DistricRequest;
import com.example.create_entity.dto.Request.ListContractDetailDriverRequest;
import com.example.create_entity.dto.Response.*;

import com.example.create_entity.Repository.AccountRepository;
import com.example.create_entity.Repository.ContractDetailRepository;
import com.example.create_entity.Repository.ContractRepository;
import com.example.create_entity.Repository.ParkingRepository;

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
import java.util.concurrent.TimeUnit;


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
    @Autowired
    ContractHadDriverRepository chdr;
    @Autowired
    DistrictRepository districtRepository;
    @Autowired
    DriverRepository dr;
    @Autowired
    LicenseRepository licenseRepository;


    public ResponseEntity<?> add(ContractRequest contractRequest) {
        ResponseVo responseVo = null;
        if (ObjectUtils.isEmpty(contractRequest)) {
            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Booking truyền vào trống", null);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        if (contractRequest.getListCarPlateNumber().isEmpty()) {
            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Bạn chưa chọn xe", null);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        ContractEntity exsitBooking = br.findByCustomerIDAndExpectedStartDateAndExpectedEndDate(contractRequest.getCustomerId(), contractRequest.getExpectedStartDate(), contractRequest.getExpectedEndDate());
        if (!ObjectUtils.isEmpty(exsitBooking)) {
            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Bạn đã đặt booking tương tự", null);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        ContractEntity newContract = ContractRequest.convertToContractEntity(contractRequest);
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
        newContract.setPickup_parking(pickUpParking.get());
        newContract.setReturn_parking(returnParking.get());
        newContract.setCustomer(customer);
        newContract.setQuantity(contractRequest.getListCarPlateNumber().size());
        try {
            Date date = new Date(System.currentTimeMillis());
            newContract.setLastModifiedDate(date);
            newContract.setCreatedDate(date);
            long diffInMillies = Math.abs(newContract.getExpected_end_date().getTime() - newContract.getExpected_start_date().getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            br.save(newContract);
            newContract = br.getByCustomerIdAndExpectStartDateAndExpectEndDate(contractRequest.getCustomerId(), contractRequest.getExpectedStartDate(), contractRequest.getExpectedEndDate());
            ContractHadDriverReponse contractHadDriverReponse = null;
            if (newContract.isHad_driver() == true) {
                ContractHadDriverEntity entity = new ContractHadDriverEntity();
                entity.set_one_way(contractRequest.isOneWay());
                DistrictsEntity districPickUpAddress = districtRepository.check_districts(contractRequest.getDistricPickUpAddress().getCity(),
                        contractRequest.getDistricPickUpAddress().getWards(),
                        contractRequest.getDistricPickUpAddress().getDistrictName());
                DistrictsEntity districReturnAddress = districtRepository.check_districts(contractRequest.getDistricReturnAddress().getCity(),
                        contractRequest.getDistricReturnAddress().getWards(),
                        contractRequest.getDistricReturnAddress().getDistrictName());
                if (ObjectUtils.isEmpty(districPickUpAddress)) {
                    districPickUpAddress = DistrictsEntity.createDistricEntity(contractRequest.getDistricPickUpAddress());
                    districtRepository.save(districPickUpAddress);
                    districPickUpAddress = districtRepository.check_districts(contractRequest.getDistricPickUpAddress().getCity(),
                            contractRequest.getDistricPickUpAddress().getWards(),
                            contractRequest.getDistricPickUpAddress().getDistrictName());
                }
                if (ObjectUtils.isEmpty(districReturnAddress)) {
                    districReturnAddress = DistrictsEntity.createDistricEntity(contractRequest.getDistricReturnAddress());
                    districtRepository.save(districReturnAddress);
                    districReturnAddress = districtRepository.check_districts(contractRequest.getDistricReturnAddress().getCity(),
                            contractRequest.getDistricReturnAddress().getWards(),
                            contractRequest.getDistricReturnAddress().getDistrictName());
                }

                entity.setPickup_district_id(districPickUpAddress.getId());
                entity.setReturn_district_id(districReturnAddress.getId());
                entity.setPickup_address(contractRequest.getPickUpAddress());
                entity.setReturn_address(contractRequest.getReturnAddress());
                entity.setContractEntity(newContract);
                entity.setLastModifiedDate(date);
                chdr.save(entity);
                entity = chdr.getByContractID(newContract.getId());
                contractHadDriverReponse = ContractHadDriverEntity.convertToContractHadDriverResponse(entity);
            }
            double expectedRentalPrice = 0;
            double depositAmount = 0;
            List<ContractDetailEntity> bookingDetailEntities = new ArrayList<>();
            for (String carPlateNumber : contractRequest.getListCarPlateNumber()) {
                ContractDetailEntity contractDetailEntity = new ContractDetailEntity();
                CarEntity carEntity = cr.findCarEntityByPlateNumber(carPlateNumber);
                contractDetailEntity.setBooking(newContract);
                contractDetailEntity.setCar(carEntity);
                contractDetailEntity.setLastModifiedDate(date);

                expectedRentalPrice += carEntity.getRentalPrice()*diff;
                depositAmount += carEntity.getDepositAmount();
                bookingDetailEntities.add(contractDetailEntity);
            }
            bdr.saveAll(bookingDetailEntities);
            newContract.setExpected_rental_price(expectedRentalPrice);
            newContract.setDeposit_amount(depositAmount);
            br.save(newContract);
            newContract = br.getByCustomerIdAndExpectStartDateAndExpectEndDate(contractRequest.getCustomerId(), contractRequest.getExpectedStartDate(), contractRequest.getExpectedEndDate());
            // save list Booking Detail
            bookingDetailEntities = bdr.getListBookingDetailEntitiesByBookingId(newContract.getId());
            List<ListContractDetailResponse> listContractDetailRespons = ListContractDetailResponse.createListBookingDetailResponse(bookingDetailEntities);
            ContractResponse contractResponse = ContractEntity.convertToBookingResponse(newContract);
            HashMap<String, Object> reponse = new HashMap<>();
            reponse.put("Booking", contractResponse);
            reponse.put("BookingDetail", listContractDetailRespons);
            reponse.put("HadDriver", contractHadDriverReponse);
            responseVo = ResponseVeConvertUntil.createResponseVo(true, "Tạo Hợp đồng thành công", reponse);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        } catch (Exception e) {
            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Lỗi Khi Tạo Mới Hợp đồng", null);
            return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> updateDriver(ContractHadDriverRequest contractHadDriverRequest) {
        ResponseVo responseVo = null;
        //getContact Entiti
        if (contractHadDriverRequest.getContractId() <= 0) {
            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Mã hợp đồng không hợp lệ ID = " + contractHadDriverRequest.getContractId(), null);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        List<ListContractDetailDriverRequest> listContractDetailDriverRequests = contractHadDriverRequest.getListContractDetailDriverRequests();
        for (ListContractDetailDriverRequest l : listContractDetailDriverRequests) {
            if (l.getDriverId() <= 0) {
                responseVo = ResponseVeConvertUntil.createResponseVo(false, "Mã tài xế không hợp lệ", l.getDriverId());
                return new ResponseEntity<>(responseVo, HttpStatus.OK);
            }
            if (l.getBookingDetailId() <= 0) {
                responseVo = ResponseVeConvertUntil.createResponseVo(false, "Mã tài xế không hợp lệ", l.getBookingDetailId());
                return new ResponseEntity<>(responseVo, HttpStatus.OK);
            }
        }
        ContractEntity contractEntity = br.FindByID(contractHadDriverRequest.getContractId());
        if (ObjectUtils.isEmpty(contractEntity)) {
            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Không tìm thấy thông tin hợp đồng ID = " + contractHadDriverRequest.getContractId(), null);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        if (!contractEntity.isHad_driver()) {
            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Hợp đồng trên không thuộc hợp đồng có tài xế", null);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        try {
            for (ListContractDetailDriverRequest l : listContractDetailDriverRequests) {
                ContractDetailEntity contractDetailEntity = bdr.BookingDetail(l.getBookingDetailId());
                if (ObjectUtils.isEmpty(contractDetailEntity)) {
                    responseVo = ResponseVeConvertUntil.createResponseVo(false, "Không tìm thấy thông tin hợp đông ", null);
                    return new ResponseEntity<>(responseVo, HttpStatus.OK);
                }
                DriverEntity entity = dr.GetDriverById(l.getDriverId());
                if (ObjectUtils.isEmpty(entity)) {
                    responseVo = ResponseVeConvertUntil.createResponseVo(false, "Không tìm thấy thông tin tài xế ID= " + l.getDriverId() + "", null);
                    return new ResponseEntity<>(responseVo, HttpStatus.OK);
                }
                DriverEntity checkValidDate = dr.findDriverValidDate(entity.getId(),contractEntity.getExpected_start_date(),contractEntity.getExpected_end_date());
                if (!ObjectUtils.isEmpty(checkValidDate)){
                    responseVo = ResponseVeConvertUntil.createResponseVo(false, "Tài Xế: "+entity.getAccountEntity().getFullName()+" Hiện Đang Có Hợp Đồng Khác", null);
                    return new ResponseEntity<>(responseVo, HttpStatus.OK);
                }
                CarEntity carEntity = contractDetailEntity.getCar();
                LicenseTypeEntity licenseTypeEntity = licenseRepository.getLicenseById(carEntity.getLicenseTypeEntity().getID());
                if(licenseTypeEntity.getID() > entity.getLicenseTypeEntity().getID()){
                    responseVo = ResponseVeConvertUntil.createResponseVo(false, "Tài xế " +entity.getAccountEntity().getFullName()+ " không đủ điều kiện lái loại xe hạng " + licenseTypeEntity.getName_License(), null);
                    return new ResponseEntity<>(responseVo, HttpStatus.OK);
                }
                contractDetailEntity.setDriver_id(entity.getId());
                bdr.save(contractDetailEntity);
            }
            responseVo = ResponseVeConvertUntil.createResponseVo(true, "Cập Nhật Tài Xế Thành Công", null);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);

        } catch (Exception e) {
            responseVo = ResponseVeConvertUntil.createResponseVo(true, "Lỗi Khi Cập Nhật Tài Xế", e.getMessage());
            return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
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
    public ResponseEntity<?> FilterByName(String name, Integer HadDriver,Integer Status,Integer p) {

        p = CheckNullPaging(p);
        Integer size = 5;
        Pageable pageable = PageRequest.of(p, size);
        List<ContractEntity> contractEntities = br.FilterByName(name,HadDriver,Status,pageable);
        List<ContractEntity> contractEntities1 = br.FilterByName1(name,HadDriver,Status);

        return responseResultContract(contractEntities, contractEntities1, size, p);

    }

    @Override
    public ResponseEntity<?> FilterByPhone(String phone, Integer HadDriver,Integer Status,Integer p) {

        p = CheckNullPaging(p);
        Integer size = 5;
        Pageable pageable = PageRequest.of(p, size);
        List<ContractEntity> contractEntities = br.FilterByPhone(phone,HadDriver,Status,pageable);
        List<ContractEntity> contractEntities1 = br.FilterByPhone1(phone,HadDriver,Status);

        return responseResultContract(contractEntities, contractEntities1, size, p);
    }

    @Override
    public ResponseEntity<?> getContractById(Long id) {
        ResponseVo responseVo = null;
        if (id == null) {
            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Chưa có thông tin Id", null);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        try {
            HashMap<String, Object> ObjectResponse = new HashMap<>();
            Long contractId = id;
            ContractEntity contractEntity = br.FindByID(contractId);
            if (ObjectUtils.isEmpty(contractEntity)) {
                responseVo = ResponseVeConvertUntil.createResponseVo(false, "Không tìm thấy thông tin phù hợp", null);
                return new ResponseEntity<>(responseVo, HttpStatus.OK);
            }
            ContractResponse response = ContractEntity.convertToBookingResponse(contractEntity);
            ObjectResponse.put("Booking", response);
            List<ContractDetailEntity> contractDetailEntities = bdr.getListBookingDetailEntitiesByBookingId(contractEntity.getId());
            List<ListContractDetailResponse> listContractDetailRespons = ListContractDetailResponse.createListBookingDetailResponse(contractDetailEntities);
            for (ListContractDetailResponse l : listContractDetailRespons) {
                if (l.getDriverId() > 0) {
                    DriverEntity driverEntity = dr.GetDriverById(l.getDriverId());
                    AccountEntity accountEntity = ar.getCustomerById(driverEntity.getId());
                    l.setDriverName(accountEntity.getFullName());
                }
            }
            ObjectResponse.put("BookingDetail", listContractDetailRespons);
            // if had driver = > put into response
            if (contractEntity.isHad_driver()) {
                ContractHadDriverEntity entity = chdr.getByContractID(id);
                if (!ObjectUtils.isEmpty(entity)) {
                    ContractHadDriverReponse contractHadDriverReponse = ContractHadDriverEntity.convertToContractHadDriverResponse(entity);
                    DistrictsEntity pickUpDistrist = districtRepository.findOneById(entity.getPickup_district_id());
                    contractHadDriverReponse.setPickupDistricAddress(DistrictReponse.createDistricReponse(pickUpDistrist));
                    DistrictsEntity returnDistrist = districtRepository.findOneById(entity.getPickup_district_id());
                    contractHadDriverReponse.setReturnDictricAddress(DistrictReponse.createDistricReponse(returnDistrist));
                    ObjectResponse.put("HadDriver", contractHadDriverReponse);
                }
            }
            responseVo = ResponseVeConvertUntil.createResponseVo(true, "Lấy thông tin Hợp đồng thành công", ObjectResponse);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        } catch (NumberFormatException e) {
            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Id nhập vào không hợp lệ", e);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> update(ContractRequest contractRequest) {
        ResponseVo responseVo = null;
        if (ObjectUtils.isEmpty(contractRequest)) {
            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Booking truyền vào trống", null);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        if (contractRequest.getListCarPlateNumber().isEmpty()) {
            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Bạn chưa chọn xe", null);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        ContractEntity checkIdEsxit =br.FindByID(contractRequest.getId());
        if(ObjectUtils.isEmpty(br)){
            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Không có kết quả trả về với id = "+contractRequest.getId()+" ", null);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        // Check if car valid from start date to end date

        //End
        if(checkIdEsxit.isHad_driver() != contractRequest.isHad_driver()){

        }
        ContractEntity entity = ContractRequest.convertToContractEntity(contractRequest);


        return null;
    }


}
