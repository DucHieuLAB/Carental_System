package com.example.create_entity.dto.Response;

import com.example.create_entity.Entity.DriverEntity;
import com.example.create_entity.untils.Validate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
                    tmp.setFullName(entity.getAccountEntity().getFullName());
                    tmp.setAge(Validate.calculateAge(entity.getAccountEntity().getDOB().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now()));
                tmp.setLicenseName(entity.getLicenseTypeEntity().getName_License());
            result.add(tmp);
        }
        return result;
    }
}
