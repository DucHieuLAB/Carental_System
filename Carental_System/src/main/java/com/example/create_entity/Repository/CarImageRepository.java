package com.example.create_entity.Repository;

import com.example.create_entity.Entity.CarImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarImageRepository extends JpaRepository<CarImageEntity,Long> {
    @Query("SELECT c FROM CarImageEntity c WHERE c.car.plateNumber = ?1 AND c.status =  true")
    List<CarImageEntity> findCarImageEntitiesByPlateNumber(String plateNumber);

    @Query("SELECT c FROM CarImageEntity c WHERE c.car.id = ?1 and c.status = true")
    List<CarImageEntity> findCarImageEntitiesByCar_Id(Long id);

    @Query("SELECT c FROM CarImageEntity c WHERE c.img = ?1 and c.car.plateNumber = ?2 and c.status = true")
    CarImageEntity getCarImageEntityByImgUrlAndCarPlateNumber(String imgUrl, String plateNumber);
}
