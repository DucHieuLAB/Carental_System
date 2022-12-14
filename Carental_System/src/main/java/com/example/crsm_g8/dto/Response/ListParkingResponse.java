package com.example.crsm_g8.dto.Response;

import com.example.crsm_g8.Entity.ParkingEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListParkingResponse {
    private Long id;
    private String name;
    private String address;
    private DistrictReponse district;
    private String phone;
    private String location;
    private int status;

    public static List<ListParkingResponse> CreateListParkinkResponse(List<ParkingEntity> parkingEntityList){
        if(parkingEntityList.isEmpty()){
            return null;
        }
        List<ListParkingResponse> result= new ArrayList<>();
        for (ParkingEntity parking : parkingEntityList) {
            ListParkingResponse tmp = new ListParkingResponse();
            tmp.setId(parking.getId());
            tmp.setName(parking.getName());
            tmp.setAddress(parking.getAddress());
            tmp.setDistrict(DistrictReponse.createDistricReponse(parking.getDistrictsEntity()));
            tmp.setPhone(parking.getPhone());
            tmp.setStatus(parking.getStatus());
            tmp.setLocation(parking.getLocation());
            result.add(tmp);
        }
        return result;
    }
}
