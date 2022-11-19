package com.example.create_entity.Repository;


import com.example.create_entity.Entity.AccountEntity;
import com.example.create_entity.Entity.CustomerEntity;
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

    @Query(value = "SELECT cs FROM StaffEntity cs INNER JOIN AccountEntity  ac ON cs.accountEntity.ID = ac.ID WHERE  cs.accountEntity.Username = ?1 ")
    CustomerEntity GetStaffByName(String username);

    @Query(value = "SELECT st FROM StaffEntity st INNER JOIN AccountEntity ac ON st.accountEntity.ID =ac.ID WHERE st.FullName LIKE %?1% ")
    Page<StaffEntity> FilterByName(String name,Pageable pageable);

    @Query(value = "SELECT st FROM StaffEntity st INNER JOIN AccountEntity ac ON st.accountEntity.ID =ac.ID WHERE st.Phone LIKE %?1% ")
    Page<StaffEntity> FilterByPhone(String Phone,Pageable pageable);

    @Query(value = "SELECT st FROM StaffEntity st INNER JOIN AccountEntity ac ON st.accountEntity.ID =ac.ID WHERE st.Identity_Number LIKE %?1% ")
    Page<StaffEntity> FilterByIdentity_Number(String Identity,Pageable pageable);

    @Query(value = "SELECT st FROM StaffEntity st ORDER BY st.ModifiedDate DESC ")
    Page<StaffEntity> List_Staff(Pageable pageable);

    @Query(value = "SELECT st FROM StaffEntity st WHERE st.accountEntity.Username = ?1 ")
    StaffEntity GetStaffByUserName(String name);

    @Query(value = "SELECT * FROM  staffs where phone = ? ",nativeQuery = true)
    List<StaffEntity> Check_Phone(String phone);

    @Query(value = "SELECT * FROM staffs where identity_number = ? ",nativeQuery = true)
    List<StaffEntity> Check_Identity(String Identity);

}
