package com.example.crms_g8.Repository;

import com.example.crms_g8.Entity.ContractDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;


@Repository
public interface ContractDetailRepository extends JpaRepository<ContractDetailEntity, Long> {

    @Query("SELECT b FROM ContractDetailEntity b WHERE b.booking.id = ?1")
    List<ContractDetailEntity> getListContractDetailEntitiesByContractId(Long id);


    @Query(value = "SELECT * FROM carrental.contract_details where contract_detail_id = ?1 ORDER BY contract_detail_id LIMIT 1", nativeQuery = true)
    ContractDetailEntity getContractDetailById(Long id);

    @Query("SELECT ctd FROM  ContractDetailEntity ctd " +
            "INNER JOIN ContractEntity  ct ON ctd.booking.id = ct.id " +
            "WHERE ctd.driverEntity.accountEntity.Username = ?1 " +
            "AND ct.status=4 " +
            "AND ct.had_driver = true " +
            "AND ct.expected_start_date > current_date " +
            "ORDER BY ct.expected_start_date asc ")
    List<ContractDetailEntity> Future_Schedule(String username);

    @Query("SELECT ctd FROM  ContractDetailEntity ctd " +
            "INNER JOIN ContractEntity  ct ON ctd.booking.id = ct.id " +
            "WHERE ctd.driverEntity.accountEntity.Username = ?1 " +
            "AND ct.status=5 " +
            "AND ct.had_driver = true ")
    List<ContractDetailEntity> Current_Schedule(String username);

    @Query("SELECT ctd FROM  ContractDetailEntity ctd " +
            "INNER JOIN ContractEntity  ct ON ctd.booking.id = ct.id " +
            "WHERE ctd.driverEntity.accountEntity.Username = ?1 " +
            "AND ct.status=6 " +
            "AND ct.had_driver = true ")
    List<ContractDetailEntity> History_schedule(String username);


//    @Query(value = "SELECT * FROM contract_details inner join contracts on contract_details.contract_id=contracts.booking_id\n" +
//            "where  contract_details.id_driver= ?1 \n" +
//            "AND   contracts.status = 4  \n" +
//            "AND  contracts.had_driver = 1 \n" +
//            "AND expected_start_date > now() \n" +
//            "order by expected_end_date asc",nativeQuery = true)
//    List<ContractDetailEntity> Future_Schedule(Long DriverID);
//    @Query(value = "SELECT * FROM carrental.contract_details \n" +
//            "left join contracts on contract_details.contract_id = contracts.booking_id\n" +
//            "join cars on contract_details.car_id=cars.id \n" +
//            "where contracts.expected_start_date > now() and contracts.status = 4 ", nativeQuery = true)
//
//
//
//
//    List<ContractDetailEntity> Schedule_count();

    //AND contracts.had_driver = CASE WHEN ?2 IS NULL THEN contracts.had_driver  ELSE ?2 END
//    @Query("select c from ContractDetailEntity c " +
//            " JOIN ContractEntity ce on c.booking.id = ce.id " +
//            "JOIN CarEntity ca on c.car.id = ca.id " +
//            "WHERE ce.expected_start_date > CURRENT_DATE AND ce.status=4" +
//            "AND ca.modelName  LIKE ?1 " +
//            "ORDER BY ce.expected_start_date ASC ")
//
//    Page<ContractDetailEntity> SearchName_schedule(String name,Pageable pageable);


//    @Query("select c from ContractDetailEntity c " +
//            " JOIN ContractEntity ce on c.booking.id = ce.id " +
//            "JOIN CarEntity ca on c.car.id = ca.id " +
//            "WHERE ce.expected_start_date > CURRENT_DATE " +
//            "AND ca.modelName  LIKE ?1  " +
//            "AND  ce.expected_start_date BETWEEN  ?2 AND ?3 " +
//            "ORDER BY ce.expected_start_date ASC " )
//    @Query(value = "\t SELECT * FROM carrental.contract_details \n" +
//            "            join contracts on contract_details.contract_id = contracts.booking_id\n" +
//            "            join cars on contract_details.car_id=cars.id \n" +
//            "            where contracts.expected_start_date > now() " +
//            "            and cars.model_name Like %?1% and contracts.status = 4 " +
//            "            AND expected_start_date  BETWEEN ?2 and ?3 " +
//            "            order by expected_start_date asc  " ,nativeQuery = true )
//
//   List<ContractDetailEntity> SearchName_schedule_Date(String name,String date1,String date2,Pageable pageable);
//
//    @Query(value = "\t SELECT * FROM carrental.contract_details \n" +
//            "            join contracts on contract_details.contract_id = contracts.booking_id\n" +
//            "            join cars on contract_details.car_id=cars.id \n" +
//            "            where contracts.expected_start_date > now() " +
//            "            and cars.model_name Like %?1% and contracts.status = 4 " +
//            "            AND expected_start_date  BETWEEN ?2 and ?3 ",nativeQuery = true )
//
//    List<ContractDetailEntity> SearchName_schedule_Date1(String name,String date1,String date2);



//    @Query("select c from ContractDetailEntity c " +
//            " JOIN ContractEntity ce on c.booking.id = ce.id " +
//            "JOIN CarEntity ca on c.car.id = ca.id " +
//            "WHERE ce.expected_start_date > CURRENT_DATE " +
//            "AND ca.plateNumber  LIKE ?1 AND ce.status=4" +
//            "ORDER BY ce.expected_start_date ASC ")
//
//    Page<ContractDetailEntity> SearchPlateNumber_schedule(String name,Pageable pageable);


    //    @Query("select c from ContractDetailEntity c " +
//            " JOIN ContractEntity ce on c.booking.id = ce.id " +
//            "JOIN CarEntity ca on c.car.id = ca.id " +
//            "WHERE ce.expected_start_date > CURRENT_DATE " +
//            "AND ca.modelName  LIKE ?1  " +
//            "AND  ce.expected_start_date BETWEEN  ?2 AND ?3 " +
//            "ORDER BY ce.expected_start_date ASC " )
//    @Query(value = "\t SELECT * FROM carrental.contract_details \n" +
//            "            join contracts on contract_details.contract_id = contracts.booking_id\n" +
//            "            join cars on contract_details.car_id=cars.id \n" +
//            "            where contracts.expected_start_date > now() " +
//            "            and cars.plate_number Like %?1% and contracts.status = 4 " +
//            "            AND expected_start_date  BETWEEN ?2 and ?3 " +
//            "            order by expected_start_date asc  " ,nativeQuery = true )
//
//    List<ContractDetailEntity> SearchPlateNumber_schedule_Date(String name,String date1,String date2,Pageable pageable);
//
//    @Query(value = "\t SELECT * FROM carrental.contract_details \n" +
//            "            join contracts on contract_details.contract_id = contracts.booking_id\n" +
//            "            join cars on contract_details.car_id=cars.id \n" +
//            "            where contracts.expected_start_date > now() " +
//            "            and cars.plate_number Like %?1% and contracts.status = 4 " +
//            "            AND expected_start_date  BETWEEN ?2 and ?3 ",nativeQuery = true )
//
//    List<ContractDetailEntity>SearchPlateNumber_schedule_Date1(String name,String date1,String date2);


    @Query("SELECT cde FROM ContractDetailEntity cde WHERE cde.car.id = ?1 and cde.booking.status > 0 and cde.booking.status < 6 and cde.booking.expected_start_date >= ?2 ")
    List<ContractDetailEntity> findContractDetailByCar(Long carId, Date currenDate);

    @Query(value = "SELECT COUNT(*) FROM contract_details c,contracts ct WHERE c.contract_id = ct.booking_id and ct.status > 0 and ct.status < 6  and c.contract_id = ?1 ",nativeQuery = true)
    long getCountContractDetail(long contractId);

    @Query(value = "SELECT COUNT(*) FROM contract_details c,contracts ct WHERE c.contract_id = ct.booking_id and ct.status > 0 and ct.status < 6 and c.id_driver = ?1 ",nativeQuery = true)
    long getCountContractDetailByDriverId(Long id);

    @Query("SELECT c FROM  ContractDetailEntity c WHERE c.car.plateNumber = ?2 and c.booking.id = ?1")
    ContractDetailEntity findContractDetailByContractIdByPlateNumber(long contractId, String carPlateNumber);

@Query("SELECT c FROM  ContractDetailEntity c WHERE c.driverEntity.id = ?1 and c.booking.expected_start_date > ?2 and c.booking.status > 1 and c.booking.status < 6")
    List<ContractDetailEntity> checkHadAnyContract(Long id, Date currendate);

    @Query("SELECT c FROM  ContractDetailEntity c WHERE c.car.plateNumber = ?1 and c.booking.expected_start_date > ?2 and c.booking.status > 2 and c.booking.status < 6 ")
    List<ContractDetailEntity> checkCarHadAnyContract(String plateNumber, Date date);

    @Query(value = "SELECT * FROM carrental.contract_details " +
            "inner join contracts on contract_details.contract_id = contracts.booking_id " +
            "where contracts.create_date >= DATE(NOW()) - INTERVAL 30 DAY ",nativeQuery = true)
    List<ContractDetailEntity>CountCar();

//    @Query("SELECT ")
//    ContractDetailEntity Detail_Schedule(Long driverID);
}
