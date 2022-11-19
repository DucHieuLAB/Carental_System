package com.example.create_entity.Repository;

import com.example.create_entity.Entity.AccountEntity;
import com.example.create_entity.Entity.CustomerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    @Query(value = "SELECT cs FROM CustomerEntity cs INNER JOIN AccountEntity  ac ON cs.accountEntity.ID = ac.ID WHERE  cs.accountEntity.Username = ?1 ")
    CustomerEntity GetCustomerByName(String username);


    @Query(value = "SELECT cs FROM CustomerEntity cs INNER JOIN AccountEntity ac ON cs.accountEntity.ID = ac.ID ")
    Page<CustomerEntity> GetListCustomer(Pageable pageable);

    @Query(value = "SELECT cs FROM CustomerEntity cs INNER JOIN AccountEntity ac ON cs.accountEntity.ID = ac.ID WHERE cs.FullName  LIKE %?1% ORDER BY cs.ModifiedDate DESC ")
    Page<CustomerEntity> FilterByName(String name,Pageable pageable);

    @Query(value = "SELECT cs FROM CustomerEntity cs INNER JOIN AccountEntity ac ON cs.accountEntity.ID = ac.ID WHERE cs.Phone  LIKE %?1% ORDER BY cs.ModifiedDate DESC ")
    Page<CustomerEntity> FilterByPhone(String phone,Pageable pageable);

    @Query(value = "SELECT cs FROM CustomerEntity cs INNER JOIN AccountEntity ac ON cs.accountEntity.ID = ac.ID WHERE cs.Identity_Number  LIKE %?1%  ORDER BY cs.ModifiedDate DESC ")
    Page<CustomerEntity> FilterByIdentity(String identity,Pageable pageable);

    @Query(value = "SELECT cs FROM CustomerEntity cs WHERE cs.ID = ?1 ")
    CustomerEntity GetCustomerID (Long ID);

    @Query(value = "SELECT cs FROM CustomerEntity cs WHERE cs.Identity_Number = ?1 ")
    CustomerEntity Check_Identity(String  Identity);

    @Query(value = "SELECT cs FROM CustomerEntity cs WHERE cs.Phone = ?1 ")
    CustomerEntity Check_Phone(String  phone);


}
