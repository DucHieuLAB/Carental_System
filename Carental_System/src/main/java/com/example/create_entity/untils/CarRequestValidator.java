package com.example.create_entity.untils;

import com.example.create_entity.common.ValidError;
import com.example.create_entity.dto.Request.GetCarReQuest;

import java.util.ArrayList;
import java.util.List;

public class CarRequestValidator {
    public static List<ValidError> validateCarRequest(GetCarReQuest carReQuest){
    List<ValidError> errors = new ArrayList<>();
    // contractId
        if(carReQuest.getContractId() <= 0){
            ValidError error = new ValidError("ContractID","Contract Id đang trống");
            errors.add(error);
        }
        // plateNumber
        if (carReQuest.getPlateNumber().size() <= 0){
            ValidError error = new ValidError("ContractID","Danh xách xe đang trống");
            errors.add(error);
        }
    return errors;
    }
}
