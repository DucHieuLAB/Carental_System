package com.example.create_entity.Repository;

import com.example.create_entity.Entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
@Repository
public interface BookingRepository extends JpaRepository<BookingEntity,Long> {
    @Query("SELECT b FROM BookingEntity b WHERE b.customer.ID = ?1 AND b.expected_start_date = ?2 AND b.expected_end_date = ?3")
    BookingEntity findByCustomerIDAndExpectedStartDateAndExpectedEndDate(Long customerId, Date startDate, Date endDate);

    @Query("SELECT b FROM BookingEntity b WHERE b.customer.ID = ?1 AND b.expected_start_date = ?2 AND b.expected_end_date = ?3 AND b.status > 0 ")
    BookingEntity getByCustomerIdAndExpectStartDateAndExpectEndDate(Long id, Date expected_start_date, Date expected_end_date);
}
