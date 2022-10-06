package com.example.create_entity.Entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cars")
public class CarEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "car_id")
    private long carId;

    @Column(name = "car_model_name",nullable = false)
    private String carModelName;

    @ManyToOne
    @JoinColumn(name = "brand_id",nullable = false, foreignKey = @ForeignKey(name = "FK_Car_brand_id"))
    BrandEntity brand;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_id",nullable = false)
    private ParkingEntity parkingEntity;

    @OneToMany(
            mappedBy = "car"
    )
    private List<CarImageEntity> carImageEntities = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_detail_id",nullable = false)
    private BookingDetailEntity bookingDetailEntity  ;

    @Column(name = "year_of_manufacture ")
    private long yearOfManufacture;

    @Column(name = "rental_price ", nullable = false)
    private double rentalPrice;

    @Column(name = "deposit_amount ", nullable = false)
    private double depositAmount;

    @Column(name = "plate_number " ,  nullable = false)
    private String plateNumber;

    @Column(name = "capacity",nullable = false)
    private int capacity;

    @Column(name = "fuel")
    private long Fuel;

    @Column(name = "gears")
    private String gears;

    @Column(name = "status ")
    private int status;

    @Column(name = "description ")
    private String description;

    @Column(name = "img ")
    private String img;


}