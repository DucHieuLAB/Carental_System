package com.example.create_entity.Entity;

import javax.persistence.*;

@Entity
@Table(name = "Role")
public class RoleEntity {
    @Id
    @GeneratedValue
    @Column(name = "role_Id", nullable = false)
    private Long RoleID;


    @Column(name = "Role_Title")
    private String Role_Title;

    @Column(name="Status")
    private Boolean Status;

    @Column(name = "Description" , nullable = true )
    private String Description;

}
