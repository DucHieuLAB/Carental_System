package com.example.create_entity.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
public class PaymentEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "contract_id", nullable = false, foreignKey = @ForeignKey(name = "FK_payment_contract"))
    private ContractEntity contract;

    @ManyToOne
    @JoinColumn(name = "ID_Staff", nullable = false, foreignKey = @ForeignKey(name = "FK_Staff"))
    private StaffEntity staffEntity;


    @Column
    private double paid;

    @Column
    private double receivables;

    @Column
    private double totalAmount;

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    @Column(name = "last_modified_date", nullable = false)
    private Date lastModifiedDate;

    @Column
    private String description;
}
