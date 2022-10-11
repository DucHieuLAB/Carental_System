package com.example.create_entity.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "booking_details")
@Getter
@Setter
@NoArgsConstructor
public class BookingDetailEntity {
    @Id
    @Column(name = "booking_detail_id",unique = true)
    private long id;

    @Column(name = "plate_number")
    private String plateNumber;

    @Column
    private Date real_pick_up_date;
    @Column
    private Date real_return_date;
    @Column
    private long driver_id;

    @ManyToOne
    @JoinColumn(name = "car_id",nullable = false, foreignKey = @ForeignKey(name = "FK_BookingDetail_Car"))
    CarEntity car;
}
