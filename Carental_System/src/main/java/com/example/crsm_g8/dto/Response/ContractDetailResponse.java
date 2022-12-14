package com.example.crsm_g8.dto.Response;

import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContractDetailResponse {
    private long BookingDetailID;
    private Long DriverID;
    private Long CarID;
    private Date Real_Pick_Up_Date;
    private Date Real_Return_Date;
    private Date Last_Modified_Date;
    private Long BookingID;

}
