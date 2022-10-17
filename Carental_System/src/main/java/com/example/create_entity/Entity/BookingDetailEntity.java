package com.example.create_entity.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "booking_details")
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BookingDetailEntity {
    @Id
    @Column(name = "booking_detail_id",unique = true)
    private long id;
    @Column
    private Date real_pick_up_date;
    @Column
    private Date real_return_date;


    @Column
    private long driver_id;

    @ManyToOne
    @JoinColumn(name = "car_id",nullable = false, foreignKey = @ForeignKey(name = "FK_BookingDetail_Car"))
    CarEntity car;

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false,foreignKey = @ForeignKey(name = "FK_BookingDetail_Booking"))
    BookingEntity booking;

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    @Column(name = "last_modified_date",nullable = false)
    private Date lastModifiedDate;

}
