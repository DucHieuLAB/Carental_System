package com.example.create_entity.Entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Districts")
public class DistrictsEntity    {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "district_id")
    private Long id;

    @Column(name = "district_name")
    private String District_Name;

    @Column(name = "wards")
    private String wards;

    @Column(name = "city")
    private String City;


}
