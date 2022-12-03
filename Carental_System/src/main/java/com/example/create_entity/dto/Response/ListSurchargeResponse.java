package com.example.create_entity.dto.Response;

import com.example.create_entity.Entity.SurchargeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListSurchargeResponse {
    private long id;
    private double amount;
    private String note;
    private Date createdDate;
    private String staffName;
    public static List<ListSurchargeResponse> createListSurchargeResponse(List<SurchargeEntity> surchargeEntityList){
        List<ListSurchargeResponse> listSurchargeResponses = new ArrayList<>();
        for (SurchargeEntity surchargeEntity : surchargeEntityList){
            ListSurchargeResponse listSurchargeResponse = new ListSurchargeResponse();
            listSurchargeResponse.setId(surchargeEntity.getId());
            listSurchargeResponse.setAmount(surchargeEntity.getAmount());
            listSurchargeResponse.setNote(surchargeEntity.getNote());
            listSurchargeResponse.setCreatedDate(surchargeEntity.getCreatedDate());
            if (!ObjectUtils.isEmpty(surchargeEntity.getStaffEntity())){
                listSurchargeResponse.setStaffName(surchargeEntity.getStaffEntity().getFullName());
            }
            listSurchargeResponses.add(listSurchargeResponse);
        }
        return listSurchargeResponses;
    }
}
