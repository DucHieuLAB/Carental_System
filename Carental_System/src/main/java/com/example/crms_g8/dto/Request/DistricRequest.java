package com.example.crms_g8.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DistricRequest {
    private Long id;
    private String districtName;
    private String wards;
    private String city;
}
