package com.example.create_entity.Entity;

import javax.persistence.*;

@Entity
@Table(name = "District")
public class DistrictsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "district_id")
    private Long id;

    @Column(name = "district_name", nullable = false)
    private String District_Name;

    @Column(name = "wards")
    private String wards;

    @Column(name = "city")
    private String City;


}
