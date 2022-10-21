package com.example.create_entity.dto.Response;

import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContractHadDriverReponse {
    private Long id;
    private String pickupAddress;
    private DistrictReponse pickupDistricAddress;
    private String returnAddress;
    private DistrictReponse returnDictricAddress;
    private boolean isOneWay;

}
