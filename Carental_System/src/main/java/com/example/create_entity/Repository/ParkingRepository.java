package com.example.create_entity.Repository;

import com.example.create_entity.Entity.ParkingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ParkingRepository extends JpaRepository<ParkingEntity,Long> {
    @Query("SELECT p FROM ParkingEntity p WHERE p.id = ?1 and p.status = 1")
    ParkingEntity findParkingEntitiesByDistricId(Long DistricId);

    @Query("SELECT p FROM ParkingEntity p WHERE p.address = ?1 and  p.name = ?2 and  p.phone = ?3")
    ParkingEntity findParkingByAddressAndNameAndPhone(String address, String name, String phone);

    @Query("SELECT p FROM ParkingEntity p WHERE p.status = 1")
    List<ParkingEntity> findAllNoPagding();
}
