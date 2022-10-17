package com.example.create_entity.dto.Request;

import com.example.create_entity.Entity.BookingDetailEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListBookingDetailRequest {
    private long id;
    private String plateNumber;
    private Date realPickUpDate;
    private Date realReturnDate;
    private long driverId;
    private long carId;
    private long bookingId;
    public static List<BookingDetailEntity>  convertToBookingDetailEntity(List<ListBookingDetailRequest> bookingDetailRequests){
        List<BookingDetailEntity> result = new ArrayList<>();
        if (bookingDetailRequests.isEmpty()){
            return null;
        }
        for (ListBookingDetailRequest b : bookingDetailRequests){
            BookingDetailEntity nb = new BookingDetailEntity();
            nb.setId(b.getId());
         //   nb.setPlateNumber(b.getPlateNumber());
            nb.setReal_pick_up_date(b.realPickUpDate);
            nb.setReal_return_date(b.realReturnDate);
            result.add(nb);
        }
        return result;

    }
}
