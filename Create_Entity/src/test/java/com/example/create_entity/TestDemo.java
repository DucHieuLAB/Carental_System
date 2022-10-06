package com.example.create_entity;


import com.example.create_entity.Entity.DriverEntity;
import com.example.create_entity.Repository.DriverRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class TestDemo {

    DriverRepository repo;
    @Test
    public void testGet() {
        Integer userid = 1;
        Optional<DriverEntity> optionalUser = repo.findById(userid);
        Assertions.assertThat(optionalUser).isPresent();
        System.out.println(optionalUser.get());
    }

}
