package com.example.create_entity.Service;

import com.example.create_entity.Entity.AccountEntity;
import com.example.create_entity.Entity.RoleEntity;
import com.example.create_entity.Repository.AccountRepository;
import com.example.create_entity.Repository.RoleRepository;
import com.example.create_entity.dto.Request.LoginRequest;
import com.example.create_entity.dto.Response.ResponseVo;
import com.example.create_entity.untils.JwtUtils;
import com.example.create_entity.untils.ResponseVeConvertUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public ResponseEntity<?> login(LoginRequest loginRequest) {
        ResponseVo responseVo = null;
        // validate
        // encode
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String enCodePassword = passwordEncoder.encode(loginRequest.getPassword());
        System.out.println(enCodePassword);
        // verify
        Optional<AccountEntity> accountEntity = accountRepository.findByUsername(loginRequest.getUsername());
        if (!accountEntity.isPresent()) {
            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Tên đăng nhập không tồn tại", null);
            return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
        }
        // check password
        if (!passwordEncoder.matches(loginRequest.getPassword(), accountEntity.get().getPassword())) {
            responseVo = ResponseVeConvertUntil.createResponseVo(false, "mật khẩu không chính xác", null);
            return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
        }
        // check account valid
        if (accountEntity.get().getStatus() == 0) {
            responseVo = ResponseVeConvertUntil.createResponseVo(false, "Tài khoản hiện không khả dụng", null);
            return new ResponseEntity<>(responseVo, HttpStatus.BAD_REQUEST);
        }

        String token = jwtUtils.generateJwt(accountEntity.get());
                HashMap<String,Object> response = new HashMap<>();
                response.put("token",token);
                response.put("role",accountEntity.get().getRoleEntity().getRoleID());
                response.put("username",accountEntity.get().getUsername());
                response.put("status",accountEntity.get().getStatus());
                response.put("accountId",accountEntity.get().getID());
        responseVo = ResponseVeConvertUntil.createResponseVo(true, "Đăng nhập thành công", response);
        return new ResponseEntity<>(responseVo, HttpStatus.OK);
    }
}
