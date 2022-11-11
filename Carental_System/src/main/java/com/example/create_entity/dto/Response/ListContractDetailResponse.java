package com.example.create_entity.dto.Response;

import com.example.create_entity.Entity.ContractDetailEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ListContractDetailResponse {
    private long id;
    private Date realPickUpDate;
    private Date realReturnDate;
    private long driverId;
    private String driverName;
    private long carId;
    private long bookingId;
    private String plateNumber;
    private int capacity;
    private String modelName;
    private String Color;
    private double rentalPrice;

    public static List<ListContractDetailResponse> createListBookingDetailResponse(List<ContractDetailEntity> bdes) {
        List<ListContractDetailResponse> result = new ArrayList<>();
        if (bdes.isEmpty()) {
            return null;
        }
        for (ContractDetailEntity entity : bdes) {
            ListContractDetailResponse tmp = new ListContractDetailResponse();
            tmp.setId(entity.getId());
            tmp.setRealReturnDate(entity.getReal_return_date());
            tmp.setRealPickUpDate(entity.getReal_pick_up_date());
            tmp.setDriverId(entity.getDriverEntity().getId());
            tmp.setPlateNumber(entity.getCar().getPlateNumber());
            tmp.setBookingId(entity.getBooking().getId());
            tmp.setModelName(entity.getCar().getModelName());
            tmp.setCapacity(entity.getCar().getCapacity());
            tmp.setColor(entity.getCar().getColor());
            tmp.setRentalPrice(entity.getCar().getRentalPrice());
            tmp.setCarId(entity.getCar().getId());
            result.add(tmp);
        }
        return result;
    }
}
