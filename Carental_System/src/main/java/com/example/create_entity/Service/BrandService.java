package com.example.create_entity.Service;

import com.example.create_entity.Entity.BrandEntity;
import com.example.create_entity.dto.Request.BrandRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface BrandService {

    ResponseEntity<?> addBrand(BrandRequest brandEntity);

    List<BrandEntity> getAll();

    ResponseEntity<?> findAll(int pageIndex, int pageSize,
                              String brandName);
    @Transactional
    ResponseEntity<?> updateBrand(BrandRequest brandEntity);

    @Transactional
    ResponseEntity<?> deleteBrand(Long brandId);

    ResponseEntity<?> findById(Long brandId, int pageIndex, int pageSize);


    ResponseEntity<?> findOnedByID(long id);
}
