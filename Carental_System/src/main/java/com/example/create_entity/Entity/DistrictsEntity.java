package com.example.create_entity.Entity;


import com.example.create_entity.dto.Request.DistricRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Districts")
public class DistrictsEntity    {

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


    public static DistrictsEntity createDistricEntity(DistricRequest districRequest) {
        if (districRequest.getCity().equals("") || districRequest.getCity().length() <= 0 ||
                districRequest.getWards().equals("") || districRequest.getWards().length() <= 0 ||
                districRequest.getDistrict_Name().equals("") || districRequest.getDistrict_Name().length() <= 0 ){
            return null;
        }
        DistrictsEntity result = new DistrictsEntity();
        result.setCity(districRequest.getCity());
        result.setWards(districRequest.getWards());
        result.setDistrict_Name(districRequest.getDistrict_Name());
        return result;
    }
}
