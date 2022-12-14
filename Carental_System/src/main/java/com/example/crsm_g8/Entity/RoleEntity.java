package com.example.crsm_g8.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Roles")
public class RoleEntity {
    @Id
    @GeneratedValue
    @Column(name = "role_Id", nullable = false)
    private Long RoleID;


    @Column(name = "Role_Title")
    private String Role_Title;

    @Column(name="Status")
    private int Status;

    @Column(name = "Description"  )
    private String Description;

}
