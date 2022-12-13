package com.example.create_entity.Service.IService;

import com.example.create_entity.dto.Request.UpdateInfoAdminRequest;
import com.example.create_entity.dto.Request.UpdateInfoCustomerRequest;
import com.example.create_entity.dto.Response.AdminInfoResponse;
import org.springframework.http.ResponseEntity;

public interface AdminService {
    ResponseEntity<?> DetailAdmin(String username);

    ResponseEntity<?> Update(UpdateInfoAdminRequest updateInfoAdminRequest);
}
