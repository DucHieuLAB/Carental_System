package com.example.crms_g8.dto.Response;

import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContractResponse {
    private long BookingId;
    private String FullName;
    private String PhoneCustomer;
    private long pickupParkingId;
    private long returnParkingId;
    private Date expectedStartDate;
    private Date expectedEndDate;
    private String note;
    private double expectedRentalPrice;
    private int quantity;
    private double depositAmount;
    private boolean had_driver;
    private long CustomerId;
    private int status;
    private Date lastModifiedDate;
    private Date CreateDate;
    private double real_price;
    private String customerEmail;
}


