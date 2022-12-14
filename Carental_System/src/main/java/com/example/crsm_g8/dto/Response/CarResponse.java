package com.example.crsm_g8.dto.Response;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarResponse {
    private String plateNumber;
    private String modelName;
    private String brandName;
    private String parkingName;
    private int status;
    private int capacity;

    private List<ListCarImageResponse> listImg;





}
