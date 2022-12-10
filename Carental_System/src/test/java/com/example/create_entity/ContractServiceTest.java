package com.example.create_entity;

import com.example.create_entity.Repository.ContractRepository;
import com.example.create_entity.Service.ContractService;
import com.example.create_entity.Service.ContractServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ContractServiceTest {
    @Autowired
    ContractServiceImpl contractService;

    @Autowired
    ContractRepository contractRepository;
    // test add cotract
}
