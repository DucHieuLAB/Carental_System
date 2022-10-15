package com.example.create_entity.dto.Request;

import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDetailRequest {
    private long id;
    private String plateNumber;
    private Date realPickUpDate;
    private Date realReturnDate;
    private long driverId;
    private long carId;
    private long bookingId;
}
