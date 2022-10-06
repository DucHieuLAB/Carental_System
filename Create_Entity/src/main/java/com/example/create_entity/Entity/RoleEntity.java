package com.example.create_entity.Entity;

import javax.persistence.*;

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
