package com.example.create_entity.Entity;

import com.example.create_entity.dto.Request.CarRequest;
import com.example.create_entity.dto.Response.CarResponseDetailResponse;
import com.example.create_entity.dto.Response.ListCarImageResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

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

    @Column(name = "model_name", nullable = false)
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
    private List<ContractDetailEntity> bookingDetailEntities = new ArrayList<>();

    @Column(name = "year_of_manufacture ")
    private long yearOfManufacture;

    @Column(name = "rental_price", nullable = false)
    private double rentalPrice;

    @Column(name = "deposit_amount", nullable = false)
    private double depositAmount;

    @Column(name = "plate_number" ,  nullable = false)
    private String plateNumber;

    @Column(name = "capacity",nullable = false)
    private int capacity;

    @Column(name = "fuel")
    private String Fuel;

    @Column(name = "gears")
    private String gears;

    @Column(name = "color")
    private String color;

    @Column(name = "status",nullable = false)
    private int status;

    @Column(name = "description ")
    private String description;

    @ManyToOne
    @JoinColumn(name = "license_id",nullable = false,foreignKey= @ForeignKey(name = "FK_Car_license_id") )
     LicenseTypeEntity licenseTypeEntity;


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
        result.setColor(carRequest.getColor());
        result.setDescription(carRequest.getDescription());
        result.setStatus(carRequest.getStatus());
        return result;
    }

    public static CarResponseDetailResponse createCarResponseDetailResponse(CarEntity car){
        if(ObjectUtils.isEmpty(car)){
            return null;
        }
        CarResponseDetailResponse crdp = new CarResponseDetailResponse();
        crdp.setId(car.getId());
        crdp.setModelName(car.getModelName());
        crdp.setBrandName(car.getBrand().getName());
        crdp.setBrandImg(car.getBrand().getImg());
        crdp.setBrandId(car.getBrand().getId());
        crdp.setYearOfManufacture(car.getYearOfManufacture());
        crdp.setRentalPrice(car.getRentalPrice());
        crdp.setDepositAmount(car.getDepositAmount());
        crdp.setPlateNumber(car.getPlateNumber());
        crdp.setCapacity(car.getCapacity());
        crdp.setColor(car.getColor());
        crdp.setFuel(car.getFuel());
        crdp.setGears(car.getGears());
        crdp.setStatus(car.getStatus());
        crdp.setLicenseId(car.getLicenseTypeEntity().getID());
        crdp.setDescription(car.getDescription());
        crdp.setParkingId(car.getParking().getId());
        List<ListCarImageResponse> carImageResponses = ListCarImageResponse.createListCarImagePesponse(car.carImageEntities);
        crdp.setImgs(carImageResponses);
        crdp.setParkingName(car.getParking().getName());
        crdp.setParkingAddress(car.getParking().getAddress());
        crdp.setLicenseName(car.getLicenseTypeEntity().getName_License());
        return crdp;
    }
}