package com.example.crsm_g8.Service.ServiceImpl;

import com.example.crsm_g8.Repository.RoleRepository;
import com.example.crsm_g8.Service.IService.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceIml implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public ResponseEntity<?> ListRole() {
        return new ResponseEntity<>(roleRepository.ListRole(), HttpStatus.OK);
    }
}
