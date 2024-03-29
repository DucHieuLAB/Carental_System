package com.example.crms_g8.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarImgRequest {
    private long id;
    private String plateNumber;
    private String imgUrl;
    private Long carId;
}
