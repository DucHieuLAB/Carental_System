package com.example.create_entity.Repository;

import com.example.create_entity.Entity.ContractDetailEntity;
import com.example.create_entity.Entity.DriverEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ContractDetailRepository extends JpaRepository<ContractDetailEntity, Long> {

    @Query("SELECT b FROM ContractDetailEntity b WHERE b.booking.id = ?1")
    List<ContractDetailEntity> getListBookingDetailEntitiesByBookingId(Long id);


    @Query(value = "SELECT * FROM carrental.contract_details where contract_detail_id = ?1 ORDER BY contract_detail_id LIMIT 1", nativeQuery = true)
    ContractDetailEntity BookingDetail(Long id);

    @Query(value = "SELECT * FROM carrental.contract_details \n" +
            "left join contracts on contract_details.contract_id = contracts.booking_id\n" +
            "join cars on contract_details.car_id=cars.id \n" +
            "where contracts.expected_start_date > now() and contracts.status = 4 " +
            " order by contracts.expected_start_date asc ", nativeQuery = true)
//    @Query("select c from ContractDetailEntity c" +
//            " JOIN ContractEntity CE on c.booking.id = CE.id " +
//            "JOIN CarEntity ca on c.car.id = ca.id ")
//

//    @Query("SELECT b FROM ContractDetailEntity b")
    List<ContractDetailEntity> Schedule(Pageable pageable);

    @Query(value = "SELECT * FROM carrental.contract_details \n" +
            "left join contracts on contract_details.contract_id = contracts.booking_id\n" +
            "join cars on contract_details.car_id=cars.id \n" +
            "where contracts.expected_start_date > now() and contracts.status = 4 ", nativeQuery = true)
    List<ContractDetailEntity> Schedule_count();

    //AND contracts.had_driver = CASE WHEN ?2 IS NULL THEN contracts.had_driver  ELSE ?2 END
    @Query("select c from ContractDetailEntity c " +
            " JOIN ContractEntity ce on c.booking.id = ce.id " +
            "JOIN CarEntity ca on c.car.id = ca.id " +
            "WHERE ce.expected_start_date > CURRENT_DATE AND ce.status=4" +
            "AND ca.modelName  LIKE ?1 " +
            "ORDER BY ce.expected_start_date ASC ")

    Page<ContractDetailEntity> SearchName_schedule(String name,Pageable pageable);


//    @Query("select c from ContractDetailEntity c " +
//            " JOIN ContractEntity ce on c.booking.id = ce.id " +
//            "JOIN CarEntity ca on c.car.id = ca.id " +
//            "WHERE ce.expected_start_date > CURRENT_DATE " +
//            "AND ca.modelName  LIKE ?1  " +
//            "AND  ce.expected_start_date BETWEEN  ?2 AND ?3 " +
//            "ORDER BY ce.expected_start_date ASC " )
    @Query(value = "\t SELECT * FROM carrental.contract_details \n" +
            "            join contracts on contract_details.contract_id = contracts.booking_id\n" +
            "            join cars on contract_details.car_id=cars.id \n" +
            "            where contracts.expected_start_date > now() " +
            "            and cars.model_name Like %?1% and contracts.status = 4 " +
            "            AND expected_start_date  BETWEEN ?2 and ?3 " +
            "            order by expected_start_date asc  " ,nativeQuery = true )

   List<ContractDetailEntity> SearchName_schedule_Date(String name,String date1,String date2,Pageable pageable);

    @Query(value = "\t SELECT * FROM carrental.contract_details \n" +
            "            join contracts on contract_details.contract_id = contracts.booking_id\n" +
            "            join cars on contract_details.car_id=cars.id \n" +
            "            where contracts.expected_start_date > now() " +
            "            and cars.model_name Like %?1% and contracts.status = 4 " +
            "            AND expected_start_date  BETWEEN ?2 and ?3 ",nativeQuery = true )

    List<ContractDetailEntity> SearchName_schedule_Date1(String name,String date1,String date2);



    @Query("select c from ContractDetailEntity c " +
            " JOIN ContractEntity ce on c.booking.id = ce.id " +
            "JOIN CarEntity ca on c.car.id = ca.id " +
            "WHERE ce.expected_start_date > CURRENT_DATE " +
            "AND ca.plateNumber  LIKE ?1 AND ce.status=4" +
            "ORDER BY ce.expected_start_date ASC ")

    Page<ContractDetailEntity> SearchPlateNumber_schedule(String name,Pageable pageable);


    //    @Query("select c from ContractDetailEntity c " +
//            " JOIN ContractEntity ce on c.booking.id = ce.id " +
//            "JOIN CarEntity ca on c.car.id = ca.id " +
//            "WHERE ce.expected_start_date > CURRENT_DATE " +
//            "AND ca.modelName  LIKE ?1  " +
//            "AND  ce.expected_start_date BETWEEN  ?2 AND ?3 " +
//            "ORDER BY ce.expected_start_date ASC " )
    @Query(value = "\t SELECT * FROM carrental.contract_details \n" +
            "            join contracts on contract_details.contract_id = contracts.booking_id\n" +
            "            join cars on contract_details.car_id=cars.id \n" +
            "            where contracts.expected_start_date > now() " +
            "            and cars.plate_number Like %?1% and contracts.status = 4 " +
            "            AND expected_start_date  BETWEEN ?2 and ?3 " +
            "            order by expected_start_date asc  " ,nativeQuery = true )

    List<ContractDetailEntity> SearchPlateNumber_schedule_Date(String name,String date1,String date2,Pageable pageable);

    @Query(value = "\t SELECT * FROM carrental.contract_details \n" +
            "            join contracts on contract_details.contract_id = contracts.booking_id\n" +
            "            join cars on contract_details.car_id=cars.id \n" +
            "            where contracts.expected_start_date > now() " +
            "            and cars.plate_number Like %?1% and contracts.status = 4 " +
            "            AND expected_start_date  BETWEEN ?2 and ?3 ",nativeQuery = true )

    List<ContractDetailEntity>SearchPlateNumber_schedule_Date1(String name,String date1,String date2);



}
