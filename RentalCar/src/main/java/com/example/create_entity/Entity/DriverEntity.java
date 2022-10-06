package com.example.create_entity.Entity;



import org.apache.commons.logging.Log;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "Diver")
public class DriverEntity {


    @Id
    @Column(name = "id_diver",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "year_experience")
    private int Year_Experience;

    @Column(name = "diver_number_license")
    private String Diver_Number_License;

    @Column(name = "status", nullable = false)
    private boolean Status;

    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date Start_Date;



    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id",nullable = false)
    private AccountEntity accountEntity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "license_id",nullable = false)
    private LicenseTypeEntity licenseTypeEntity;
}
