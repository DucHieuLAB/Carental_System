package com.example.create_entity.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
public class BookingEntity {
    @Id
    @Column(name = "booking_id", unique = true)
    private long id;

    @ManyToOne
    @JoinColumn(name = "pickup_parking_id", nullable = false,foreignKey = @ForeignKey(name = "FK_bookings_pickup_parkings"))
    private ParkingEntity pickup_parking;

    @ManyToOne
    @JoinColumn(name = "return_parking_id", nullable = false,foreignKey = @ForeignKey(name = "FK_bookings_return_parkings"))
    private ParkingEntity return_parking;

    @Column
    private Date expected_start_date;

    @Column
    private Date expected_end_date;

    @Column
    private String note;

    @Column
    private double expected_rental_price;

    @Column
    private int quantity;

    @Column
    private double deposit_amount;

    @Column
    private boolean had_driver;

    @Column
    private long customer_id;

    @Column
    private int status;

    @OneToMany(
            mappedBy = "booking"
    )
    List<BookingDetailEntity> bookingDetailEntityList;
}
