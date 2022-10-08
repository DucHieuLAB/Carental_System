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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id",nullable = false,unique = true)
    private BookingEntity bookingEntity;



    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private CarEntity car;





}
