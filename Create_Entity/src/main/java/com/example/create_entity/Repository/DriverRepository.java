package com.example.create_entity.Repository;

import com.example.create_entity.Entity.DriverEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface DriverRepository extends JpaRepository<DriverEntity, Integer>, PagingAndSortingRepository<DriverEntity, Integer> {


    @Transactional
    @Query(value = "SELECT * FROM driver WHERE id_diver = ?1 ", nativeQuery = true)
    DriverEntity GetDriverById(Long id);

    @Transactional
    @Query(value = "select * from driver where status=1 ", nativeQuery = true)
    Page<DriverEntity> GetDriverByStatus(Pageable pageable);

    @Transactional
    @Query(value = "select * from driver where status=1 ", nativeQuery = true)
    Page<DriverEntity> GetDriverByName(Pageable pageable);


    @Transactional
    @Query(value = "select * from driver left join accounts on driver.account_id=accounts.account_id where accounts.full_name like  %?%  ", nativeQuery = true)
    List<DriverEntity> GetDriverBy_fullName(String name, Pageable pageable);




    @Transactional
    @Query(value = "select * from driver left join accounts on driver.account_id=accounts.account_id where accounts.full_name like  %?%  ", nativeQuery = true)
    List<DriverEntity> GetDriverBy_fullName1(String name);

    @Transactional
    @Query(value = "select * from driver cross join accounts on driver.account_id=accounts.account_id where accounts.phone LIKE  %?%  ", nativeQuery = true)
    List<DriverEntity> GetDriverBy_Phone1(String name);

    @Transactional
    @Query(value = "select * from driver cross join accounts on driver.account_id=accounts.account_id where accounts.phone LIKE  %?%    ", nativeQuery = true)
    List<DriverEntity> GetDriverBy_Phone(String name,Pageable pageable);

    @Transactional
    @Query(value = "select * from driver cross join accounts on driver.account_id=accounts.account_id where accounts.identity_number LIKE %?%  ", nativeQuery = true)
    List<DriverEntity> GetDriverBy_Identity1(String name);

    @Transactional
    @Query(value = "select * from driver cross join accounts on driver.account_id=accounts.account_id where accounts.identity_number LIKE %?% ", nativeQuery = true)
    List<DriverEntity> GetDriverBy_Identity(String name,Pageable pageable);


////    @Modifying
////    @Transactional
////    @Query(value = "DELETE FROM driver WHERE code = ?1 ",nativeQuery = true)
////    void Delete(String code);
//
//    @Modifying
//    @Transactional
//    @Query(value = "UPDATE  SET  Acc   FROM driver WHERE id_diver = 1 ",nativeQuery = true)
//    void Delete(Long ID);

}
