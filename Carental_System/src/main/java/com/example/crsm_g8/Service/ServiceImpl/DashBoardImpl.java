package com.example.crsm_g8.Service.ServiceImpl;

import com.example.crsm_g8.Entity.*;
import com.example.crsm_g8.Repository.*;
import com.example.crsm_g8.Service.IService.DashBoardService;
import com.example.crsm_g8.dto.Response.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DashBoardImpl implements DashBoardService {

    @Autowired
    ContractRepository contractRepository;

    @Autowired
    CustomerRepository customerRepository;


    @Autowired
    DriverRepository driverRepository;

    @Autowired
    ContractDetailRepository contractDetailRepository;

    @Autowired
    PaymentsRepository paymentsRepository;

    @Override
    public ResponseEntity<?> CountRequestContract() {
        ResponseVo responseVo = new ResponseVo();
        List<ContractEntity> contractEntities = contractRepository.ListRequestContract();
        responseVo.setStatus(true);
        responseVo.setMessage("Số lươt yêu cầu thuê xe trong hệ thống ");
        responseVo.setData(contractEntities.size());
        return new ResponseEntity<>(responseVo, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> Countnewcustomer() {
        ResponseVo responseVo = new ResponseVo();
        List<CustomerEntity> customerEntityList = customerRepository.CountNewCustomer();
        responseVo.setStatus(true);
        responseVo.setMessage("Tổng số khách hàng mới đăng kí vào hệ thống trong 30 ngày ");
        responseVo.setData(customerEntityList.size());
        return new ResponseEntity<>(responseVo, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> Countdriver() {
        ResponseVo responseVo = new ResponseVo();
        List<DriverEntity> driverEntityList = driverRepository.countdriver();
        responseVo.setStatus(true);
        responseVo.setMessage("Tổng số driver đang hoạt động ");
        responseVo.setData(driverEntityList.size());
        return new ResponseEntity<>(responseVo, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> CountCloseContract() {
        ResponseVo responseVo = new ResponseVo();
        List<ContractEntity> contractEntities = contractRepository.ListCloseContract();
        responseVo.setStatus(true);
        responseVo.setMessage("Số lần hủy hợp đồng trong 30 ngày  ");
        responseVo.setData(contractEntities.size());
        return new ResponseEntity<>(responseVo, HttpStatus.OK);
    }

    Integer Capacity_4 = 0;
    Integer Capacity_7 = 0;
    Integer Capacity_16 = 0;
    Integer Capacity_29 = 0;
    Integer Capacity_47 = 0;

    @Override
    public ResponseEntity<?> CountCar() {
        ResponseVo responseVo = new ResponseVo();

        List<ContractDetailEntity> contractDetailEntityList = contractDetailRepository.CountCar();
        contractDetailEntityList.forEach(ContractDetailEntity -> {
            if (ContractDetailEntity.getCar().getCapacity() == 4) {
                Capacity_4++;
            } else if (ContractDetailEntity.getCar().getCapacity() == 7) {
                Capacity_7++;
            } else if (ContractDetailEntity.getCar().getCapacity() == 16) {
                Capacity_16++;
            } else if (ContractDetailEntity.getCar().getCapacity() == 29) {
                Capacity_29++;
            } else if (ContractDetailEntity.getCar().getCapacity() == 45) {
                Capacity_47++;
            }
        });
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(Capacity_4);
        arrayList.add(Capacity_7);
        arrayList.add(Capacity_16);
        arrayList.add(Capacity_29);
        arrayList.add(Capacity_47);
        Capacity_4 = 0;
        Capacity_7 = 0;
        Capacity_16 = 0;
        Capacity_29 = 0;
        Capacity_47 = 0;

        responseVo.setStatus(true);
        responseVo.setMessage("Danh mục xe đc thuê nhiều nhất trong tháng : ");
        responseVo.setData(arrayList);
        return new ResponseEntity<>(responseVo, HttpStatus.OK);
    }

    Double Paid_1 = 0.0;
    Double Paid_2 = 0.0;
    Double Paid_3 = 0.0;
    Double Paid_4 = 0.0;

    public ArrayList<Object> ResponseTotalPaid(List<PaymentEntity> paymentEntityList) {
        ArrayList<Object> objects = new ArrayList<>();
        paymentEntityList.forEach(PaymentEntity -> {
            if (PaymentEntity.getContract().getCreatedDate().getMonth() == 1 ||
                    PaymentEntity.getContract().getCreatedDate().getMonth() == 2 ||
                    PaymentEntity.getContract().getCreatedDate().getMonth() == 3) {
                Paid_1 += PaymentEntity.getPaid();
            } else if (PaymentEntity.getContract().getCreatedDate().getMonth() == 4 ||
                    PaymentEntity.getContract().getCreatedDate().getMonth() == 6 ||
                    PaymentEntity.getContract().getCreatedDate().getMonth() == 5) {
                Paid_2 += PaymentEntity.getPaid();
            } else if (PaymentEntity.getContract().getCreatedDate().getMonth() == 9 ||
                    PaymentEntity.getContract().getCreatedDate().getMonth() == 7 ||
                    PaymentEntity.getContract().getCreatedDate().getMonth() == 8) {
                Paid_3 += PaymentEntity.getPaid();
            } else if (PaymentEntity.getContract().getCreatedDate().getMonth() == 10 ||
                    PaymentEntity.getContract().getCreatedDate().getMonth() == 11 ||
                    PaymentEntity.getContract().getCreatedDate().getMonth() == 12) {
                Paid_4 += PaymentEntity.getPaid();
            }
        });

        objects.add(Paid_1.longValue());
        objects.add(Paid_2.longValue());
        objects.add(Paid_3.longValue());
        objects.add(Paid_4.longValue());

        Paid_1 = 0.0;
        Paid_2 = 0.0;
        Paid_3 = 0.0;
        Paid_4 = 0.0;
        return objects;
    }

    @Override
    public ResponseEntity<?> TotalPaidHadDriver() {
        ResponseVo responseVo = new ResponseVo();

        List<PaymentEntity> paymentEntityList = paymentsRepository.TotalPaidHadDriverQuarter();
        ArrayList<Object> objects = this.ResponseTotalPaid(paymentEntityList);
        responseVo.setMessage("Doanh thu cho thuê xe có tài xế trong 1 năm ");
        responseVo.setData(objects);
        responseVo.setStatus(true);
        return new ResponseEntity<>(responseVo, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> TotalPaidSelfDriving() {
        ResponseVo responseVo = new ResponseVo();
        ArrayList<Object> objects;
        List<PaymentEntity> paymentEntityList = paymentsRepository.TotalPaidSelfDrivingQuarter();
        objects = this.ResponseTotalPaid(paymentEntityList);
        responseVo.setMessage("Doanh thu cho thuê xe tự lái trong 1 năm ");
        responseVo.setData(objects);
        responseVo.setStatus(true);
        return new ResponseEntity<>(responseVo, HttpStatus.OK);
    }
}
