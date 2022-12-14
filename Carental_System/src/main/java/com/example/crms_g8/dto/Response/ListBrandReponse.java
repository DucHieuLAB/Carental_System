package com.example.crms_g8.dto.Response;

import com.example.crms_g8.Entity.BrandEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListBrandReponse {
    private long id;
    private String name;
    private String img;
    private String description;

    public static List<ListBrandReponse> createResponseData(List<BrandEntity> brandEntityList){
        List<ListBrandReponse> result = new ArrayList<>();
        if(ObjectUtils.isEmpty(brandEntityList)){
            return null;
        }

        for (BrandEntity brandEntity : brandEntityList) {
            result.add(new ListBrandReponse(brandEntity.getId(), brandEntity.getName(), brandEntity.getImg(), brandEntity.getDescription()));
        }
        return result;
    }
}
