package com.example.create_entity.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "LicenseTypes")
public class LicenseTypeEntity {

    @Id
    @Column(name = "license_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;


    @Column(name="license_name", nullable = false)
    private  String Name_License;

    @Column(name = "license_description")
    private String Description;
}
