package com.example.create_entity.Service;

import com.example.create_entity.Entity.*;

import com.example.create_entity.Repository.*;
import com.example.create_entity.common.Error;
import com.example.create_entity.common.ValidError;
import com.example.create_entity.dto.Request.*;
import com.example.create_entity.dto.Response.*;

import com.example.create_entity.Repository.AccountRepository;
import com.example.create_entity.Repository.ContractDetailRepository;
import com.example.create_entity.Repository.ContractRepository;
import com.example.create_entity.Repository.ParkingRepository;

import com.example.create_entity.untils.CarRequestValidator;
import com.example.create_entity.untils.DepositValidator;
import com.example.create_entity.untils.ResponseVeConvertUntil;
import org.apache.http.annotation.Contract;
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
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    PaymentsRepository paymentsRepository;
    @Autowired
    StaffRepository staffRepository;
    @Autowired
    DepositValidator depositValidator;

    @Override
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
        for (String plateNumber : contractRequest.getListCarPlateNumber()
        ) {
            CarEntity carEntity = cr.findCarEntityByPlateNumber(plateNumber);
            if (ObjectUtils.isEmpty(carEntity)) {
                responseVo = ResponseVeConvertUntil.createResponseVo(false, "không tìm thấy xe biển " + plateNumber, null);
                return new ResponseEntity<>(responseVo, HttpStatus.OK);
            }
        }
        try {
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
            CustomerEntity customer = customerRepository.GetCustomerID(contractRequest.getCustomerId());
            if (ObjectUtils.isEmpty(customer)) {
                responseVo = ResponseVeConvertUntil.createResponseVo(false, "Thông tin người dùng không chính xác", null);
                return new ResponseEntity<>(responseVo, HttpStatus.OK);
            }
            newContract.setPickup_parking(pickUpParking.get());
            newContract.setReturn_parking(returnParking.get());
            newContract.setCustomer(customer);
            newContract.setQuantity(contractRequest.getListCarPlateNumber().size());

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
                CarEntity exsitCarEntity = cr.checkCarValidInTime(contractRequest.getExpectedStartDate(), contractRequest.getExpectedEndDate(), carPlateNumber);
                if (!ObjectUtils.isEmpty(exsitCarEntity)) {
                    throw new Exception("Xe Bạn chọn hiện không khả dụng trong thời gian mong muốn");
                }
                contractDetailEntity.setBooking(newContract);
                contractDetailEntity.setCar(carEntity);
                contractDetailEntity.setLastModifiedDate(date);
                expectedRentalPrice += carEntity.getRentalPrice() * diff;
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
            ContractResponse contractResponse = ContractEntity.convertToContractResponse(newContract);
            HashMap<String, Object> reponse = new HashMap<>();
            reponse.put("Booking", contractResponse);
            reponse.put("BookingDetail", listContractDetailRespons);
            reponse.put("HadDriver", contractHadDriverReponse);
            responseVo = ResponseVeConvertUntil.createResponseVo(true, "Tạo Hợp đồng thành công", reponse);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        } catch (Exception e) {
            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Lỗi Khi Tạo Mới Hợp đồng", e.getMessage());
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
        ReposMesses messes = new ReposMesses();
        try {
            Pageable pageable = PageRequest.of(p, 5);

            Page<ContractEntity> page = br.ListContract(pageable);

            List<ContractResponse> contractResponse = new ArrayList<>();

            page.forEach(BookingEntity -> {

                ContractResponse contractResponse1 = ContractEntity.convertToContractResponse(BookingEntity);
                contractResponse.add(contractResponse1);
            });

            PagingContract pagingContract = new PagingContract();

            pagingContract.setContractResponseList(contractResponse);
            pagingContract.setTotalPage(page.getTotalPages());
            pagingContract.setNumberPage(page.getNumber() + 1);


            if (!page.isEmpty()) {
                return new ResponseEntity<>(pagingContract, HttpStatus.OK);
            } else {
                messes.setMess("Không có dữ liệu bảng hợp đồng !");
                return new ResponseEntity<>(messes, HttpStatus.OK);
            }
        } catch (Exception e) {
            messes.setMess(e.getMessage());
            return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public ResponseEntity<?> ListContract2(Integer p) {
        if (p == null) {
            p = 0;
        } else if (p > 0) {
            p = p - 1;
        }
        ReposMesses messes = new ReposMesses();

        try {
            Pageable pageable = PageRequest.of(p, 5);

            Page<ContractEntity> page = br.ListContract_2(pageable);

            List<ContractResponse> contractResponse = new ArrayList<>();

            page.forEach(BookingEntity -> {

                ContractResponse contractResponse1 = ContractEntity.convertToContractResponse(BookingEntity);
                contractResponse.add(contractResponse1);
            });

            PagingContract pagingContract = new PagingContract();

            pagingContract.setContractResponseList(contractResponse);
            pagingContract.setTotalPage(page.getTotalPages());
            pagingContract.setNumberPage(page.getNumber() + 1);


            if (!page.isEmpty()) {
                return new ResponseEntity<>(pagingContract, HttpStatus.OK);
            } else {
                messes.setMess("Không có dữ liệu bảng hợp đồng !");
                return new ResponseEntity<>(messes, HttpStatus.OK);
            }
        } catch (Exception e) {
            messes.setMess(e.getMessage());
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
            contractResponse = com.example.create_entity.Entity.ContractEntity.convertToContractResponse(ContractEntity);
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
            messes.setMess("Không tìm thấy dữ liệu yêu cầu  ! ");
            return new ResponseEntity<>(messes, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(pagingContract, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> FilterByName(String name, Integer HadDriver, Integer Status, Integer p) {

        p = CheckNullPaging(p);
        Integer size = 5;
        Pageable pageable = PageRequest.of(p, size);
        List<ContractEntity> contractEntities = br.FilterByName(name, HadDriver, Status, pageable);
        List<ContractEntity> contractEntities1 = br.FilterByName1(name, HadDriver, Status);

        return responseResultContract(contractEntities, contractEntities1, size, p);

    }

    @Override
    public ResponseEntity<?> FilterByPhone(String phone, Integer HadDriver, Integer Status, Integer p) {

        p = CheckNullPaging(p);
        Integer size = 5;
        Pageable pageable = PageRequest.of(p, size);
        List<ContractEntity> contractEntities = br.FilterByPhone(phone, HadDriver, Status, pageable);
        List<ContractEntity> contractEntities1 = br.FilterByPhone1(phone, HadDriver, Status);

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
            ContractResponse response = ContractEntity.convertToContractResponse(contractEntity);
            ObjectResponse.put("Booking", response);
            List<ContractDetailEntity> contractDetailEntities = bdr.getListBookingDetailEntitiesByBookingId(contractEntity.getId());
            List<ListContractDetailResponse> listContractDetailRespons = ListContractDetailResponse.createListBookingDetailResponse(contractDetailEntities);
            for (ListContractDetailResponse l : listContractDetailRespons) {
                if (l.getDriverId() > 0) {
                    DriverEntity driverEntity = dr.GetDriverById(l.getDriverId());
                    l.setDriverName(driverEntity.getFullName());
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
    public ResponseEntity<?> getListContractByCustomerId(long customerId) {
        ResponseVo responseVo = null;
        List<ContractEntity> contractEntities = br.getByCustomerId(customerId);
        if (contractEntities.isEmpty()) {
            responseVo = ResponseVeConvertUntil.createResponseVo(false, "khách hàng chưa có hợp đồng nào", null);
            return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
        }
        List<ContractResponse> responses = new ArrayList<>();
        for (ContractEntity entity : contractEntities) {
            ContractResponse tmp = ContractEntity.convertToContractResponse(entity);
            responses.add(tmp);
        }
        responseVo = ResponseVeConvertUntil.createResponseVo(true, "Danh sách hợp đồng", responses);
        return new ResponseEntity<>(responseVo, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> cancelRenting(long id, int i) {
        ContractEntity contractEntity = br.FindByID(id);
        if (i != 2 || i != 1) {
            ResponseVo responseVo = ResponseVeConvertUntil.createResponseVo(false, "role chưa hợp lệ", null);
            return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
        }
        if (ObjectUtils.isEmpty(contractEntity)) {
            ResponseVo responseVo = ResponseVeConvertUntil.createResponseVo(false, "Mã hợp đồng không đúng", null);
            return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
        }
        try {
            if (i == 1) {
                contractEntity.setStatus(0);
            }
            if (i == 2) {
                if (contractEntity.getStatus() == 1) {
                    contractEntity.setStatus(0);
                }
            }
            br.save(contractEntity);
            ResponseVo responseVo = ResponseVeConvertUntil.createResponseVo(true, "Hủy hợp đồng thành công", null);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        } catch (Exception e) {
            ResponseVo responseVo = ResponseVeConvertUntil.createResponseVo(false, "Lỗi khi cố gắng hủy hợp đồng ", e.getMessage());
            return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> updateDriverAndRealPrice(ContractDriverRealPriceRequest contractDriverRealPriceRequest) {
        ResponseVo responseVo = null;
        if (contractDriverRealPriceRequest.getContractId() <= 0) {
            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Thông tin hợp đồng không đúng", null);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        if (contractDriverRealPriceRequest.getReal_price() <= 0) {
            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Giá cho thuê không thể bé hơn hoặc bằng 0", null);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        // tim contract ti contractId request
        ContractEntity contractEntity = br.FindByID(contractDriverRealPriceRequest.getContractId());
        // if contract Entity is null mean contractId is invalid
        if (ObjectUtils.isEmpty(contractEntity)) {
            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Không tìm thấy thông tin hợp đồng ID = " + contractDriverRealPriceRequest.getContractId(), null);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        }
        // Get List ContractDetailRequest
        List<ListContractDetailDriverRequest> listContractDetailDriverRequests = contractDriverRealPriceRequest.getListContractDetailDriverRequests();
        // check number contract  detail valid must listContractDetailDriverRequests.size  <= count
        int count = bdr.getCountContractDetail(contractDriverRealPriceRequest.getContractId());
        if (listContractDetailDriverRequests.size() > count) {
            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Danh sach chi tiet hop dong khong chinh xac", null);
        }
        for (ListContractDetailDriverRequest l : listContractDetailDriverRequests) {
            if (l.getDriverId() <= 0) {
                responseVo = ResponseVeConvertUntil.createResponseVo(false, "Mã tài xế không hợp lệ", l.getDriverId());
                return new ResponseEntity<>(responseVo, HttpStatus.OK);
            }
            if (l.getBookingDetailId() <= 0) {
                responseVo = ResponseVeConvertUntil.createResponseVo(false, "Mã  không hợp lệ", l.getBookingDetailId());
                return new ResponseEntity<>(responseVo, HttpStatus.OK);
            }
        }
//
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
//
                DriverEntity entity = dr.GetDriverById(l.getDriverId());
                if (ObjectUtils.isEmpty(entity)) {
                    responseVo = ResponseVeConvertUntil.createResponseVo(false, "Không tìm thấy thông tin tài xế ID= " + l.getDriverId() + "", null);
                    return new ResponseEntity<>(responseVo, HttpStatus.OK);
                }
                int countByDriver = bdr.getCountContractDetailByDriverId(entity.getId());
                DriverEntity checkValidDate = null;
                // if driver dont had any contract
                if (countByDriver > 0) {
                    checkValidDate = dr.findDriverValidDate(entity.getId(), contractEntity.getExpected_start_date(), contractEntity.getExpected_end_date());
                    if (!ObjectUtils.isEmpty(checkValidDate)) {
                        responseVo = ResponseVeConvertUntil.createResponseVo(false, "Tài Xế: " + entity.getFullName() + " hiện đang có hợp đồng khác", null);
                        return new ResponseEntity<>(responseVo, HttpStatus.OK);
                    }
                }
                CarEntity carEntity = contractDetailEntity.getCar();
                LicenseTypeEntity licenseTypeEntity = licenseRepository.getLicenseById(carEntity.getLicenseTypeEntity().getID());
                if (licenseTypeEntity.getID() > entity.getLicenseTypeEntity().getID()) {
                    responseVo = ResponseVeConvertUntil.createResponseVo(false, "Tài xế " + entity.getFullName() + " không đủ điều kiện lái loại xe hạng " + licenseTypeEntity.getName_License(), null);
                    return new ResponseEntity<>(responseVo, HttpStatus.OK);
                }
                contractDetailEntity.setDriverEntity(entity);
                bdr.save(contractDetailEntity);
            }
            contractEntity.setReal_price(contractDriverRealPriceRequest.getReal_price());
            contractEntity.setStatus(2);
            br.save(contractEntity);
            contractEntity = br.FindByID(contractDriverRealPriceRequest.getContractId());
            ContractResponse response = ContractEntity.convertToContractResponse(contractEntity);
            List<ContractDetailEntity> bookingDetailEntities = bdr.getListBookingDetailEntitiesByBookingId(contractEntity.getId());
            List<ListContractDetailResponse> listContractDetailRespons = ListContractDetailResponse.createListBookingDetailResponse(bookingDetailEntities);
            ContractHadDriverEntity entity = chdr.getByContractID(response.getBookingId());
            ContractHadDriverReponse contractHadDriverReponse = ContractHadDriverEntity.convertToContractHadDriverResponse(entity);
            HashMap<String, Object> reponse = new HashMap<>();
            reponse.put("Booking", response);
            reponse.put("BookingDetail", listContractDetailRespons);
            reponse.put("HadDriver", contractHadDriverReponse);
            responseVo = ResponseVeConvertUntil.createResponseVo(true, "Cập Nhật Tài Xế Và Giá thuê thành công", reponse);
            return new ResponseEntity<>(responseVo, HttpStatus.OK);
        } catch (Exception e) {
            responseVo = ResponseVeConvertUntil.createResponseVo(true, "Lỗi khi cập nhật giá thuê và tài xế", e.getMessage());
            return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public ResponseEntity<?> confirmContract(long id, int role) {
        if (role == 2) {

        }
        return null;
    }

    @Override
    public ResponseEntity<?> confirmDeposit(DepositRequest depositRequest) {
        // validate Request
        List<ValidError> errors = depositValidator.validateDepositRequest(depositRequest);
        // if not suscess
        if (errors.size()>0){
            ResponseVo responseVo = ResponseVeConvertUntil.createResponseVo(false, "Thông tin request không hợp lệ", errors);
            return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
        }
        // verify
        ContractEntity contractEntity = br.findByIdAndStatus2(depositRequest.getContractId());
        if (ObjectUtils.isEmpty(contractEntity)) {
            ResponseVo responseVo = ResponseVeConvertUntil.createResponseVo(false, "Không tìm thấy thông tin hợp đồng", null);
            return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
        }
        StaffEntity staffEntity = staffRepository.staffEntity(depositRequest.getAccountId());
        if (ObjectUtils.isEmpty(staffEntity)) {
            ResponseVo responseVo = ResponseVeConvertUntil.createResponseVo(false, "Không tìm thấy nhân viên ID = " + depositRequest.getAccountId(), null);
            return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
        }
        //save payment into Database
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setContract(contractEntity);
        paymentEntity.setStaffEntity(staffEntity);
        paymentEntity.setDescription(depositRequest.getDescription());
        paymentEntity.setPaid(depositRequest.getPaid());
        paymentEntity.setLastModifiedDate(new Date(System.currentTimeMillis()));
        // cal  Receivables; = realprice - total paid
        paymentEntity.setReceivables(contractEntity.getReal_price() - depositRequest.getPaid());
        paymentEntity.setTotalAmount(contractEntity.getReal_price());
        paymentsRepository.save(paymentEntity);
        // update Contract id = 4;
        contractEntity.setStatus(3);
        contractEntity.setLastModifiedDate(new Date(System.currentTimeMillis()));
        br.save(contractEntity);
        // response
//        HashMap<String,Object> response = new HashMap<>();
//        Contract Response
//        Payment Response
        PaymentResponse paymentRespone = new PaymentResponse(depositRequest.getPaid(),contractEntity.getReal_price() - depositRequest.getPaid(),contractEntity.getReal_price());
        ResponseVo responseVo = new ResponseVo(true,"Cập nhập thông tin thành công",paymentRespone);
        return new ResponseEntity<>(responseVo,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> comfirmGetCar(GetCarReQuest CarReQuest) throws Exception{
        // validate
        List<ValidError> errors = CarRequestValidator.validateCarRequest(CarReQuest);
        // if not success
        if (errors.size() > 0){
            ResponseVo responseVo = new ResponseVo(false,"Lỗi Bad Request",errors);
            return new ResponseEntity<>(responseVo,HttpStatus.BAD_REQUEST);
        }
        // if success
        ContractEntity contractEntity = br.findByIdAndStatus3(CarReQuest.getContractId());
        if(ObjectUtils.isEmpty(contractEntity)){
            ResponseVo responseVo = new ResponseVo(false,"Hợp đồng không tồn tại",null);
            return new ResponseEntity<>(responseVo,HttpStatus.BAD_REQUEST);
        }
        List<String> plateNumber = CarReQuest.getPlateNumber();
        for (String CarPlateNumber : plateNumber){
            ContractDetailEntity contractDetailEntity = bdr.findContractDetailByContractIdByPlateNumber(CarReQuest.getContractId(),CarPlateNumber);
            if (ObjectUtils.isEmpty(contractDetailEntity)){
               throw new Exception("Xe biển số "+ CarPlateNumber+" Không hợp lệ" );
            }
            contractDetailEntity.setReal_pick_up_date(new Date(System.currentTimeMillis()));
            bdr.save(contractDetailEntity);
        }
        contractEntity.setLastModifiedDate(new Date(System.currentTimeMillis()));
        contractEntity.setStatus(4);
        br.save(contractEntity);
        // response
        ResponseVo responseVo = new ResponseVo(true,"Cập nhật thông tin thành công",null);
        return new ResponseEntity<>(responseVo,HttpStatus.OK);
    }


//    @Override
//    public ResponseEntity<?> updateRealPrice(ContractRealPriceRequest contractRealPriceRequest) {
//        ResponseVo responseVo = null;
//        if (ObjectUtils.isEmpty(contractRealPriceRequest)) {
//            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Thông tin request trống", null);
//            return new ResponseEntity<>(responseVo, HttpStatus.OK);
//        }
//        if (contractRealPriceRequest.getContractId() <= 0) {
//            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Thông tin hợp đồng không đúng", null);
//            return new ResponseEntity<>(responseVo, HttpStatus.OK);
//        }
//        if (contractRealPriceRequest.getReal_price() <= 0) {
//            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Giá cho thuê không thể bé hơn hoặc bằng 0", null);
//            return new ResponseEntity<>(responseVo, HttpStatus.OK);
//        }
//        ContractEntity contractEntity = br.FindByID(contractRealPriceRequest.getContractId());
//        if (ObjectUtils.isEmpty(contractEntity)) {
//            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Thông tin hợp đồng ID = " + contractRealPriceRequest.getContractId(), null);
//            return new ResponseEntity<>(responseVo, HttpStatus.OK);
//        }
//        contractEntity.setReal_price(contractRealPriceRequest.getReal_price());
//        br.save(contractEntity);
//        contractEntity = br.FindByID(contractRealPriceRequest.getContractId());
//        ContractResponse response = ContractEntity.convertToContractResponse(contractEntity);
//        responseVo = ResponseVeConvertUntil.createResponseVo(true, "Thay đổi giá thuê thành công ", response);
//        return new ResponseEntity<>(responseVo, HttpStatus.OK);
//    }
//
//
//    @Override
//    public ResponseEntity<?> update(ContractRequest contractRequest) {
//        ResponseVo responseVo = null;
//        if (ObjectUtils.isEmpty(contractRequest)) {
//            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Booking truyền vào trống", null);
//            return new ResponseEntity<>(responseVo, HttpStatus.OK);
//        }
//        if (contractRequest.getListCarPlateNumber().isEmpty()) {
//            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Bạn chưa chọn xe", null);
//            return new ResponseEntity<>(responseVo, HttpStatus.OK);
//        }
//        ContractEntity checkIdEsxit = br.FindByID(contractRequest.getId());
//        if (ObjectUtils.isEmpty(br)) {
//            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Không có kết quả trả về với id = " + contractRequest.getId() + " ", null);
//            return new ResponseEntity<>(responseVo, HttpStatus.OK);
//        }
//        // Check if car valid from start date to end date
//        for (String carPlateNumber : contractRequest.getListCarPlateNumber()) {
//            CarEntity carEntity = cr.findCarEntityByPlateNumber(carPlateNumber);
//            if (ObjectUtils.isEmpty(carEntity)) {
//                responseVo = ResponseVeConvertUntil.createResponseVo(false, "Không tìm thấy kết quả xe biển " + carPlateNumber + " ", null);
//                return new ResponseEntity<>(responseVo, HttpStatus.OK);
//            }
//            carEntity = cr.checkCarValidInTime(contractRequest.getExpectedStartDate(), contractRequest.getExpectedEndDate(), carPlateNumber);
//            if (ObjectUtils.isEmpty(carEntity)) {
//                responseVo = ResponseVeConvertUntil.createResponseVo(false, "Xe biển số " + carPlateNumber + " không đủ điều kiện thời gian để tạo hợp đồng ", null);
//                return new ResponseEntity<>(responseVo, HttpStatus.OK);
//            }
//
//        }
//
//        //End
//        if (checkIdEsxit.isHad_driver() != contractRequest.isHad_driver()) {
//
//        }
//        ContractEntity entity = ContractRequest.convertToContractEntity(contractRequest);
//
//
//        return null;
//    }

    //
//    @Override
//    public ResponseEntity<?> updateDriver(ContractHadDriverRequest contractHadDriverRequest) {
//        ResponseVo responseVo = null;
//        //getContact Entiti
//        if (contractHadDriverRequest.getContractId() <= 0) {
//            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Mã hợp đồng không hợp lệ ID = " + contractHadDriverRequest.getContractId(), null);
//            return new ResponseEntity<>(responseVo, HttpStatus.OK);
//        }
//        List<ListContractDetailDriverRequest> listContractDetailDriverRequests = contractHadDriverRequest.getListContractDetailDriverRequests();
//        for (ListContractDetailDriverRequest l : listContractDetailDriverRequests) {
//            if (l.getDriverId() <= 0) {
//                responseVo = ResponseVeConvertUntil.createResponseVo(false, "Mã tài xế không hợp lệ", l.getDriverId());
//                return new ResponseEntity<>(responseVo, HttpStatus.OK);
//            }
//            if (l.getBookingDetailId() <= 0) {
//                responseVo = ResponseVeConvertUntil.createResponseVo(false, "Mã tài xế không hợp lệ", l.getBookingDetailId());
//                return new ResponseEntity<>(responseVo, HttpStatus.OK);
//            }
//        }
//        ContractEntity contractEntity = br.FindByID(contractHadDriverRequest.getContractId());
//        if (ObjectUtils.isEmpty(contractEntity)) {
//            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Không tìm thấy thông tin hợp đồng ID = " + contractHadDriverRequest.getContractId(), null);
//            return new ResponseEntity<>(responseVo, HttpStatus.OK);
//        }
//        if (!contractEntity.isHad_driver()) {
//            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Hợp đồng trên không thuộc hợp đồng có tài xế", null);
//            return new ResponseEntity<>(responseVo, HttpStatus.OK);
//        }
//        //Update Driver
//        try {
//            for (ListContractDetailDriverRequest l : listContractDetailDriverRequests) {
//                ContractDetailEntity contractDetailEntity = bdr.BookingDetail(l.getBookingDetailId());
//                if (ObjectUtils.isEmpty(contractDetailEntity)) {
//                    responseVo = ResponseVeConvertUntil.createResponseVo(false, "Không tìm thấy thông tin hợp đông ", null);
//                    return new ResponseEntity<>(responseVo, HttpStatus.OK);
//                }
//                DriverEntity entity = dr.GetDriverById(l.getDriverId());
//                if (ObjectUtils.isEmpty(entity)) {
//                    responseVo = ResponseVeConvertUntil.createResponseVo(false, "Không tìm thấy thông tin tài xế ID= " + l.getDriverId() + "", null);
//                    return new ResponseEntity<>(responseVo, HttpStatus.OK);
//                }
//                DriverEntity checkValidDate = dr.findDriverValidDate(entity.getId(), contractEntity.getExpected_start_date(), contractEntity.getExpected_end_date());
//                if (!ObjectUtils.isEmpty(checkValidDate)) {
//                    responseVo = ResponseVeConvertUntil.createResponseVo(false, "Tài Xế: " + entity.getAccountEntity().getFullName() + " Hiện Đang Có Hợp Đồng Khác", null);
//                    return new ResponseEntity<>(responseVo, HttpStatus.OK);
//                }
//                CarEntity carEntity = contractDetailEntity.getCar();
//                LicenseTypeEntity licenseTypeEntity = licenseRepository.getLicenseById(carEntity.getLicenseTypeEntity().getID());
//                if (licenseTypeEntity.getID() > entity.getLicenseTypeEntity().getID()) {
//                    responseVo = ResponseVeConvertUntil.createResponseVo(false, "Tài xế " + entity.getAccountEntity().getFullName() + " không đủ điều kiện lái loại xe hạng " + licenseTypeEntity.getName_License(), null);
//                    return new ResponseEntity<>(responseVo, HttpStatus.OK);
//                }
//                contractDetailEntity.setDriver_id(entity.getId());
//
//                bdr.save(contractDetailEntity);
//            }
//            responseVo = ResponseVeConvertUntil.createResponseVo(true, "Cập Nhật Tài Xế Thành Công", null);
//            return new ResponseEntity<>(responseVo, HttpStatus.OK);
//
//        } catch (Exception e) {
//            responseVo = ResponseVeConvertUntil.createResponseVo(true, "Lỗi Khi Cập Nhật Tài Xế", e.getMessage());
//            return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
//        }
//    }
}
