package com.example.crms_g8.dto.Request;

import com.example.crms_g8.Entity.ContractDetailEntity;
import com.example.crms_g8.dto.Response.ListCarImageResponse;
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
    private Date realPickUpDate;
    private Date realReturnDate;
    private long driverId;
    private long carId;
    private long bookingId;
    List<ListCarImageResponse> carImgs;
    public static List<ContractDetailEntity>  convertToBookingDetailEntity(List<ListBookingDetailRequest> bookingDetailRequests){
        List<ContractDetailEntity> result = new ArrayList<>();
        if (bookingDetailRequests.isEmpty()){
            return null;
        }
        for (ListBookingDetailRequest b : bookingDetailRequests){
            ContractDetailEntity nb = new ContractDetailEntity();
            nb.setId(b.getId());
            nb.setReal_pick_up_date(b.realPickUpDate);
            nb.setReal_return_date(b.realReturnDate);
            result.add(nb);
        }
        return result;

    }
}
