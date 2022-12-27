package com.example.crms_g8.untils;

import com.example.crms_g8.common.ValidError;
import com.example.crms_g8.dto.Request.PaymentRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class PaymentRequestValidator {

    public List<ValidError> validatePaymentRequest(PaymentRequest paymentRequest){
        List<ValidError> errors = new ArrayList<>();
        if (paymentRequest.getContractId() <= 0){
            ValidError error = new ValidError("ContractID", "Mã hợp đồng không hợp lệ");
            errors.add(error);
        }
        if (paymentRequest.getPaid() == 0){
            ValidError error = new ValidError("ContractID", "Số tiền không thể bằng 0 ");
            errors.add(error);
        }
        return errors;
    }
}
