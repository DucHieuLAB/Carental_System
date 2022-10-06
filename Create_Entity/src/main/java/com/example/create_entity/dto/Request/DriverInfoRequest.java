package com.example.create_entity.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DriverInfoRequest {


    private String FullName;

    private String Identity_Number;

    private String driverNumberLicense;

    private String Phone;

    private int yearExperience;

    private int status;


}
