package com.example.create_entity.Repository;

import com.example.create_entity.Entity.BookingDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import org.springframework.stereotype.Repository;



@Repository
public interface ContractDetailRepository extends JpaRepository<BookingDetailEntity, Long> {

    @Query("SELECT b FROM BookingDetailEntity b WHERE b.booking.id = ?1")
    public List<BookingDetailEntity> getListBookingDetailEntitiesByBookingId(Long id);


    @Query(value = "SELECT * FROM carrental.booking_details where booking_detail_id = ?1 ", nativeQuery = true)
    BookingDetailEntity BookingDetail(Long id);

}
