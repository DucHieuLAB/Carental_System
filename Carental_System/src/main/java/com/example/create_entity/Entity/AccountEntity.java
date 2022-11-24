package com.example.create_entity.Entity;
import lombok.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Accounts",
        uniqueConstraints = {@UniqueConstraint(name = "UNIQUE_USERNAME_Email", columnNames = {"user_name", "email"})})
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id",nullable = false)
    private Long ID;

    @Column(name = "user_name", nullable = false)
    private String Username;

    @Column(name = "pass_word", nullable = false)
    private String Password;

    @Column(name = "email", nullable = false)
    private String Email;

    @Column(name = "status")
    private int status;

    @Column(name = "modified_date")
    @Temporal(TemporalType.DATE)
    private Date ModifiedDate;

    @Column(name = "create_date")
    @Temporal(TemporalType.DATE)
    private Date CreateDate;

    @Column(name = "OTP")
    private String Otp;

    @Column(name="otp_requested_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date otp_requested_time;

    @ManyToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinColumn(name = "role_Id")
    private RoleEntity roleEntity;
    private static final long OTP_VALID_DURATION = 5 * 60 * 1000;
    public boolean isOTPRequired() {
        long currentTimeInMillis = System.currentTimeMillis();
        long otpRequestedTimeInMillis = this.getOtp_requested_time().getTime();

        if (otpRequestedTimeInMillis + OTP_VALID_DURATION < currentTimeInMillis) {
            return false;
        }
        return true;
    }


//    @ManyToOne(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
//    @JoinColumn(name = "district_id")
//    private DistrictsEntity districtsEntity;
//
//
//
//    @Column(name = "full_name", nullable = false)
//    private String FullName;
//
//    @Column(name = "dob")
//    @Temporal(TemporalType.DATE)
//    private Date DOB;
//
//    @Column(name = "gender")
//    private int Gender;
//
//
//    @Column(name = "phone")
//    private String Phone;
//
//
//    @Column(name = "status")
//    private int status;
//
//    @Column(name = "img")
//    private String img;
//
//    @Column(name = "identity_number")
//    private String Identity_Number;
//
//    @Column(name = "identity_Picture_Front")
//    private String Identity_Picture_Front;
//
//    @Column(name = "identity_picture_back")
//    private String Identity_Picture_Back;
//
//    @Column(name = "address")
//    private String Address;

}
