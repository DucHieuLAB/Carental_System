package com.example.create_entity.Repository;

import com.example.create_entity.Entity.AccountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    @Query(value = "SELECT * FROM accounts where user_name = ? ", nativeQuery = true)
    List<AccountEntity> Check_username(String username);

    @Query(value = "SELECT * FROM accounts where user_name = ? ", nativeQuery = true)
    AccountEntity GetAccountByName(String username);
    @Query(value = "SELECT * FROM accounts where  email =?", nativeQuery = true)
    List<AccountEntity> Check_email(String email);
//    @Query(value = "SELECT * FROM AccountEntity WHERE email = ?1 ", nativeQuery = true)
//    AccountEntity Check_email(String username);

    @Query(value = "SELECT * FROM accounts where phone = ? ",nativeQuery = true)
    List<AccountEntity> Check_Phone(String phone);

    @Query(value = "SELECT * FROM accounts where identity_number = ? ",nativeQuery = true)
    List<AccountEntity> Check_Identity(String Identity);

    @Query(value = "SELECT a FROM AccountEntity  a WHERE a.ID = ?1 ")
    AccountEntity getCustomerById(long customerId);




    @Query(value = "SELECT * FROM accounts where role_id = ?",nativeQuery = true)
    Page<AccountEntity> List_Staff(Integer id_role, Pageable pageable);




    //select * from accounts where email like '%hieu%' AND role_id=2 order by create_date ASC

    @Query(value = "select * from accounts where full_name like %?% AND role_id=? order by create_date ASC",nativeQuery = true)
    Page<AccountEntity> FilterByName(String Name,Integer Role_id,Pageable pageable);

    @Query(value = "select * from accounts where phone like %?% AND role_id=? order by create_date ASC",nativeQuery = true)
    Page<AccountEntity> FilterByPhone(String Phone,Integer Role_id,Pageable pageable);

    @Query(value = "select * from accounts where identity_number like %?% AND role_id=? order by create_date ASC",nativeQuery = true)
    Page<AccountEntity> FilterByIdentity_Number(String cmt,Integer Role_id,Pageable pageable);

}
