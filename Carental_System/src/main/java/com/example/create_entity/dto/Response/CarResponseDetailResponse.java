package com.example.create_entity.dto.Response;

import com.example.create_entity.Entity.BrandEntity;
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
    private long Fuel;
    private String gears;
    private int status;
    private String description;
    private long parkingId;
    private String parkingName;
    private String parkingAddress;
    private List<ListCarImageResponse> imgs;
}
