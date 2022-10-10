package com.example.create_entity.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "brands")
public class BrandEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name",columnDefinition ="VARCHAR(2048) NOT NULL, FULLTEXT KEY nameFulltext (name)")
    private String name;

    @Column(name = "img")
    private String img;

    @Column(name = "description",length = 3000)
    private String description;

    @Column(name = "status")
    private int status;

    @OneToMany(
            mappedBy = "brand"
    )
    private List<CarEntity> carEntities = new ArrayList<>();

}