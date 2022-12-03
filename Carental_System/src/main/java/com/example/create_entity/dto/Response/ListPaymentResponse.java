package com.example.create_entity.dto.Response;

import com.example.create_entity.Entity.PaymentEntity;
import com.example.create_entity.Entity.StaffEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListPaymentResponse {
    private long id;

    private long contractId;

    private double paid;

    private double receivables;

    private double totalAmount;

    private Date lastModifiedDate;

    private String description;

    private String staffName;

    private boolean hadDriver;

    public static List<ListPaymentResponse> createListPaymentResponse (List<PaymentEntity> paymentEntities){
        List<ListPaymentResponse> result = new ArrayList<>();
        if (paymentEntities.size() <= 0){
            return null;
        }
        for (PaymentEntity entity : paymentEntities){
            ListPaymentResponse listPaymentResponse = new ListPaymentResponse();
            listPaymentResponse.setContractId(entity.getContract().getId());
            listPaymentResponse.setId(entity.getId());
            listPaymentResponse.setPaid(entity.getPaid());
            listPaymentResponse.setReceivables(entity.getReceivables());
            listPaymentResponse.setTotalAmount(entity.getTotalAmount());
            listPaymentResponse.setLastModifiedDate(entity.getLastModifiedDate());
            listPaymentResponse.setDescription(entity.getDescription());
            if (!ObjectUtils.isEmpty(entity.getStaffEntity())){
                listPaymentResponse.setStaffName(entity.getStaffEntity().getFullName());
            }
            listPaymentResponse.setHadDriver(entity.getContract().isHad_driver());
            result.add(listPaymentResponse);
        }
        return result;
    }
}
