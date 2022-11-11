package com.example.create_entity.Repository;

import com.example.create_entity.Entity.AccountEntity;
import com.example.create_entity.Entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
}
