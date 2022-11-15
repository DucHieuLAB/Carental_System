package com.example.create_entity.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "contract_details")
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ContractDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_detail_id",unique = true)
    private long id;

    @Column
    private Date real_pick_up_date;
    @Column
    private Date real_return_date;


    @ManyToOne
    @JoinColumn(name = "ID_Driver",foreignKey = @ForeignKey(name = "FK_Driver"))
    private DriverEntity driverEntity;

    @ManyToOne
    @JoinColumn(name = "car_id",nullable = false, foreignKey = @ForeignKey(name = "FK_BookingDetail_Car"))
    CarEntity car;

    @ManyToOne
    @JoinColumn(name = "contract_id", nullable = false,foreignKey = @ForeignKey(name = "FK_BookingDetail_Booking"))
    ContractEntity booking;

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    @Column(name = "last_modified_date",nullable = false)
    private Date lastModifiedDate;

}
