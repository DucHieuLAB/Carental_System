package com.example.crms_g8.Service.IService;

import com.example.crms_g8.dto.Request.UpdateInfoAdminRequest;
import org.springframework.http.ResponseEntity;

public interface AdminService {
    ResponseEntity<?> DetailAdmin(String username);

    ResponseEntity<?> Update(UpdateInfoAdminRequest updateInfoAdminRequest);
}
