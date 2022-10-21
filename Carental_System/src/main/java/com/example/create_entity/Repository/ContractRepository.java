package com.example.create_entity.Repository;

import com.example.create_entity.Entity.ContractEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<ContractEntity,Long> {
    @Query("SELECT b FROM ContractEntity b WHERE b.customer.ID = ?1 AND b.expected_start_date = ?2 AND b.expected_end_date = ?3")
    ContractEntity findByCustomerIDAndExpectedStartDateAndExpectedEndDate(Long customerId, Date startDate, Date endDate);


    @Query("SELECT b FROM ContractEntity b WHERE b.customer.ID = ?1 AND b.expected_start_date = ?2 AND b.expected_end_date = ?3 AND b.status > 0 ")
    ContractEntity getByCustomerIdAndExpectStartDateAndExpectEndDate(Long id, Date expected_start_date, Date expected_end_date);

    @Query(value = "select * from contracts order by expected_start_date ASC",nativeQuery = true)
    Page<ContractEntity> ListContract(Pageable pageable);




    @Query(value = "select * from contracts " +
            "left join accounts " +
            "on contracts.customer_id=accounts.account_id " +
            "where accounts.full_name " +
            "like %?% order by " +
            "contracts.expected_start_date asc",nativeQuery = true)
    List<ContractEntity> FilterByName(String name,Pageable pageable);


    @Query(value = "select * from contracts " +
            "left join accounts " +
            "on contracts.customer_id=accounts.account_id " +
            "where accounts.full_name " +
            "like %?% order by " +
            "contracts.expected_start_date asc",nativeQuery = true)
    List<ContractEntity> FilterByName1(String name);

    @Query(value = "select * from contracts " +
            "left join accounts " +
            "on contracts.customer_id=accounts.account_id " +
            "where accounts.phone " +
            "like %?% order by " +
            "contracts.expected_start_date asc",nativeQuery = true)
    List<ContractEntity> FilterByPhone(String phone,Pageable pageable);


    @Query(value = "select * from contracts " +
            "left join accounts " +
            "on contracts.customer_id=accounts.account_id " +
            "where accounts.phone " +
            "like %?% order by " +
            "contracts.expected_start_date asc",nativeQuery = true)
    List<ContractEntity> FilterByPhone1(String phone);


    @Query(value = "select * from contracts  where contracts.had_driver=1 order by contracts.expected_start_date asc",nativeQuery = true)
    List<ContractEntity> FilterByHadDriver(Pageable pageable);
    @Query(value = "select * from contracts  where contracts.had_driver=1 order by contracts.expected_start_date asc",nativeQuery = true)
    List<ContractEntity> FilterByHadDriver1();

    @Query(value = "select * from contracts  where contracts.had_driver=0 order by contracts.expected_start_date asc",nativeQuery = true)
    List<ContractEntity> FilterByNotHadDriver(Pageable pageable);
    @Query(value = "select * from contracts  where contracts.had_driver=0 order by contracts.expected_start_date asc",nativeQuery = true)
    List<ContractEntity> FilterByNotHadDriver1();


    @Query(value ="select * from contracts  where status=1 order by contracts.expected_start_date asc" ,nativeQuery = true)
    Page<ContractEntity> FilterByWaitingForProgressing(Pageable pageable);

    @Query(value ="select * from contracts  where status=2 order by contracts.expected_start_date asc" ,nativeQuery = true)
    Page<ContractEntity> FilterByWaitForConfirmation(Pageable pageable);

    @Query(value ="select * from contracts  where status=3 order by contracts.expected_start_date asc" ,nativeQuery = true)
    Page<ContractEntity> FilterByEffective(Pageable pageable);


    @Query(value ="select * from contracts  where status=4 order by contracts.expected_start_date asc" ,nativeQuery = true)
    Page<ContractEntity> FilterByActivate(Pageable pageable);


    @Query(value ="select * from contracts  where status=5 order by contracts.expected_start_date asc" ,nativeQuery = true)
    Page<ContractEntity> FilterByClose(Pageable pageable);


    @Query(value ="select * from contracts  where status=6 order by contracts.expected_start_date asc" ,nativeQuery = true)
    Page<ContractEntity> FilterByCancel(Pageable pageable);



    @Query("SELECT c FROM ContractEntity c WHERE c.id = ?1 and c.status > 0 ")
    ContractEntity FindByID(Long id);

}
