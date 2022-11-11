package com.example.create_entity.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Staffs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class StaffEntity implements Serializable {
    @Id
    @Column(name = "Staff_ID",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID ;

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

}
