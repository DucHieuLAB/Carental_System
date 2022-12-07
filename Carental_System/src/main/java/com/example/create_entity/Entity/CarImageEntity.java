package com.example.create_entity.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CarImages")
public class CarImageEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "img", length = 4000)
    private String img;

    @Column(name = "status", nullable = false)
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false, foreignKey = @ForeignKey(name = "FK_Car_Image_id"))
    CarEntity car;


    public List<Object> ResponseImg(List<CarImageEntity> carImageEntity) {
        List<Object> objectList = new ArrayList<>();
        carImageEntity.forEach(CarImageEntity -> {
            objectList.add(CarImageEntity.getImg());
        });
        return objectList;
    }
}
