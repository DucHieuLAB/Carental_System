package com.example.create_entity.dto.Request;

import com.example.create_entity.Entity.BookingEntity;
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
    private long CustomerId;
    private int status;
    List<ListBookingDetailRequest> lstListBookingDetailRequests;
    public static BookingEntity convertToBookingEntity(BookingRequest br){
        BookingEntity result = new BookingEntity();
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
        return result;
    }
}
