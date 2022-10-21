package com.example.create_entity.Service;

import com.example.create_entity.Entity.RoleEntity;
import com.example.create_entity.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceIml implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public ResponseEntity<?> ListRole() {
        return new ResponseEntity<>(roleRepository.ListRole(), HttpStatus.OK);
    }
}
