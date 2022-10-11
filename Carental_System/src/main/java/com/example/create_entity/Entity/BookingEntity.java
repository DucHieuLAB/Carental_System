package com.example.create_entity.Entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "bookings")
@Getter
@Setter
@NoArgsConstructor
public class BookingEntity {
    @Id
    @Column(name = "booking_id",unique = true)
    private long id;

    @Column
    private long pickup_parking_id;

    @Column
    private long return_parking_id;

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
}
