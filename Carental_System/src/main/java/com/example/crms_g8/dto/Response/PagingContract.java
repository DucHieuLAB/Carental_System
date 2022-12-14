package com.example.crms_g8.dto.Response;


import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagingContract {
 private    List<ContractResponse> contractResponseList;
 private int NumberPage;
 private int TotalPage;

}
