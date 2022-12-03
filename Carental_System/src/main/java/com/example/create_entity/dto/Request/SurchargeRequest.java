package com.example.create_entity.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SurchargeRequest {
    private long contractId;
    private double amount;
    private String note;
    private long staffAccountId;
}
