package com.example.create_entity.Entity;

import javax.persistence.*;


@Entity
@Table(name = "CarImages",
        uniqueConstraints =
                { @UniqueConstraint(name = "unique_plate_number_img",
                        columnNames = { "plate_number", "img" })})
public class CarImageEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "plate_number" ,  nullable = false ,length = 100)
    private String plateNumber;

    @Column(name = "img",length = 100)
    private String img;

    @ManyToOne
    @JoinColumn(name = "car_id",nullable = false, foreignKey = @ForeignKey(name = "FK_Car_Image_id"))
    CarEntity car;

}
