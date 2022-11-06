package com.example.create_entity.Repository;

import com.example.create_entity.Entity.BrandEntity;
import com.example.create_entity.Entity.CarEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<CarEntity,Long> {
    @Query("SELECT DISTINCT(c.capacity)  FROM CarEntity c  ")
    List<Integer> getListCapacity();

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

    @Query(value = "SELECT * FROM cars  WHERE  cars.model_name like ?1 "
            + "AND cars.parking_id = CASE WHEN (?2) IS NULL THEN cars.parking_id ELSE ?2 END "
            + "AND cars.capacity = CASE WHEN (?3) IS NULL THEN cars.capacity  ELSE ?3 END "
            + "AND status > 0",nativeQuery = true
            )
    Page<CarEntity> findBySearch(String modelName, Long parkingId, Integer capacity, Pageable pageable);

    @Query("SELECT c FROM CarEntity c WHERE c.parking.id = CASE WHEN ?1 IS NULL THEN c.parking.id ELSE ?1 END "
            + "AND c.capacity = CASE WHEN ?2 IS NULL THEN c.capacity ELSE ?2 END "
            + "AND c.status > 0")
    Page<CarEntity> findAllBySearch(Long parkingId, int capacity, Pageable pageable);

    @Query("SELECT c FROM CarEntity c WHERE c.modelName = ?1 and c.status > 0 ")
    CarEntity findCarEntityByModelName(String modelName);

    @Query(value = "SELECT * \n" +
            "FROM cars \n" +
            "LEFT JOIN contract_details c on cars.id = c.car_id AND cars.plate_number = ?3\n" +
            "JOIN contracts ct on c.contract_id = ct.booking_id\n" +
            "WHERE ct.expected_start_date >= ?1 AND ct.expected_start_date <= ?2\n" +
            "OR ct.expected_start_date < ?1 AND ct.expected_end_date >  ?1 \n" +
            "OR ct.expected_start_date >=  ?1 AND ct.expected_end_date >  ?2 \n" +
            "LIMIT 1", nativeQuery = true)
    CarEntity checkCarValidInTime(Date expectedStartDate, Date expectedEndDate, String carPlateNumber);

    @Query(value = "SELECT * FROM cars",nativeQuery = true)
    List<CarEntity> getListCarBestSeller();

    @Query(value = "SELECT * \n" +
            "FROM cars \n" +
            "JOIN parkings on parkings.id = cars.parking_id And cars.parking_id = CASE WHEN (?3) IS NULL THEN cars.parking_id ELSE ?3 END\n" +
            "LEFT JOIN contract_details c on cars.id = c.car_id \n" +
            "LEFT JOIN contracts ct on c.contract_id = ct.booking_id AND  ct.expected_start_date = NULl  AND ct.expected_end_date = NULL  OR  c.contract_id = ct.booking_id AND ct.expected_start_date > ?2 OR c.contract_id = ct.booking_id AND ct.expected_end_date < ?1",nativeQuery = true)
    Page<CarEntity> findByStartDateAndEndDateAndParkingId(Date startDate, Date endDate, Long parkingId, Pageable pageable);

    @Query(value = "SELECT *\n" +
            "FROM cars\n" +
            "JOIN parkings on parkings.id = cars.parking_id And cars.parking_id = CASE WHEN (?3) IS NULL THEN cars.parking_id ELSE ?3 END\n" +
            "JOIN districts on districts.district_id = parkings.district_id AND districts.city = CASE WHEN (?4) IS NULL THEN districts.city ELSE ?4 END\n" +
            "LEFT JOIN contract_details c on cars.id = c.car_id \n" +
            "LEFT JOIN contracts ct on c.contract_id = ct.booking_id AND  ct.expected_start_date = NULl  AND ct.expected_end_date = NULL  OR  c.contract_id = ct.booking_id AND ct.expected_start_date > ?2 OR c.contract_id = ct.booking_id AND ct.expected_end_date < ?1",nativeQuery = true)
    Page<CarEntity> findByStartDateAndEndDateAndParkingIdAndCitiName(Date startDate, Date endDate, Long parkingId, String cityName, Pageable pageable);
}

