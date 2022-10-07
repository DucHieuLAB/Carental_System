package com.example.create_entity.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "parkings")
public class ParkingEntity {

    @Id
    @Column(name = "parking_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address")
    private String address;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id",nullable = false)
    private DistrictsEntity districtsEntity;

    @Column(name = "phone")
    private String phone;

    @Column(name = "status")
    private int status;
}
