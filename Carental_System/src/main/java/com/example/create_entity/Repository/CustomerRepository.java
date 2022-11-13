package com.example.create_entity.Repository;

import com.example.create_entity.Entity.AccountEntity;
import com.example.create_entity.Entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    @Query(value = "SELECT cs FROM CustomerEntity cs INNER JOIN AccountEntity  ac ON cs.accountEntity.ID = ac.ID WHERE  cs.accountEntity.Username = ?1 ")
    CustomerEntity GetCustomerByName(String username);

}
