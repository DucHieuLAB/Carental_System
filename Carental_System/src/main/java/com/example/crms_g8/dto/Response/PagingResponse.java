package com.example.crms_g8.dto.Response;

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

    private List<?> objects;
    private int TotalPage;
    private int numberPage;
}

