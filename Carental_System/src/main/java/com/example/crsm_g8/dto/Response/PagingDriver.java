package com.example.crsm_g8.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagingDriver {
    private List<DriverInfoResponse> driverInfoResponsesList;
    private int TotalPage;
    private int numberPage;
}
