package com.example.create_entity.Repository;

import com.example.create_entity.Entity.DriverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import javax.transaction.Transactional;
import java.util.List;
@Repository
public interface DriverRepository extends JpaRepository<DriverEntity,Integer> {


    @Transactional
    @Query(value = "SELECT * FROM driver WHERE id_diver = ?1 ",nativeQuery = true)
    DriverEntity GetDriverById(Long id);





//    @Modifying
//    @Transactional
//    @Query(value = "DELETE FROM driver WHERE code = ?1 ",nativeQuery = true)
//    void Delete(String code);

    @Modifying
    @Transactional
    @Query(value = "UPDATE  SET  Acc        FROM driver WHERE id_diver = 1 ",nativeQuery = true)
    void Delete(Long ID);

}
