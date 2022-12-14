package com.example.crsm_g8.Entity;

import com.example.crsm_g8.dto.Request.DistricRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Districts")
public class DistrictsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "district_id")
    private Long id;

    @Column(name = "district_name")
    private String District_Name;

    @Column(name = "wards")
    private String wards;

    @Column(name = "city")
    private String City;

    @OneToMany(
            mappedBy = "districtsEntity"
    )
    List<ParkingEntity> parkingEntities;

    public static DistrictsEntity createDistricEntity(DistricRequest districRequest) {
        if (districRequest.getCity() == null|| districRequest.getCity().length() <= 0 ||
                districRequest.getWards() == null || districRequest.getWards().length() <= 0 ||
                districRequest.getDistrictName() == null || districRequest.getDistrictName().length() <= 0 ){
            return null;
        }
        DistrictsEntity result = new DistrictsEntity();
        result.setCity(districRequest.getCity());
        result.setWards(districRequest.getWards());
        result.setDistrict_Name(districRequest.getDistrictName());
        return result;
    }
}
