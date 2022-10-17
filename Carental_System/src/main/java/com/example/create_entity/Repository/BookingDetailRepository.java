package com.example.create_entity.Repository;

import com.example.create_entity.Entity.BookingDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingDetailRepository extends JpaRepository<BookingDetailEntity,Long> {
@Query("SELECT b FROM BookingDetailEntity b WHERE b.booking.id = ?1")
    public List<BookingDetailEntity> getListBookingDetailEntitiesByBookingId(Long id);
}
