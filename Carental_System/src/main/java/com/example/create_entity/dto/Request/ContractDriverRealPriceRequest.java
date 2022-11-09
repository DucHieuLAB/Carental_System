package com.example.create_entity.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContractDriverRealPriceRequest {
    private long contractId;
    private double real_price;
    List<ListContractDetailDriverRequest> listContractDetailDriverRequests;
}
