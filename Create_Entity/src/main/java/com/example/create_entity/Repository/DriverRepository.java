package com.example.create_entity.Repository;

import com.example.create_entity.Entity.DriverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRepository extends JpaRepository<DriverEntity,Integer> {

    @Query(value = "SELECT * FROM car_rental.driver",nativeQuery = true)
    List<DriverEntity> GetAll();
}
