package com.example.crms_g8.dto.Response;

import com.example.crms_g8.Entity.CarImageEntity;
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
public class ListCarImageResponse {
    private long id;
    private String plateNumber;
    private String imgUrl;

    public static List<ListCarImageResponse> createListCarImagePesponse(List<CarImageEntity> carImageEntities){
        List<ListCarImageResponse> result = new ArrayList<>();
        if(carImageEntities.isEmpty()){
            return null;
        }
        for (CarImageEntity carimage : carImageEntities){
            ListCarImageResponse tmp = new ListCarImageResponse();
            tmp.setId(carimage.getId());
            tmp.setPlateNumber(carimage.getCar().getPlateNumber());
            tmp.setImgUrl(carimage.getImg());
            if (carimage.isStatus()){
                result.add(tmp);
            }
        }
        return result;
    }
}
