package com.example.create_entity.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagingResponse {

    private List<?> driverInfoResponsesList;
    private int TotalPage;
    private int numberPage;
}

