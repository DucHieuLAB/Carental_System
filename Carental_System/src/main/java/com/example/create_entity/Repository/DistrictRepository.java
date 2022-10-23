package com.example.create_entity.Repository;

import com.example.create_entity.Entity.DistrictsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepository extends JpaRepository<DistrictsEntity, Long> {

    @Query(value = "SELECT * FROM districts where city=? and wards=? and district_name=?", nativeQuery = true)
    List<DistrictsEntity> check_district(String city,String ward,String district_name);

    @Query(value = "SELECT  *  FROM districts where city=? and wards=? and district_name=? ORDER BY district_id LIMIT 1", nativeQuery = true)
    DistrictsEntity check_districts(String city,String ward,String district_name);


}
