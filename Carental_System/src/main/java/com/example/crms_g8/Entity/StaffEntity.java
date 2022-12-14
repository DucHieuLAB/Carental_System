package com.example.crms_g8.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Staffs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class StaffEntity {
    @Id
    @Column(name = "ID",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", nullable = false,unique = true)
    private AccountEntity accountEntity;

    @Column(name = "full_name", nullable = false)
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

    @Column(name = "img")
    private String img;

    @Column(name = "status")
    private int status;

    @Column(name = "identity_number")
    private String Identity_Number;

    @Column(name = "identity_Picture_Front")
    private String Identity_Picture_Front;

    @Column(name = "identity_picture_back")
    private String Identity_Picture_Back;

    @Column(name = "address")
    private String Address;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "district_id")
    private DistrictsEntity districtsEntity;

    @OneToMany(mappedBy = "staffEntity", cascade = CascadeType.ALL)
    private List<PaymentEntity> paymentEntities =new ArrayList<>();


    @OneToMany(mappedBy = "staffEntity", cascade = CascadeType.ALL)
    private List<SurchargeEntity> surchargeEntities =new ArrayList<>();
}
