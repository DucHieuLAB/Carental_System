package com.example.crms_g8.Repository;

import com.example.crms_g8.Entity.CustomerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    @Query(value = "SELECT cs FROM CustomerEntity cs INNER JOIN AccountEntity  ac ON cs.accountEntity.ID = ac.ID WHERE  cs.accountEntity.Username = ?1 ")
    CustomerEntity GetByUserName(String username);


    @Query(value = "SELECT cs FROM CustomerEntity cs INNER JOIN AccountEntity ac ON cs.accountEntity.ID = ac.ID ")
    Page<CustomerEntity>FindAll(Pageable pageable);

    @Query(value = "SELECT cs FROM CustomerEntity cs INNER JOIN AccountEntity ac ON cs.accountEntity.ID = ac.ID WHERE cs.FullName  LIKE %?1% ORDER BY cs.ModifiedDate DESC ")
    Page<CustomerEntity>SearchByName(String name,Pageable pageable);

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


    @Query(value = "SELECT * FROM customers " +
            "inner join accounts on customers.id = accounts.account_id " +
            "where customers.phone = ? and accounts.user_name != ? and customers.id != ?  ",nativeQuery = true)
    CustomerEntity Check_Phone_Update(String phone, String username, Long id);


    @Query(value = "SELECT * FROM customers " +
            "inner join accounts on customers.id = accounts.account_id " +
            "where customers.identity_number = ? and accounts.user_name != ? and customers.id != ?  ",nativeQuery = true)
   CustomerEntity Check_Identity_Update(String Identity,String username,Long id);


    @Query(value = "SELECT  *  from customers " +
            "inner join accounts on customers.account_id = accounts.account_id " +
            "WHERE accounts.create_date >= DATE(NOW()) - INTERVAL 30 DAY",nativeQuery = true)
   List<CustomerEntity> CountNewCustomer();


}
