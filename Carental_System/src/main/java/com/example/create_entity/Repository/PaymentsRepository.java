package com.example.create_entity.Repository;

import com.example.create_entity.Entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentsRepository extends JpaRepository<PaymentEntity,Long> {
    @Query("SELECT p FROM PaymentEntity p WHERE p.contract.id = ?1")
    List<PaymentEntity> getListPaymentBtContractId(long id);
}
