package com.example.create_entity.Entity;

import com.example.create_entity.dto.Response.ContractHadDriverReponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "contract_had_drivers")
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ContractHadDriverEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY )
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private ContractEntity ContractEntity;

    @Column(name = "pickup_district_id")
    private long pickup_district_id;

    @Column(name = "return_district_id")
    private long return_district_id;

    @Column(name = "pickup_address")
    private String pickup_address;

    @Column(name = "is_one_way")
    private boolean is_one_way;

    @Column(name = "return_address")
    private String return_address;

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    @Column(name = "last_modified_date", nullable = false)
    private Date lastModifiedDate;
    public static ContractHadDriverReponse convertToContractHadDriverResponse(ContractHadDriverEntity entity){
        if(ObjectUtils.isEmpty(entity)){
            return null;
        }
        ContractHadDriverReponse result = new ContractHadDriverReponse();
        result.setId(entity.getId());
        result.setPickupAddress(entity.getPickup_address());
        result.setReturnAddress(entity.getReturn_address());
        result.setOneWay(entity.is_one_way());
        return result;
    }
}
