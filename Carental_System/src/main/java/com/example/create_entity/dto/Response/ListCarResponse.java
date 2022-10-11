package com.example.create_entity.dto.Response;

import com.example.create_entity.Entity.CarEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ListCarResponse {
    private String plateNumber;
    private String modelName;
    private String brandName;
    private String parkingName;
    private int status;
    private int capacity;

    public static List<ListCarResponse> createListCarPesponse(List<CarEntity> carEntities){
        List<ListCarResponse> result = new ArrayList<>();
        for (CarEntity carEntity : carEntities){
            ListCarResponse listCarResponse = new ListCarResponse();
            listCarResponse.setPlateNumber(carEntity.getPlateNumber());
            listCarResponse.setModelName(carEntity.getModelName());
            listCarResponse.setBrandName(carEntity.getBrand().getName());
            listCarResponse.setParkingName(carEntity.getParking().getName());
            listCarResponse.setStatus(carEntity.getStatus());
            listCarResponse.setCapacity(carEntity.getCapacity());
            result.add(listCarResponse);
        }
        return result;
    }
}
