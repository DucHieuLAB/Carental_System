package com.example.create_entity.Repository;

import com.example.create_entity.Entity.AccountEntity;
import com.example.create_entity.Entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity,Long> {


    @Transactional
    @Query(value = "SELECT * FROM carrental.roles WHERE role_title=\"Driver\" ; ",nativeQuery = true)
    RoleEntity GetRoleDriver();
}
