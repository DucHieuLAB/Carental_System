package com.example.crsm_g8.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarRequest {
    private long id;
    private String modelName;
    private long brandId;
    private long yearOfManufacture;
    private double rentalPrice;
    private double depositAmount;
    private String plateNumber;
    private int capacity;
    private String Fuel;
    private String gears;
    private String color;
    private int status;
    private String description;
    private long parkingId;
    private long licenseId;
    private List<String> imgs;
}
