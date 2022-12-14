package com.example.crms_g8.dto.Response;

import com.example.crms_g8.Entity.ContractHadDriverEntity;
import com.example.crms_g8.Entity.DistrictsEntity;
import lombok.*;

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
