package com.example.crsm_g8.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParkingRequest {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private int status;
    private String location;
    private DistricRequest district;

}
