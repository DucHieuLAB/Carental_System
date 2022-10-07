package com.example.create_entity.Repository;

import com.example.create_entity.Entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface BrandRepository  extends JpaRepository<BrandEntity,Long> {
    @Query("SELECT b FROM BrandEntity b WHERE b.name = ?1 ")
    BrandEntity findBrandEntityByName(String name) ;

    @Query("SELECT b FROM BrandEntity b WHERE b.id = ?1 and b.status = 1")
    BrandEntity findBrandEntityById (Long id);
}
