package com.example.crms_g8.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DriverByCarByContractRequest {
    String plateNumber;
    Date expectedStartDate;
    Date expectedEndDate;
}
