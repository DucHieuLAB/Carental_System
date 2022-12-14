package com.example.crsm_g8.untils;

import com.example.crsm_g8.common.ValidError;
import com.example.crsm_g8.dto.Request.DepositRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DepositValidator {

    public List<ValidError> validateDepositRequest(DepositRequest depositRequest) {
        List<ValidError> errors = new ArrayList<>();
//     contractId;
        if (depositRequest.getContractId() <= 0) {
            ValidError error = new ValidError("ContractID", "Mã hợp đồng không hợp lệ");
            errors.add(error);
        }
//     accountId;
        if (depositRequest.getAccountId() <= 0) {
            ValidError error = new ValidError("AccountID", "Mã tài khoản nhân viên không hợp lệ");
            errors.add(error);
        }
//     paid;
        if (depositRequest.getPaid() <= 0) {
            ValidError error = new ValidError("Paid", "Số tiền đã trả không hợp lệ");
            errors.add(error);
        }
        return errors;
    }
}
