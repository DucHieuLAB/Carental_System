package com.example.create_entity.Repository;

import com.example.create_entity.Entity.DriverEntity;
import com.example.create_entity.Entity.StaffEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Repository
public interface DriverRepository extends JpaRepository<DriverEntity, Long> {

    @Transactional
    @Query(value = "SELECT * FROM driver WHERE id_diver = ?1 ", nativeQuery = true)
    DriverEntity GetDriverById(Long id);

    @Query(value = "select *  from driver inner join accounts on driver.account_id=accounts.account_id where accounts.user_name = ?", nativeQuery = true)
    DriverEntity GetByUsername(String username);

    @Transactional
    @Query(value = "select * from driver where driver.status=1 ", nativeQuery = true)
    Page<DriverEntity> GetDriverByStatus(Pageable pageable);

    @Transactional
    @Query(value = "select * from driver where driver.status=1 ", nativeQuery = true)
    List<DriverEntity> GetDriverByStatus1();

    @Query(value = "SELECT  dr FROM DriverEntity dr INNER JOIN AccountEntity ac ON dr.accountEntity.ID = ac.ID WHERE dr.FullName LIKE %?1% ")
    Page<DriverEntity> GetDriverBy_fullName(String name, Pageable pageable);

    @Query(value = "SELECT  dr FROM DriverEntity dr INNER JOIN AccountEntity ac ON dr.accountEntity.ID = ac.ID WHERE dr.Phone LIKE %?1%")
    Page<DriverEntity> GetDriverBy_Phone(String phone, Pageable pageable);


    @Query(value = "SELECT de FROM DriverEntity de WHERE de.Phone = ?1 ")
    List<DriverEntity> Check_Phone(String Phone);

    @Query(value = "SELECT de FROM DriverEntity de WHERE de.Identity_Number = ?1 ")
    List<DriverEntity> Check_Identity(String Phone);

    @Query(value = "SELECT  dr FROM DriverEntity dr INNER JOIN AccountEntity ac ON dr.accountEntity.ID = ac.ID WHERE dr.Identity_Number LIKE %?1% ")
    Page<DriverEntity> GetDriverBy_Identity(String cmt, Pageable pageable);


    @Query(value = "SELECT * From driver where driver.diver_number_license=? ", nativeQuery = true)
    List<DriverEntity> Check_diver_number_license(String diver_number_license);

    @Query(value = "SELECT *" +
            "FROM driver d\n" +
            "LEFT JOIN contract_details c on d.id_diver = c.driver_id\n" +
            "JOIN contracts ct on c.contract_id = ct.booking_id\n" +
            "WHERE ct.expected_start_date >= ?2 AND ct.expected_start_date <= ?3\n" +
            "OR ct.expected_start_date < ?2 AND ct.expected_end_date >  ?2\n" +
            "AND d.id_diver = ?1\n", nativeQuery = true)
    DriverEntity findDriverValidDate(Long id, Date expected_start_date, Date expected_end_date);

    @Query(value = "SELECT *\n" +
            "FROM driver d\n" +
            "LEFT JOIN contract_details c on d.id_diver = c.driver_id\n" +
            "LEFT JOIN contracts ct on  c.contract_id = ct.booking_id AND  ct.expected_start_date = NULl  AND ct.expected_end_date = NULL  \n" +
            " OR  c.contract_id = ct.booking_id AND ct.expected_start_date > ?2 \n" +
            " OR c.contract_id = ct.booking_id AND ct.expected_end_date < ?1\n" +
            "JOIN cars on cars.plate_number = ?3 AND d.license_id >= cars.license_id ", nativeQuery = true)
    List<DriverEntity> getDriverByPlateNumberExpectedStartDateExpectedEnđate(Date expectedStartDate, Date expectedEndDate, String plateNumber);


//    @Modifying
//    @Transactional
//    @Query(value = "DELETE FROM driver WHERE code = ?1 ",nativeQuery = true)
//    void Delete(String code);
//
//    @Modifying
//    @Transactional
//    @Query(value = "UPDATE  SET  Acc   FROM driver WHERE id_diver = 1 ",nativeQuery = true)
//    void Delete(Long ID);


//    @Transactional
//    @Query(value = "select * from driver left join accounts on driver.account_id=accounts.account_id where accounts.full_name like  %?%  ", nativeQuery = true)
//    List<DriverEntity> GetDriverBy_fullName1(String name);
//    @Transactional
//    @Query(value = "select * from driver left join accounts on driver.account_id=accounts.account_id where  like  %?%  ", nativeQuery = true)
//    List<DriverEntity> GetDriverByPhone(String name, Pageable pageable);

//    @Query(value = "select * from driver cross join accounts on driver.account_id=accounts.account_id where accounts.phone LIKE  %?%  ", nativeQuery = true)
//    List<DriverEntity> GetDriverBy_Phone1(String name);

//    @Transactional
//    @Query(value = "select * from driver cross join accounts on driver.account_id=accounts.account_id where accounts.identity_number LIKE %?%  ", nativeQuery = true)
//    List<DriverEntity> GetDriverBy_Identity1(String name);

}
