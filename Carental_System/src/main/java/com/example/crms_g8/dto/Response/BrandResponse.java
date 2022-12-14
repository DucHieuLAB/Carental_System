package com.example.crms_g8.dto.Response;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BrandResponse {
    private long id;
    private String name;
    private String img;
    private String description;
}
