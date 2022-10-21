package com.example.create_entity.Repository;

import com.example.create_entity.Entity.ContractHadDriverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractHadDriverRepository extends JpaRepository<ContractHadDriverEntity,Long> {
    @Query("SELECT c FROM ContractHadDriverEntity c WHERE c.ContractEntity.id = ?1 ")
    ContractHadDriverEntity getByContractID(long id);
}
