package com.example.create_entity.Repository;

import com.example.create_entity.Entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    @Query(value = "SELECT * FROM accounts where user_name = ? ", nativeQuery = true)
    List<AccountEntity> Check_username(String username);

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
}