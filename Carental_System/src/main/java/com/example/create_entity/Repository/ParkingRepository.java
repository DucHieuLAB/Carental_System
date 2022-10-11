package com.example.create_entity.Repository;

import com.example.create_entity.Entity.ParkingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ParkingRepository extends JpaRepository<ParkingEntity,Long> {
    @Query("SELECT p FROM ParkingEntity p WHERE p.id = ?1 and p.status = 1")
    ParkingEntity findParkingEntitiesByDistricId(Long DistricId);

}
