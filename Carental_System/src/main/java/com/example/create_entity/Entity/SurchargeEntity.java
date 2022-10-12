package com.example.create_entity.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "surcharges")
@Getter
@Setter
@NoArgsConstructor
public class SurchargeEntity {
    @Id
    @Column(name = "surcharge_id")
    private long contract_id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id", nullable = false)
    private ContractEntity contractEntity;

    @Column(name = "amount")
    private double amount;

    @Column(name = "note")
    private String note;

}
