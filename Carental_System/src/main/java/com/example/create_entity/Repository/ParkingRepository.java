package com.example.create_entity.Repository;

import com.example.create_entity.Entity.ParkingEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ParkingRepository extends JpaRepository<ParkingEntity,Long> {
    @Query("SELECT p FROM ParkingEntity p WHERE p.id = ?1 and p.status = 1")
    ParkingEntity findParkingEntitiesByDistricId(Long DistricId);

    @Query("SELECT p FROM ParkingEntity p WHERE p.address = ?1 and  p.name = ?2 and  p.phone = ?3")
    ParkingEntity findParkingByAddressAndNameAndPhone(String address, String name, String phone);

    @Query("SELECT p FROM ParkingEntity p WHERE p.address = ?1 or  p.name = ?2 or  p.phone = ?3")
    List<ParkingEntity> findParkingByAddressOrNameOrPhone(String address, String name, String phone);


    @Query("SELECT p FROM ParkingEntity p WHERE p.status = 1")
    List<ParkingEntity> findAllNoPagding();

    @Query("SELECT p FROM ParkingEntity p WHERE p.id = ?1 AND p.status = 1")
    Optional<ParkingEntity> findByIdAndStatus(Long id);

    @Query(value = "SELECT * FROM parkings  WHERE  "
            + "status > 0",nativeQuery = true
    )
    Page<ParkingEntity> findAllPaging(Pageable pageable);

    @Query("SELECT p FROM ParkingEntity p WHERE p.name = ?1 AND p.status = 1")
    ParkingEntity findParkingByName(String name);
}
