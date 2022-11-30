package com.example.create_entity.Repository;

import com.example.create_entity.Entity.AccountEntity;
import com.example.create_entity.Entity.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, Long> {


    @Query(value = "SELECT ad FROM  AdminEntity  ad INNER JOIN AccountEntity  ac ON ad.accountEntity.ID = ac.ID WHERE ac.Username = ?1")
    AdminEntity GetByUsername(String username);

    @Query(value = "SELECT * FROM admins inner join accounts on admins.id = accounts.account_id where admins.phone = ? and accounts.user_name != ? and admins.id != ?",nativeQuery = true)
    AdminEntity Check_Phone_Update(String phone , String username, Long id);

    @Query(value = "SELECT * FROM admins inner join accounts on admins.id = accounts.account_id where admins.identity_number = ? and accounts.user_name != ? and admins.id != ?",nativeQuery = true)
    AdminEntity  Check_Identity_Update(String trim, String username, Long id);
}


