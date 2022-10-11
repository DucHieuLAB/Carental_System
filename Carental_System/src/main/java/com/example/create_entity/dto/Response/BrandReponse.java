package com.example.create_entity.dto.Response;

import com.example.create_entity.Entity.BrandEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrandReponse {
    private long id;
    private String name;
    private String img;
    private String description;

    public static List<BrandReponse> createResponseData(List<BrandEntity> brandEntityList){
        List<BrandReponse> result = new ArrayList<>();
        if(ObjectUtils.isEmpty(brandEntityList)){
            return null;
        }

        for (BrandEntity brandEntity : brandEntityList) {
            result.add(new BrandReponse(brandEntity.getId(), brandEntity.getName(), brandEntity.getImg(), brandEntity.getDescription()));
        }
        return result;
    }
}
