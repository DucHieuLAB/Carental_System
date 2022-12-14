package com.example.crms_g8.dto.Response;

import com.example.crms_g8.Entity.ParkingEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParkingResponse {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private int status;
    private String location;
    private DistrictReponse district;

    public static ParkingResponse createParkingResponse(ParkingEntity parkingEntity){
        ParkingResponse result = new ParkingResponse();
        if(ObjectUtils.isEmpty(parkingEntity)){
            return null;
        }
        result.setId(parkingEntity.getId());
        result.setName(parkingEntity.getName());
        result.setAddress(parkingEntity.getAddress());
        result.setPhone(parkingEntity.getPhone());
        result.setStatus(parkingEntity.getStatus());
        result.setDistrict(DistrictReponse.createDistricReponse(parkingEntity.getDistrictsEntity()));
        result.setLocation(parkingEntity.getLocation());
        return result;
    }
}
