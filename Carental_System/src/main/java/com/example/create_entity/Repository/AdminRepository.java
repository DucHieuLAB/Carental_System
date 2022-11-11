package com.example.create_entity.Repository;

import com.example.create_entity.Entity.AccountEntity;
import com.example.create_entity.Entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, Long> {
}
