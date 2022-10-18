package com.example.create_entity.dto.Request;

import com.example.create_entity.Entity.ContractEntity;
import lombok.*;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {
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
    private long customerId;
    private int status;
    List<String> listCarPlateNumber;
    public static ContractEntity convertToBookingEntity(BookingRequest br){
        ContractEntity result = new ContractEntity();
        if (ObjectUtils.isEmpty(br)){
            return null;
        }
        result.setId(br.getId());
        result.setExpected_start_date(br.expectedStartDate);
        result.setExpected_end_date(br.getExpectedEndDate());
        result.setNote(br.getNote());
        result.setExpected_rental_price(br.getExpectedRentalRrice());
        result.setQuantity(br.getQuantity());
        result.setDeposit_amount(br.getDepositAmount());
        result.setHad_driver(br.isHad_driver());
        result.setStatus(br.getStatus());
        return result;
    }
}
