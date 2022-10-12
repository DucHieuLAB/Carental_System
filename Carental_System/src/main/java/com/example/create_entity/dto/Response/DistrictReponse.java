package com.example.create_entity.dto.Response;

import com.example.create_entity.Entity.DistrictsEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DistrictReponse {
    private Long id;
    private String districtName;
    private String wards;
    private String city;

    public static DistrictReponse createDistricReponse(DistrictsEntity districtsEntity){
        DistrictReponse result = new DistrictReponse();
        result.setId(districtsEntity.getId());
        result.setDistrictName(districtsEntity.getDistrict_Name());
        result.setWards(districtsEntity.getWards());
        result.setCity(districtsEntity.getCity());
        return result;
    }
}
