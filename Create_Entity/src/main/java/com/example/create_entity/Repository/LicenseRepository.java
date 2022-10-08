package com.example.create_entity.Repository;

import com.example.create_entity.Entity.DriverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LicenseRepository  extends JpaRepository<DriverEntity,Integer> {
}
