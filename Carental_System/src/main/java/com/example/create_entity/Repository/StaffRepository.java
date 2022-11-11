package com.example.create_entity.Repository;


import com.example.create_entity.Entity.AccountEntity;
import com.example.create_entity.Entity.StaffEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffRepository extends JpaRepository<StaffEntity,Long> {

    @Query(value = "SELECT * FROM carrental_v2.staffs Where staffs.account_id= ?1 ",nativeQuery = true)
    StaffEntity staffEntity(Long ac_id);

    @Query(value = "select * from staffs where full_name like %?% AND role_id=? order by create_date ASC ",nativeQuery = true)
    Page<StaffEntity> FilterByName(String Name, Integer Role_id, Pageable pageable);


    @Query(value = "SELECT * FROM  staffs where phone = ? ",nativeQuery = true)
    List<StaffEntity> Check_Phone(String phone);

    @Query(value = "SELECT * FROM staffs where identity_number = ? ",nativeQuery = true)
    List<StaffEntity> Check_Identity(String Identity);

}
