package com.example.create_entity.Repository;

import com.example.create_entity.Entity.BookingEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface BookingRepository extends JpaRepository<BookingEntity,Long> {
    @Query("SELECT b FROM BookingEntity b WHERE b.customer.ID = ?1 AND b.expected_start_date = ?2 AND b.expected_end_date = ?3")
    BookingEntity findByCustomerIDAndCreatedDate(Long customerId, Date startDate, Date endDate);



    @Query(value = "select * from bookings order by create_date DESC",nativeQuery = true)
    Page<BookingEntity> ListBooking (Pageable pageable);

}
