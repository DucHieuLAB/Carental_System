package com.example.create_entity.dto.Response;


import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagingBooking {
 private    List<BookingResponse> bookingResponseList ;
 private int NumberPage;
 private int TotalPage;

}
