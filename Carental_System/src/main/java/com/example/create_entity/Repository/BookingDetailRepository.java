package com.example.create_entity.Repository;

import com.example.create_entity.Entity.BookingDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface BookingDetailRepository extends JpaRepository<BookingDetailEntity,Long> {

    @Query(value = "SELECT * FROM carrental.booking_details where booking_detail_id = ?1 ",nativeQuery = true)
    BookingDetailEntity BookingDetail(Long id);
}
