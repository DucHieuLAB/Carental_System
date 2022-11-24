package com.example.create_entity.dto.Request;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepositRequest {
    private long contractId;
    private long accountId;
    private String description;
    private double paid;
}
