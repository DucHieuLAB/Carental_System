package com.example.crms_g8.Entity;

import com.example.crms_g8.dto.Request.ParkingRequest;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "district_id",nullable = false)
    private DistrictsEntity districtsEntity;

    @Column(name = "phone")
    private String phone;

    @Column(name = "status")
    private int status;
    @Column(name = "location_url",length = 4000)
    private String location;
    @OneToMany(
            mappedBy = "parking"
    )
    private List<CarEntity> carEntities = new ArrayList<>();

    @OneToMany(
            mappedBy = "pickup_parking"
    )
    private List<ContractEntity> bookingEntitiesPickup = new ArrayList<>();

    @OneToMany(
            mappedBy = "return_parking"
    )
    private List<ContractEntity> bookingEntitiesReturn = new ArrayList<>();
    
    public static ParkingEntity createParking(ParkingRequest parkingRequest) {
        ParkingEntity result = new ParkingEntity();
        result.setId(parkingRequest.getId());
        result.setName(parkingRequest.getName());
        result.setAddress(parkingRequest.getAddress());
        result.setPhone(parkingRequest.getPhone());
        result.setLocation(parkingRequest.getLocation());
        result.setStatus(1);
        return result;
    }
}
