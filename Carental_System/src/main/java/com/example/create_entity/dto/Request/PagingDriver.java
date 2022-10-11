package com.example.create_entity.dto.Request;

import com.example.create_entity.Entity.DriverEntity;
import com.example.create_entity.dto.Response.DriverInfoResponse;
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
