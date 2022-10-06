package com.example.create_entity.Repository;

import com.example.create_entity.Entity.AccountEntity;
import com.example.create_entity.Entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface RoleRepository extends CrudRepository<RoleEntity,Long> {
}
