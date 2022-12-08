package com.example.create_entity.dto.Response;

import com.example.create_entity.Entity.ContractHadDriverEntity;
import com.example.create_entity.Entity.DistrictsEntity;
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


    public  ContractHadDriverReponse  hadDriverReponse(ContractHadDriverEntity hadDriver, DistrictsEntity pickupDistrictAddress,DistrictsEntity ReturnDistrictAddress) {
        ContractHadDriverReponse result = new ContractHadDriverReponse();
        DistrictReponse districtReponse = new DistrictReponse();
        result.setId(hadDriver.getId());
        result.setPickupAddress(hadDriver.getPickup_address());
        result.setPickupDistricAddress(districtReponse.createDistricReponse(pickupDistrictAddress));
        result.setReturnAddress(hadDriver.getReturn_address());
        result.setReturnDictricAddress(districtReponse.createDistricReponse(ReturnDistrictAddress));
        result.setOneWay(hadDriver.is_one_way());

        return result;
    }

}
