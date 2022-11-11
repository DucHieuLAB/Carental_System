package com.example.create_entity.Entity;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.apache.commons.logging.Log;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Transactional
@Table(name = "Driver")
public class DriverEntity implements Serializable {


    @Id
    @Column(name = "ID",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", nullable = false,unique = true)
    private AccountEntity accountEntity;


    @Column(name = "full_name")
    private String FullName;

    @Column(name = "dob")
    @Temporal(TemporalType.DATE)
    private Date DOB;

    @Column(name = "gender")
    private int Gender;

    @Column(name = "phone")
    private String Phone;

    @Column(name = "modified_date")
    @Temporal(TemporalType.DATE)
    private Date ModifiedDate;

    @Column(name = "status")
    private int status;

    @Column(name = "img")
    private String img;

    @Column(name = "identity_number")
    private String Identity_Number;

    @Column(name = "identity_Picture_Front")
    private String Identity_Picture_Front;

    @Column(name = "identity_picture_back")
    private String Identity_Picture_Back;

    @Column(name = "address")
    private String Address;

    @Column(name = "year_experience")
    private int Year_Experience;

    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date Start_Date;

    @Column(name = "diver_number_license")
    private String Driver_Number_License;

    @Column(name = "driving_license_image_front")
    private String driving_license_image_Front;

    @Column(name = "driving_license_image_back")
    private String driving_license_image_back;

    @OneToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn(name = "license_id",nullable = false, referencedColumnName = "license_id")
    private LicenseTypeEntity licenseTypeEntity;

    @ManyToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn(name = "district_id")
    private DistrictsEntity districtsEntity;

    @OneToMany(mappedBy = "driverEntity", cascade = CascadeType.ALL)
    private List<ContractDetailEntity> contractDetailEntityList =new ArrayList<>();


}
