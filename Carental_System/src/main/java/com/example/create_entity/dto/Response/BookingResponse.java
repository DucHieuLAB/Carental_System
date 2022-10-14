package com.example.create_entity.dto.Response;

import lombok.*;

import java.util.Date;
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {
    private long id;
    private long pickupParkingId;
    private long returnParkingId;
    private Date expectedStartDate;
    private Date expectedEndDate;
    private String note;
    private double expectedRentalRrice;
    private int quantity;
    private double depositAmount;
    private boolean had_driver;
    private long CustomerId;
    private int status;
}
