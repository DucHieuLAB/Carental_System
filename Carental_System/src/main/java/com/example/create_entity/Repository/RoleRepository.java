package com.example.create_entity.Repository;

import com.example.create_entity.Entity.AccountEntity;
import com.example.create_entity.Entity.RoleEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity,Long> {


    @Query(value = "SELECT * FROM roles WHERE role_title= ? ; ",nativeQuery = true)
    RoleEntity GetRoleDriver(String name);


    @Query(value = "SELECT role_id FROM roles where role_title= ? ; ",nativeQuery = true)
    Integer  GetIDRoleByNameRole(String name);

    @Query(value = "SELECT * FROM carrental.roles ;  ",nativeQuery = true)
    List<RoleEntity> ListRole();



}
