package com.example.create_entity.Repository;

import com.example.create_entity.Entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PaymentsRepository extends JpaRepository<PaymentEntity,Long> {
    @Query("SELECT p FROM PaymentEntity p WHERE p.contract.id = ?1")
    List<PaymentEntity> getListPaymentByContractId(long id);

    @Query(value = "SELECT * from payments inner join contracts on payments.contract_id = contracts.booking_id " +
            "where year(contracts.create_date)=  year(current_date()) and contracts.had_driver = 1 ",nativeQuery = true)
    List<PaymentEntity> Total_Paid_HadDriver_Quarter();

    @Query(value = "SELECT * from payments inner join contracts on payments.contract_id = contracts.booking_id " +
            "where year(contracts.create_date)=  year(current_date()) and contracts.had_driver = 0 ",nativeQuery = true)
    List<PaymentEntity> Total_Paid_Self_driving_Quarter();

}
