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
    private String fullName;
    private String phoneCustomer;
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
    private Date lastModifiedDate;
    private Date createdDate;

}
