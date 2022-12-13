package com.example.create_entity.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "surcharges")
@Getter
@Setter
@NoArgsConstructor
public class SurchargeEntity {
    @Id
    @Column(name = "surcharge_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contract_id", nullable = false)
    private ContractEntity contractEntity;

    @Column(name = "amount")
    private double amount;

    @Column(name = "note")
    private String note;

    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = true, foreignKey = @ForeignKey(name = "FK_Surcharge_Staff"))
    private StaffEntity staffEntity;

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = true, foreignKey = @ForeignKey(name = "FK_Surcharge_Driver"))
    private DriverEntity driverEntity;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @Column(name = "createdDate", nullable = false)
    private Date createdDate;

}
