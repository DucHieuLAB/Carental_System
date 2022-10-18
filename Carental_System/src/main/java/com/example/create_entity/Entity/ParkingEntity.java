package com.example.create_entity.Entity;

import com.example.create_entity.dto.Request.ParkingRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "parkings")
public class ParkingEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id",nullable = false)
    private DistrictsEntity districtsEntity;

    @Column(name = "phone")
    private String phone;

    @Column(name = "status")
    private int status;

    @OneToMany(
            mappedBy = "parking"
    )
    private List<CarEntity> carEntities = new ArrayList<>();

    @OneToMany(
            mappedBy = "pickup_parking"
    )
    private List<BookingEntity> bookingEntitiesPickup = new ArrayList<>();

    @OneToMany(
            mappedBy = "return_parking"
    )
    private List<BookingEntity> bookingEntitiesReturn = new ArrayList<>();
    
    public static ParkingEntity createParking(ParkingRequest parkingRequest) {
        ParkingEntity result = new ParkingEntity();
        result.setId(parkingRequest.getId());
        result.setName(parkingRequest.getName());
        result.setAddress(parkingRequest.getAddress());
        result.setPhone(parkingRequest.getPhone());
        result.setStatus(1);
        return result;
    }
}
