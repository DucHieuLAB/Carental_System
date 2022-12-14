package com.example.crms_g8.Repository;

import com.example.crms_g8.Entity.BrandEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;



@Repository
@EnableJpaRepositories
public interface BrandRepository  extends JpaRepository<BrandEntity,Long> {
    @Query("SELECT b FROM BrandEntity b WHERE b.name = ?1 ")
    BrandEntity findBrandEntityByName(String name) ;

    @Query("SELECT b FROM BrandEntity b WHERE b.id = ?1 and b.status = 1 ")
    BrandEntity findBrandEntityById (Long id);

    @Query("SELECT b FROM BrandEntity b WHERE b.id = ?1  and b.name = ?2 and b.status = 1")
    BrandEntity findBrandEntityByIdAndName (Long id ,String name );

    @Query(value = "SELECT * FROM brands  WHERE MATCH(name) "
            + "AGAINST (?1) AND status = 1", nativeQuery = true )
    Page<BrandEntity> findBySearch(String nameSearch, Pageable pageable);

    Page<BrandEntity> findBrandEntityByStatus(int status, Pageable pageable);

}
