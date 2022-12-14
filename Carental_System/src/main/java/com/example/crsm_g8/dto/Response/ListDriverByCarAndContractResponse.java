package com.example.crsm_g8.dto.Response;

import com.example.crsm_g8.Entity.DriverEntity;
import com.example.crsm_g8.untils.Validate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListDriverByCarAndContractResponse {
    private Long id;
    private int yearExperience;
    private String fullName;
    private int age;
    private String licenseName;
    public static List<ListDriverByCarAndContractResponse> createResponse(List<DriverEntity> driverEntityList) {
        List<ListDriverByCarAndContractResponse> result = new ArrayList<>();

        for (DriverEntity entity:driverEntityList
             ) {
            ListDriverByCarAndContractResponse tmp = new ListDriverByCarAndContractResponse();
            tmp.setId(entity.getId());
            tmp.setYearExperience(entity.getYear_Experience());
                    tmp.setFullName(entity.getFullName());
                    tmp.setAge(Validate.calculateAge(Validate.convertToLocalDateViaMilisecond(entity.getDOB()), LocalDate.now()));
                tmp.setLicenseName(entity.getLicenseTypeEntity().getName_License());
            result.add(tmp);
        }
        return result;
    }
}
