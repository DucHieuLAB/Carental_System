package com.example.create_entity.Service;

import com.example.create_entity.Entity.AccountEntity;
import com.example.create_entity.Repository.AccountRepository;
import com.example.create_entity.Repository.RoleRepository;
import com.example.create_entity.dto.Response.PagingResponse;
import com.example.create_entity.dto.Response.ReposMesses;
import com.example.create_entity.dto.Response.StaffResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceIml implements AccountService {
    @Autowired
    AccountRepository accountRepository;


    @Autowired
    RoleRepository roleRepository;
    public Integer CheckNullPaging(Integer p) {
        if (p == null) {
            p = 0;
        } else if (p > 0) {
            p = p - 1;
        }
        return p;
    }
    @Override
    public ResponseEntity<?> getListByNameRole(Integer p) {

          p = CheckNullPaging(p);

        List<StaffResponse> staffResponseList = new ArrayList<>();
        Integer id = roleRepository.GetIDRoleByNameRole("Staff");

        Pageable pageable = PageRequest.of(p, 5);
        Page<AccountEntity> accountEntities = accountRepository.List_Staff(id, pageable);
        try {
            accountEntities.forEach(AccountEntity -> {
                StaffResponse staffResponse = new StaffResponse();
                staffResponse.setPhone(AccountEntity.getPhone());
                staffResponse.setStatus(AccountEntity.getStatus());
                staffResponse.setFullName(AccountEntity.getFullName());
                staffResponse.setIdentity_number(AccountEntity.getIdentity_Number());
                staffResponse.setDOB(AccountEntity.getDOB());
                staffResponse.setUserName(AccountEntity.getUsername());
                staffResponse.setEmail(AccountEntity.getEmail());
                staffResponse.setAddress(AccountEntity.getAddress());
                staffResponseList.add(staffResponse);
            });
            PagingResponse pagingResponse = new PagingResponse();
            pagingResponse.setDriverInfoResponsesList(staffResponseList);
            pagingResponse.setTotalPage(accountEntities.getTotalPages());
            pagingResponse.setNumberPage(accountEntities.getNumber() + 1);

            if (accountEntities.isEmpty()) {
                ReposMesses messes = new ReposMesses();
                messes.setMess("NOT FOUND");
                return new ResponseEntity<>(messes, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(pagingResponse, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }

    }
}
