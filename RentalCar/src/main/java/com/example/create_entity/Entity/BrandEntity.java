package com.example.create_entity.Entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "brand")
public class BrandEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private long brandId;

    @Column(nullable = false,length = 100)
    private String brandName;

    @Column
    private String brandImg;

    @Column
    private String description;

    @OneToMany(
            mappedBy = "brand"
    )
    private List<CarEntity> carEntities = new ArrayList<>();

    public BrandEntity(){}

    public BrandEntity(long brandId, String brandName, String brandImg, String description) {
        this.brandId = brandId;
        this.brandName = brandName;
        this.brandImg = brandImg;
        this.description = description;
    }

    public long getBrandId() {
        return brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandImg() {
        return brandImg;
    }

    public void setBrandImg(String brandImg) {
        this.brandImg = brandImg;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}