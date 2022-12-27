package com.example.crms_g8.untils;

import com.example.crms_g8.common.ValidError;
import com.example.crms_g8.dto.Request.SurchargeRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SurchargeValidator {
    public List<ValidError> validateSurchargeRequest(SurchargeRequest SurchargeRequest) {
        List<ValidError> errors = new ArrayList<>();
//        contractId
        if (SurchargeRequest.getContractId() <= 0){
            ValidError error = new ValidError("Mã hợp đồng","Mã hợp đồng không hợp lệ ");
            errors.add(error);
        }
//        amount
        if (SurchargeRequest.getAmount() == 0){
            ValidError error = new ValidError("Số tiền thu","Số tiền thu không thể bằng 0");
            errors.add(error);
        }

        if (SurchargeRequest.getDriverAccountId() <= 0 && SurchargeRequest.getStaffAccountId() <= 0){
            ValidError error = new ValidError("Thông tin người tạo","Thông tin nhân viên hoặc tài xế lỗi");
            errors.add(error);
        }

        if (SurchargeRequest.getDriverAccountId() > 0 && SurchargeRequest.getStaffAccountId() > 0){
            ValidError error = new ValidError("Thông tin người tạo","Không thể có đồng thời Nhân viên và Tài xế là người tạo phụ thu");
            errors.add(error);
        }
        return errors;
    }
}
