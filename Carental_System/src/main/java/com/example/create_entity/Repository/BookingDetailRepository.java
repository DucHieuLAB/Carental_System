package com.example.create_entity.Repository;

import com.example.create_entity.Entity.BookingDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingDetailRepository extends JpaRepository<BookingDetailEntity,Long> {
}
