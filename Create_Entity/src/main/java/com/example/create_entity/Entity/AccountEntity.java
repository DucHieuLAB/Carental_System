package com.example.create_entity.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "Accounts",
        uniqueConstraints = {
                @UniqueConstraint(name = "UNIQUE", columnNames = {"user_name", "email"})})
public class AccountEntity   {

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


    @ManyToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn(name = "role_Id")
    private RoleEntity roleEntity;


    @ManyToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn(name = "district_id")
    private DistrictsEntity districtsEntity;

    public AccountEntity() {

    }

    @Override
    public String toString() {
        return "AccountEntity{" +
                "ID=" + ID +
                ", Username='" + Username + '\'' +
                ", Password='" + Password + '\'' +
                ", FullName='" + FullName + '\'' +
                ", DOB=" + DOB +
                ", Gender=" + Gender +
                ", Email='" + Email + '\'' +
                ", Phone='" + Phone + '\'' +
                ", ModifiedDate=" + ModifiedDate +
                ", CreateDate=" + CreateDate +
                ", status=" + status +
                ", img='" + img + '\'' +
                ", Identity_Number='" + Identity_Number + '\'' +
                ", Identity_Picture_Front='" + Identity_Picture_Front + '\'' +
                ", Identity_Picture_Back='" + Identity_Picture_Back + '\'' +
                ", Address='" + Address + '\'' +
                ", roleEntity=" + roleEntity +
                ", districtsEntity=" + districtsEntity +
                '}';
    }
}
