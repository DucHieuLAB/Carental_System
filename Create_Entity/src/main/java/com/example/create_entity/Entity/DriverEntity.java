package com.example.create_entity.Entity;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.logging.Log;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "Driver")
public class DriverEntity {


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

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public int getYear_Experience() {
        return Year_Experience;
    }

    public void setYear_Experience(int year_Experience) {
        Year_Experience = year_Experience;
    }

    public String getDriver_Number_License() {
        return Driver_Number_License;
    }

    public void setDriver_Number_License(String driver_Number_License) {
        Driver_Number_License = driver_Number_License;
    }


    public Date getStart_Date() {
        return Start_Date;
    }

    public void setStart_Date(Date start_Date) {
        Start_Date = start_Date;
    }

    public String getDriving_license_image_Front() {
        return driving_license_image_Front;
    }

    public void setDriving_license_image_Front(String driving_license_image_Front) {
        this.driving_license_image_Front = driving_license_image_Front;
    }

    public String getDriving_license_image_back() {
        return driving_license_image_back;
    }

    public void setDriving_license_image_back(String driving_license_image_back) {
        this.driving_license_image_back = driving_license_image_back;
    }

    public AccountEntity getAccountEntity() {
        return accountEntity;
    }

    public void setAccountEntity(AccountEntity accountEntity) {
        this.accountEntity = accountEntity;
    }

    public LicenseTypeEntity getLicenseTypeEntity() {
        return licenseTypeEntity;
    }

    public void setLicenseTypeEntity(LicenseTypeEntity licenseTypeEntity) {
        this.licenseTypeEntity = licenseTypeEntity;
    }

    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date Start_Date;


    @Column(name = "driving_license_image_front")
    private String driving_license_image_Front;

    @Column(name = "driving_license_image_back")
    private String driving_license_image_back;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id",nullable = false)
    private AccountEntity accountEntity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "license_id",nullable = false)
    private LicenseTypeEntity licenseTypeEntity;
}
