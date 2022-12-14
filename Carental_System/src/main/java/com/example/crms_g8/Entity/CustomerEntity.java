package com.example.crms_g8.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "Customers")
@Getter
@Setter
@NoArgsConstructor
public class CustomerEntity {


    @Id
    @Column(name = "ID",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;



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

    @Column(name = "status")
    private int status;

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

    @ManyToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn(name = "district_id")
    private DistrictsEntity districtsEntity;


    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<ContractEntity> contractEntities =new ArrayList<>();
}
