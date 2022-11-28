package com.example.create_entity.Repository;

import com.example.create_entity.Entity.SurchargeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SurchargeRepository extends JpaRepository<SurchargeEntity,Long> {
@Query("SELECT s FROM SurchargeEntity s WHERE s.contractEntity.id = ?1")
    List<SurchargeEntity> getListSurchargeByContractId(long id);
}
