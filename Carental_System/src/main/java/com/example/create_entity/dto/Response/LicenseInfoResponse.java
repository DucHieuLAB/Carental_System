package com.example.create_entity.dto.Response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LicenseInfoResponse {
   private Long ID;
   private String description;
   private String name_license;
}
