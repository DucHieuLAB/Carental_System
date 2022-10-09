package com.example.create_entity;


import com.example.create_entity.Entity.AccountEntity;
import com.example.create_entity.Entity.DistrictsEntity;
import com.example.create_entity.Entity.DriverEntity;
import com.example.create_entity.Entity.RoleEntity;
import com.example.create_entity.Repository.AccountRepository;
import com.example.create_entity.Repository.DriverRepository;
import com.example.create_entity.Repository.RoleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class TestDemo {

    @Autowired
    DriverRepository repo;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    RoleRepository roleRepository;



    @Test
    public void testGet() {
        Integer userid = 1;
        Optional<DriverEntity> optionalUser = repo.findById(userid);
        Assertions.assertThat(optionalUser).isPresent();
        System.out.println(optionalUser.get());
    }

    @Test
    public void testAccount() throws ParseException {

        RoleEntity roleEntity = roleRepository.GetRoleDriver();


        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setFullName("Nguyen xuan hieu1222222");
        accountEntity.setEmail("hieunxhe140911");
        accountEntity.setUsername("hieu123");
        accountEntity.setGender(1);
        String sDate1="31/12/1998";
        Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);


        accountEntity.setPhone("0368995147");
        accountEntity.setDOB(date1);
        accountEntity.setIdentity_Number("1999291211212121");
        accountEntity.setIdentity_Picture_Back("img1.jpg");
        accountEntity.setIdentity_Picture_Front("img2.jpg");
        accountEntity.setAddress("Hung Yen1");
        accountEntity.setPassword("PassWord1");
        accountEntity.setRoleEntity(roleEntity);

        AccountEntity account = accountRepository.save(accountEntity);
        Assertions.assertThat(account).isNotNull();
        Assertions.assertThat(account.getID()).isGreaterThan(0);


        //   accountEntity.setPassword("PassWord1");


    }

    @Test
    public void testDistrict() throws ParseException {

        System.out.println("     -  sss ss ss      -".toUpperCase().trim().replace(" ",""));




    }
}
