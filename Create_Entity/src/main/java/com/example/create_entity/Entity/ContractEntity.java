package com.example.create_entity.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "contracts")
@Getter
@Setter
@NoArgsConstructor
public class ContractEntity {
    @Id
    @Column(name = "contract_id")
    private long id;



    @Column(name = "status")
    private int status;

    @Column(name = "real_price")
    private double real_price;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private BookingEntity bookingEntity;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private AccountEntity accountEntity;
}
