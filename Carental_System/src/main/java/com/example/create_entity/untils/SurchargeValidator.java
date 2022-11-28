package com.example.create_entity.untils;

import com.example.create_entity.common.ValidError;
import com.example.create_entity.dto.Request.SurchargeRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SurchargeValidator {
    public List<ValidError> validateSurchargeRequest(SurchargeRequest purchargeRequest) {
        List<ValidError> errors = new ArrayList<>();
//        contractId
        if (purchargeRequest.getContractId() <= 0){
            ValidError error = new ValidError("Mã hợp đồng","Mã hợp đồng không hợp lệ ");
            errors.add(error);
        }
//        amount
        if (purchargeRequest.getAmount() <= 0){
            ValidError error = new ValidError("Số tiền thu","Số tiền thu không thể nhỏ hơn hoặc bằng 0");
            errors.add(error);
        }
        return errors;
    }
}
