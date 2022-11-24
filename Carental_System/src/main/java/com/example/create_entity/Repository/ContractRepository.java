package com.example.create_entity.Repository;

import com.example.create_entity.Entity.ContractEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContractRepository extends JpaRepository<ContractEntity, Long> {
    @Query("SELECT b FROM ContractEntity b WHERE b.customer.ID = ?1 AND b.expected_start_date = ?2 AND b.expected_end_date = ?3")
    ContractEntity findByCustomerIDAndExpectedStartDateAndExpectedEndDate(Long customerId, Date startDate, Date endDate);


    @Query("SELECT b FROM ContractEntity b WHERE b.customer.ID = ?1 AND b.expected_start_date = ?2 AND b.expected_end_date = ?3 AND b.status > 0 ")
    ContractEntity getByCustomerIdAndExpectStartDateAndExpectEndDate(Long id, Date expected_start_date, Date expected_end_date);

    @Query(value = "select * from contracts where (status = 1 or status = 2 or status = 3 )    order by expected_start_date ASC", nativeQuery = true)
    Page<ContractEntity> ListContract(Pageable pageable);


    @Query(value = "select * from contracts where (status = 4 or status = 5 or status = 6 or status=7 )    order by expected_start_date ASC", nativeQuery = true)
    Page<ContractEntity> ListContract_2(Pageable pageable);

    @Query(value = "select * from contracts left join customers  \n" +
            "            on contracts.id_customer=customers.id \n" +
            "            where customers.full_name like %?1% \n" +
            "            AND contracts.had_driver = CASE WHEN ?2 IS NULL THEN contracts.had_driver  ELSE ?2 END\n" +
            "            AND contracts.status = CASE WHEN ?3 IS NULL THEN contracts.status ELSE ?3 END\n" +
            "            ", nativeQuery = true)
    List<ContractEntity> FilterByName(String name, Integer HadDriver, Integer Status, Pageable pageable);


    @Query(value = "  select * from contracts left join customers  \n" +
            "            on contracts.id_customer=customers.id \n" +
            "            where customers.full_name like %?1% \n" +
            "            AND contracts.had_driver = CASE WHEN ?2 IS NULL THEN contracts.had_driver  ELSE ?2 END\n" +
            "            AND contracts.status = CASE WHEN ?3 IS NULL THEN contracts.status ELSE ?3 END\n" +
            "            ", nativeQuery = true)
    List<ContractEntity> FilterByName1(String name, Integer HadDriver, Integer Status);

    @Query(value = "  select * from contracts left join customers \n" +
            "            on contracts.id_customer=customers.id \n" +
            "            where customers.phone like %?1% \n" +
            "            AND contracts.had_driver = CASE WHEN ?2 IS NULL THEN contracts.had_driver  ELSE ?2 END\n" +
            "            AND contracts.status = CASE WHEN ?3 IS NULL THEN contracts.status ELSE ?3 END\n" +
            "            ", nativeQuery = true)
    List<ContractEntity> FilterByPhone(String name, Integer HadDriver, Integer Status, Pageable pageable);


    @Query(value = "  select * from contracts left join customers   \n" +
            "            on contracts.id_customer=customers.id \n" +
            "            where customers.phone like %?1% \n" +
            "            AND contracts.had_driver = CASE WHEN ?2 IS NULL THEN contracts.had_driver  ELSE ?2 END\n" +
            "            AND contracts.status = CASE WHEN ?3 IS NULL THEN contracts.status ELSE ?3 END\n" +
            "            ", nativeQuery = true)
    List<ContractEntity> FilterByPhone1(String name, Integer HadDriver, Integer Status);


    @Query("SELECT c FROM ContractEntity c WHERE c.id = ?1 and c.status > 0 ")
    ContractEntity FindByID(Long id);

    @Query("SELECT c FROM ContractEntity c WHERE c.customer.ID = ?1 ORDER BY c.expected_start_date DESC")
    List<ContractEntity> getByCustomerId(long customerId);

    @Query(value = "     SELECT *, DATEDIFF(ct.expected_end_date,?2) as dayDiff\n" +
            "            FROM cars \n" +
            "            JOIN parkings on parkings.id = cars.parking_id\n" +
            "\t\t\t      LEFT JOIN contract_details c on cars.id = c.car_id\n" +
            "            LEFT JOIN contracts ct on c.contract_id = ct.booking_id  \n" +
            "\t\t\t      WHERE cars.plate_number = ?1 and DATEDIFF(ct.expected_end_date,?2) < 0\n" +
            "            ORDER BY  dayDiff DESC LIMIT 1;", nativeQuery = true)
    Optional<ContractEntity> findContractByPlateNumberAndStartDate(String plateNumber, Date startDate);

    @Query(value = "     SELECT *, DATEDIFF(ct.expected_start_date,?2) as dayDiff\n" +
            "            FROM cars \n" +
            "            JOIN parkings on parkings.id = cars.parking_id\n" +
            "\t\t\t      LEFT JOIN contract_details c on cars.id = c.car_id\n" +
            "            LEFT JOIN contracts ct on c.contract_id = ct.booking_id  \n" +
            "\t\t\t      WHERE cars.plate_number = ?1 and DATEDIFF(ct.expected_start_date,?2) > 0  \n" +
            "            ORDER BY  dayDiff ASC \n" +
            "            LIMIT 1;", nativeQuery = true)
    Optional<ContractEntity> findContractByPlateNumberAndEndDate(String plateNumber, Date endDate);

    @Query("SELECT c FROM ContractEntity c WHERE c.id= ?1 and c.status > 2")
    ContractEntity findByIdAndStatus(long contractId);
}
