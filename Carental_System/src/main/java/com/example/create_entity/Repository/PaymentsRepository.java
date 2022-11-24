package com.example.create_entity.Repository;

import com.example.create_entity.Entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentsRepository extends JpaRepository<PaymentEntity,Long> {
}
