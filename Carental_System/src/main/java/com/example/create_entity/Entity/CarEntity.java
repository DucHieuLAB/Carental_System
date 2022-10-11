package com.example.create_entity.Entity;

import com.example.create_entity.dto.Request.CarRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cars")
public class CarEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY )
    @Column(name = "id")
    private long id;

    @Column(name = "model_name", columnDefinition ="VARCHAR(2048) NOT NULL, FULLTEXT KEY modelNameFulltext (modelName)")
    private String modelName;

    @ManyToOne
    @JoinColumn(name = "brand_id",nullable = false, foreignKey = @ForeignKey(name = "FK_Car_brand_id"))
    BrandEntity brand;

    @OneToMany(
            mappedBy = "car"
    )
    private List<CarImageEntity> carImageEntities = new ArrayList<>();

    @OneToMany(
            mappedBy = "car"
    )
    private List<BookingDetailEntity> bookingDetailEntities = new ArrayList<>();

    @Column(name = "year_of_manufacture ")
    private long yearOfManufacture;

    @Column(name = "rental_price", nullable = false)
    private double rentalPrice;

    @Column(name = "deposit_amount", nullable = false)
    private double depositAmount;

    @Column(name = "plate_number" ,  columnDefinition ="VARCHAR(2048) NOT NULL, FULLTEXT KEY plateNumberFulltext (plateNumber)")
    private String plateNumber;

    @Column(name = "capacity",nullable = false)
    private int capacity;

    @Column(name = "fuel")
    private long Fuel;

    @Column(name = "gears")
    private String gears;

    @Column(name = "status",nullable = false)
    private int status;

    @Column(name = "description ")
    private String description;

    @ManyToOne
    @JoinColumn(name = "parking_id",nullable = false, foreignKey = @ForeignKey(name = "FK_Car_parking_id"))
    ParkingEntity parking;

    public static CarEntity createCarEntity(CarRequest carRequest){
        CarEntity result = new CarEntity();
        result.setId(carRequest.getId());
        result.setModelName(carRequest.getModelName());
        result.setYearOfManufacture(carRequest.getYearOfManufacture());
        result.setRentalPrice(carRequest.getRentalPrice());
        result.setDepositAmount(carRequest.getDepositAmount());
        result.setPlateNumber(carRequest.getPlateNumber());
        result.setCapacity(carRequest.getCapacity());
        result.setFuel(carRequest.getFuel());
        result.setGears(carRequest.getGears());
        result.setDescription(carRequest.getDescription());
        result.setStatus(carRequest.getStatus());
        return result;
    }
}