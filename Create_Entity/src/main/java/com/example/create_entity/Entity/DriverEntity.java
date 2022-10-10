package com.example.create_entity.Entity;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.logging.Log;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Transactional
@Table(name = "Driver")
public class DriverEntity  {


    @Id
    @Column(name = "id_diver",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "year_experience")
    private int Year_Experience;

    @Column(name = "diver_number_license")
    private String Driver_Number_License;

    @Column(name = "status")
    private int Status;

    @Override
    public String toString() {
        return "DriverEntity{" +
                "id=" + id +
                ", Year_Experience=" + Year_Experience +
                ", Driver_Number_License='" + Driver_Number_License + '\'' +
                ", Status=" + Status +
                ", Start_Date=" + Start_Date +
                ", driving_license_image_Front='" + driving_license_image_Front + '\'' +
                ", driving_license_image_back='" + driving_license_image_back + '\'' +
                ", accountEntity=" + accountEntity +
                ", licenseTypeEntity=" + licenseTypeEntity +
                '}';
    }

    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date Start_Date;


    @Column(name = "driving_license_image_front")
    private String driving_license_image_Front;

    @Column(name = "driving_license_image_back")
    private String driving_license_image_back;

    @OneToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn(name = "account_id",nullable = false,referencedColumnName = "account_id")
    private AccountEntity accountEntity;

    @OneToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn(name = "license_id",nullable = false, referencedColumnName = "license_id")
    private LicenseTypeEntity licenseTypeEntity;
}
