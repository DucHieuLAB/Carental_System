package com.example.crms_g8.dto.Response;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarResponseDetailResponse {
    private long id;
    private String modelName;
    private long brandId;
    private String brandName;
    private String brandImg;
    private long yearOfManufacture;
    private double rentalPrice;
    private double depositAmount;
    private String plateNumber;
    private int capacity;
    private String color;
    private String Fuel;
    private String gears;
    private int status;
    private String description;
    private long parkingId;
    private long licenseId;
    private String parkingName;
    private String parkingAddress;
    private String licenseName;
    private List<ListCarImageResponse> imgs;
    private String speedometer;
}
