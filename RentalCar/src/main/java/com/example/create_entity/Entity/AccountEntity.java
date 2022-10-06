package com.example.create_entity.Entity;


import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.repository.Modifying;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "Account",
        uniqueConstraints = {
                @UniqueConstraint(name = "UNIQUE", columnNames = {"user_name", "pass_word", "email"})})
public class AccountEntity {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id",nullable = false)
    private Long ID;


    @Column(name = "user_name", nullable = false)
    private String Username;

    @Column(name = "pass_word", nullable = false)
    private String Password;

    @Column(name = "full_name", nullable = false)
    private String FullName;


    @Column(name = "dob")
    @Temporal(TemporalType.DATE)
    private Date DOB;

    @Column(name = "gender")
    private int Gender;

    @Column(name = "email", nullable = false)
    private String Email;

    @Column(name = "phone")
    private String Phone;

    @Column(name = "modified_date")
    @Temporal(TemporalType.DATE)
    private Date ModifiedDate;

    @Column(name = "create_date")
    @Temporal(TemporalType.DATE)
    private Date CreateDate;

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


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_Id", nullable = false)
    private RoleEntity roleEntity;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id", nullable = false)
    private DistrictsEntity districtsEntity;

    public AccountEntity() {
    }
}
