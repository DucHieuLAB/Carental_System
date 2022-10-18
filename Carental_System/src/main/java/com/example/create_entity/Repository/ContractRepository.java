package com.example.create_entity.Repository;

import com.example.create_entity.Entity.ContractEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
@Repository
public interface ContractRepository extends JpaRepository<ContractEntity,Long> {
    @Query("SELECT b FROM ContractEntity b WHERE b.customer.ID = ?1 AND b.expected_start_date = ?2 AND b.expected_end_date = ?3")
    ContractEntity findByCustomerIDAndExpectedStartDateAndExpectedEndDate(Long customerId, Date startDate, Date endDate);


    @Query("SELECT b FROM ContractEntity b WHERE b.customer.ID = ?1 AND b.expected_start_date = ?2 AND b.expected_end_date = ?3 AND b.status > 0 ")
    ContractEntity getByCustomerIdAndExpectStartDateAndExpectEndDate(Long id, Date expected_start_date, Date expected_end_date);

    @Query(value = "select * from bookings order by create_date DESC",nativeQuery = true)
    Page<ContractEntity> ListBooking (Pageable pageable);

}
