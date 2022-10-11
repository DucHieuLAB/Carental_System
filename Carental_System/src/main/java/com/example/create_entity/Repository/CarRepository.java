package com.example.create_entity.Repository;

import com.example.create_entity.Entity.BrandEntity;
import com.example.create_entity.Entity.CarEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<CarEntity,Long> {
    @Query("SELECT DISTINCT(c.capacity)  FROM CarEntity c  ")
    public List<Integer> getListCapacity();

    @Query("SELECT c FROM CarEntity c WHERE c.id = ?1 and c.status > 0")
    CarEntity findCarEntityById(Long id);

    @Query("SELECT c FROM CarEntity c WHERE c.plateNumber = ?1 and c.status > 0")
    CarEntity findCarEntityByPlateNumber(String plateNumber);

    @Query(value = "SELECT * FROM CarEntity b WHERE MATCH(plate_number) "
            + "AGAINST (?1)AND capacity = ?2 AND MATCH (parking_id) AGAINST (?3)  AND status = ?3", nativeQuery = true )
    Page<BrandEntity> findBySearch(String plate_number,Integer capacity,Long ParkingId,Integer status, Pageable pageable);

    @Query("SELECT c FROM CarEntity c WHERE c.status > 0")
    Page<CarEntity> findCarEntityByStatus(Pageable pageable);

    @Query("SELECT c FROM CarEntity c WHERE c.id = ?1 AND c.plateNumber = ?2 AND c.status > 0")
    CarEntity findCarEntityByIdAndPlateNumber(Long id, String status);

    @Query(value = "SELECT * FROM cars WHERE MATCH(model_name) "
            + "AGAINST (?1) "
            + "AND parking_id = CASE WHEN ?2 IS NULL THEN parking_id ELSE ?2 END "
            + "AND capacity = CASE WHEN ?3 IS NULL THEN capacity ELSE ?3 END "
            + "AND status > 0"
            , nativeQuery = true )
    Page<CarEntity> findBySearch(String modelName, Long parkingId, int capacity, Pageable pageable);

    @Query("SELECT c FROM CarEntity c WHERE c.parking.id = CASE WHEN ?1 IS NULL THEN c.parking.id ELSE ?1 END "
            + "AND c.capacity = CASE WHEN ?2 IS NULL THEN c.capacity ELSE ?2 END "
            + "AND c.status > 0")
    Page<CarEntity> findAllBySearch(Long parkingId, int capacity, Pageable pageable);
}

