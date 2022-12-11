package com.example.create_entity.Repository;

import com.example.create_entity.Entity.DistrictsEntity;
import com.example.create_entity.dto.Response.DistrictReponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepository extends JpaRepository<DistrictsEntity, Long> {

    @Query(value = "SELECT * FROM districts where city=?1 and wards=?2 and district_name=?3", nativeQuery = true)
    List<DistrictsEntity> check_district(String city,String ward,String district_name);
    @Query(value = "SELECT  *  FROM districts where city= ?1 and wards=?2 and district_name=?3  LIMIT 1", nativeQuery = true)
    DistrictsEntity check_districts(String city,String ward,String district_name);

    @Query( value = "SELECT * FROM districts d WHERE d.district_id = ?1 ORDER BY d.district_id LIMIT 1 ", nativeQuery = true)
    DistrictsEntity findOneById(long pickup_district_id);
}
