package com.example.create_entity.Repository;

import com.example.create_entity.Entity.LicenseTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LicenseRepository  extends JpaRepository<LicenseTypeEntity,Long> {

    @Query(value = "SELECT * from carrental.license_types where license_name=? ", nativeQuery = true)
    LicenseTypeEntity Get_License_By_Name(String license);
    @Query("SELECT l FROM LicenseTypeEntity l where l.ID = ?1")
    LicenseTypeEntity getLicenseById(long licenseId);
}
